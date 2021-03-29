package internal.use_cases.query

import models.SqlCode
import models.SqlQuery

@PublishedApi
internal object InnerJoinUseCase {
    operator fun invoke(code: SqlCode, query: SqlQuery.InnerJoin): SqlCode.Executable {
        val innerJoinStatement =
            "INNER JOIN ${query.schema.name}.${query.table.name}"
        val onStatement =
            "ON ${query.table.name}.${query.on.name} ${query.condition.sql} ${query.value}"

        val sql = "$innerJoinStatement $onStatement"

        return SqlCode.Executable("${code.sql} $sql")
    }
}
