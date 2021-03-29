package internal.use_cases.query

import models.SqlCode
import models.SqlQuery

@PublishedApi
internal object WhereUseCase {
    operator fun invoke(code: SqlCode, selection: SqlQuery.Where): SqlCode.Final {
        val whereStatement = selection.let {
            "WHERE ${it.table.name}.${it.column.name} ${it.condition.sql} '${it.value}'"
        }

        val sql = "$code $whereStatement"
        return SqlCode.Final(sql)
    }
}
