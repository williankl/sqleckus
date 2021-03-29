package internal.use_cases.call

import errors.SQLecusException
import internal.JsonHelper.retrieveItemValuePair
import models.SqlCall
import java.sql.Connection

@PublishedApi
internal object InsertItemUseCase {
    inline operator fun <reified T> invoke(connection: Connection, call: SqlCall.InsertItem<T>) {
        val insertStatement = "INSERT INTO ${call.schema.name}.${call.table.name}"

        val columns =
            call.table.columns.joinToString(separator = ", ") { column ->
                call.item.retrieveItemValuePair()
                    .firstOrNull { it.first == column.name }
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
                call.item.retrieveItemValuePair()
                    .firstOrNull { it.first == column.name }
                    ?.let { "'${it.second}'" }
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
