package internal.use_cases.query

import models.Column
import models.Schema
import models.SqlCode
import models.SqlOperator
import models.SqlQuery
import models.Table

@PublishedApi
internal object InnerJoinUseCase {
    operator fun invoke(
        code: SqlCode,
        schema: Schema,
        table: Table,
        on: Column,
        condition: SqlOperator,
        value: Any
    ): SqlCode.Executable {
        val innerJoinStatement =
            "INNER JOIN ${schema.name}.${table.name}"
        val onStatement =
            "ON ${table.name}.${on.name} ${condition.sql} $value"

        val sql = "$innerJoinStatement $onStatement"

        return SqlCode.Executable("${code.sql} $sql")
    }
}
