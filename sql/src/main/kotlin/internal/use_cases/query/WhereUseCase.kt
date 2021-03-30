package internal.use_cases.query

import models.Column
import models.SqlCode
import models.SqlOperator
import models.Table

@PublishedApi
internal object WhereUseCase {
    operator fun invoke(
        code: SqlCode,
        table: Table,
        column: Column,
        condition: SqlOperator,
        value: Any
    ): SqlCode.TerminalLogic {
        val whereStatement = "WHERE ${table.name}.${column.name} ${condition.sql} '$value'"

        val sql = "${code.sql} $whereStatement"
        return SqlCode.TerminalLogic(sql)
    }
}
