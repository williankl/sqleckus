package internal.use_cases.call

import errors.SQLecusException
import internal.JsonHelper.retrieveItemValuePair
import models.Schema
import models.Table
import java.sql.Connection

@PublishedApi
internal object InsertItemUseCase {
    inline operator fun <reified T> invoke(
        connection: Connection,
        schema: Schema,
        table: Table,
        item: T
    ) {
        val insertStatement = "INSERT INTO ${schema.name}.${table.name}"

        val columns =
            table.columns.joinToString(separator = ", ") { column ->
                item.retrieveItemValuePair()
                    .firstOrNull { it.first == column.name }
                    ?.let { it.first }
                    ?: throw SQLecusException.CouldNotFindSqlFieldOnTable(
                        column.name,
                        schema.name,
                        table.name
                    )
            }

        val valuesStatement = "VALUES"

        val values =
            table.columns.joinToString(separator = ", ") { column ->
                item.retrieveItemValuePair()
                    .firstOrNull { it.first == column.name }
                    ?.let { "'${it.second}'" }
                    ?: throw SQLecusException.CouldNotFindSqlFieldOnTable(
                        column.name,
                        schema.name,
                        table.name
                    )
            }

        val sql = "$insertStatement ($columns) $valuesStatement ($values);"
        println(sql).let { connection.prepareCall(sql).execute() }
    }
}
