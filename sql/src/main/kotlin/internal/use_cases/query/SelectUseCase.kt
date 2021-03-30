package internal.use_cases.query

import models.Column
import models.Schema
import models.SqlCode
import models.Table

@PublishedApi
internal object SelectUseCase {
    operator fun invoke(
        schema: Schema,
        table: Table,
        columns: List<Column>? = null
    ): SqlCode.Executable {
        val targetSelection =
            columns
                ?.joinToString(separator = ", ") { column ->
                    column.name
                } ?: "*"

        val sql = "SELECT $targetSelection FROM ${schema.name}.${table.name}"

        return SqlCode.Executable(sql, table)
    }
}
