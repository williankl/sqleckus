package internal.use_cases.query

import models.SqlCode
import models.SqlQuery

@PublishedApi
internal object SelectUseCase {
    operator fun invoke(selection: SqlQuery.Selection): SqlCode.Executable {
        val targetSelection =
            selection.columns
                ?.joinToString(separator = ", ") { column ->
                    column.name
                } ?: "*"

        val sql = selection.let {
            "SELECT $targetSelection FROM ${it.schema.name}.${it.table.name}"
        }
        return SqlCode.Executable(sql)
    }
}
