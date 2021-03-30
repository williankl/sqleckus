package internal.use_cases.call

import models.Schema
import models.SqlStatement
import models.Table
import java.sql.Connection

internal object UpdateItemUseCase {
    operator fun invoke(
        connection: Connection,
        schema: Schema,
        table: Table,
        changeSet: List<SqlStatement.SetStatement>,
        condition: SqlStatement.TableConditionStatement
    ) {
        val updateStatement =
            "UPDATE ${schema.name}.${table.name}"

        val setStatement =
            changeSet.joinToString(separator = ", ") { set ->
                "${set.column.name} = ${set.toValue}"
            }.let { "SET $it" }

        val whereStatement =
            condition.let {
                "${it.column.name} ${it.operator.sql} ${it.value}"
            }.let { "WHERE $it" }

        val sql = "$updateStatement $setStatement $whereStatement;"
        println(sql).let { connection.prepareCall(sql).execute() }
    }
}
