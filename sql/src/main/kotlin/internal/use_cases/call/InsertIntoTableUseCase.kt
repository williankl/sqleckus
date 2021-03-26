package internal.use_cases.call

import errors.SQLecusException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import models.SqlCall
import retrieveCallableKeyValuePair
import retrieveCallableNames
import java.sql.Connection
import kotlin.reflect.KClass

internal object InsertIntoTableUseCase {

    @OptIn(ExperimentalStdlibApi::class)
    operator fun invoke(connection: Connection, call: SqlCall.InsertItem<Any>){
        val klass = call.item::class

        val insertStatement = "INSERT INTO ${call.schema.name}.${call.table.name}"

        val columns =
            call.table.columns.joinToString(separator = ", ") { column ->
                klass.retrieveCallableKeyValuePair()
                    .firstOrNull { it?.first == column.name }
                    ?.let { it.first }
                    ?: throw SQLecusException.CouldNotFindSqlFieldOnTable(
                        column.name,
                        call.schema.name,
                        call.table.name
                    )
            }

        val valuesStatement = "VALUES"

        val values =
            call.table.columns.joinToString(separator = ", ") { column ->
                klass.retrieveCallableKeyValuePair()
                    .firstOrNull { it?.first == column.name }
                    ?.let { it.second }
                    ?: throw SQLecusException.CouldNotFindSqlFieldOnTable(
                        column.name,
                        call.schema.name,
                        call.table.name
                    )
            }

        val sql = "$insertStatement ($columns) $valuesStatement ($values);"

        println(sql).let { connection.prepareCall(sql).execute() }
    }
}