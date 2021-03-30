package internal.use_cases.query

import models.Column
import models.SqlCode
import models.SqlOperator
import models.Table

@PublishedApi
internal object AndOrUseCase {
    operator fun invoke(
        code: SqlCode,
        logicOperator: SqlOperator.Logic,
        table: Table,
        column: Column,
        condition: SqlOperator,
        value: Any
    ): SqlCode.TerminalLogic {
        val andOrUseCase = "${logicOperator.sql} ${table.name}.${column.name} ${condition.sql} $value"

        val sql = "${code.sql} $andOrUseCase"
        return SqlCode.TerminalLogic(sql)
    }
}
