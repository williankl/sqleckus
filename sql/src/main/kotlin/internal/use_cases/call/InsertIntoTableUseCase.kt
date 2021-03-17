package internal.use_cases.call

import errors.SQLecusException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import models.SqlCall
import java.sql.Connection

internal object InsertIntoTableUseCase {
    operator fun invoke(connection: Connection, call: SqlCall){
        if(call is SqlCall.InsertItem<*>) { /* Nothing */ }
        else throw SQLecusException.InvalidAction

        val json = Json.encodeToJsonElement(call.item)

        val insertStatement = "INSERT INTO ${call.schema.name}.${call.table.name}"

        val columns =
            json.jsonObject.entries.joinToString(separator = " , ") { map ->
                call.table.columns
                    .firstOrNull { column -> column.name == map.key }
                    ?.name
                    ?: throw SQLecusException.CouldNotFindSqlFieldOnTable(map.key, call.schema.name, call.table.name)
            }

        val valuesStatement = "VALUES"

        val values =
            json.jsonObject.entries.joinToString(separator = " , ") { map ->
                call.table.columns
                    .firstOrNull { column -> column.name == map.key }
                    ?.let { "'${it.type.code}'" }
                    ?: throw SQLecusException.CouldNotFindSqlFieldOnTable(map.key, call.schema.name, call.table.name)
            }

        val sql = "$insertStatement ($columns) $valuesStatement ($values);"

        println(sql).let { connection.prepareCall(sql).execute() }
    }
}