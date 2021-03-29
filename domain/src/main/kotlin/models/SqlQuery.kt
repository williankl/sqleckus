package models

sealed class SqlQuery {
    data class Selection(
        val schema: Schema,
        val table: Table,
        val columns: List<Column>? = null
    ) : SqlQuery()

    data class Where(
        val table: Table,
        val column: Column,
        val condition: SqlOperator,
        val value: Any
    )

    data class InnerJoin(
        val schema: Schema,
        val table: Table,
        val on: Column,
        val condition: SqlOperator,
        val value: Any
    ) : SqlQuery()
}
