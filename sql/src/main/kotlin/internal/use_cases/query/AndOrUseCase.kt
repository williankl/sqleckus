package internal.use_cases.query

import models.Column
import models.Schema
import models.SqlCode
import models.SqlOperator
import models.Table

@PublishedApi
internal object AndOrUseCase {
    operator fun invoke(
        code: SqlCode,
        logicOperator: SqlOperator.Logic,
        schema: Schema,
        table: Table,
        column: Column,
        condition: SqlOperator,
        value: Any
    ): SqlCode.TerminalLogic {
        val andOrUseCase = "${logicOperator.sql} ${schema.name}.${table.name}.${column.name} ${condition.sql} '$value'"

        val sql = "${code.sql} $andOrUseCase"
        return SqlCode.TerminalLogic(sql)
    }

    operator fun invoke(
        code: SqlCode,
        logicOperator: SqlOperator.Logic,
        schema: Schema,
        table: Table,
        column: Column,
        condition: SqlOperator,
        toSchema: Schema,
        toTable: Table,
        toColumn: Column
    ): SqlCode.TerminalLogic {
        val andOrUseCase =
            "${logicOperator.sql} ${schema.name}.${table.name}.${column.name} ${condition.sql} ${toSchema.name}.${toTable.name}.${toColumn.name}"

        val sql = "${code.sql} $andOrUseCase"
        return SqlCode.TerminalLogic(sql)
    }
}
