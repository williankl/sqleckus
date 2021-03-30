package internal.use_cases.call

import models.Schema
import models.SqlStatement
import models.Table
import java.sql.Connection

internal object DeleteItemUseCase {
    operator fun invoke(
        connection: Connection,
        schema: Schema,
        table: Table,
        condition: SqlStatement.TableConditionStatement
    ) {
        val deleteStatement = "DELETE FROM ${schema.name}.${table.name}"

        val whereStatement =
            condition.let {
                "WHERE ${it.column.name} ${it.operator.sql} ${it.value}"
            }

        val sql = "$deleteStatement $whereStatement;"
        println(sql).let { connection.prepareCall(sql).execute() }
    }
}
