package internal.use_cases.query

import models.Column
import models.Schema
import models.SqlCode
import models.SqlOperator
import models.Table

@PublishedApi
internal object WhereUseCase {
    operator fun invoke(
        code: SqlCode,
        schema: Schema,
        table: Table,
        column: Column,
        condition: SqlOperator,
        value: Any
    ): SqlCode.TerminalLogic {
        val whereStatement = "WHERE ${schema.name}.${table.name}.${column.name} ${condition.sql} '$value'"

        val sql = "${code.sql} $whereStatement"
        return SqlCode.TerminalLogic(sql)
    }

    operator fun invoke(
        code: SqlCode,
        schema: Schema,
        table: Table,
        column: Column,
        condition: SqlOperator,
        toSchema: Schema,
        toTable: Table,
        toColumn: Column
    ): SqlCode.TerminalLogic {
        val whereStatement =
            "WHERE ${schema.name}.${table.name}.${column.name} ${condition.sql} ${toSchema.name}.${toTable.name}.${toColumn.name}"

        val sql = "${code.sql} $whereStatement"
        return SqlCode.TerminalLogic(sql)
    }
}
