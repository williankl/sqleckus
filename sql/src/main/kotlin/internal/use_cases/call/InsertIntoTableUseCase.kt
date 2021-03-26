package internal.use_cases.call

import errors.SQLecusException
import models.SqlCall
import retrieveCallableKeyValuePair
import java.sql.Connection

internal object InsertIntoTableUseCase {

    @OptIn(ExperimentalStdlibApi::class)
    operator fun invoke(connection: Connection, call: SqlCall.InsertItem<Any>) {
        val insertStatement = "INSERT INTO ${call.schema.name}.${call.table.name}"

        val columns =
            call.table.columns.joinToString(separator = ", ") { column ->
                call.item.retrieveCallableKeyValuePair()
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
                call.item.retrieveCallableKeyValuePair()
                    .firstOrNull { it?.first == column.name }
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
