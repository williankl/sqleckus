package internal.use_cases.call

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import models.SqlCall
import java.lang.IllegalArgumentException
import java.sql.Connection

internal object InsertIntoTableUseCase {
    operator fun invoke(connection: Connection, call: SqlCall){
        if(call is SqlCall.InsertItem<*>) { /* Nothing */ }
        else throw IllegalArgumentException("This item should not be here!")
        val json = Json.encodeToJsonElement(call.item)

        val insertStatement = "INSERT INTO ${call.schema.name}.${call.table.name}"

        val columns =
            json.jsonObject.entries.joinToString(separator = " , ") { map ->
                call.table.columns
                    .firstOrNull { column -> column.name == map.key }
                    ?.name
                    ?: throw IllegalArgumentException("Could not find ${map.key} equivalent on table ${call.schema.name}.${call.table.name}")
            }

        val valuesStatement = "VALUES"

        val values =
            json.jsonObject.entries.joinToString(separator = " , ") { map ->
                call.table.columns
                    .firstOrNull { column -> column.name == map.key }
                    ?.let { "'${it.type.code}'" }
                    ?: throw IllegalArgumentException("Could not find '${map.key}' equivalent on table '${call.schema.name}.${call.table.name}'")
            }

        val sql = "$insertStatement ($columns) $valuesStatement ($values);"

        connection
            .prepareCall(sql)
            .execute()
            .also { println(sql) }
    }
}