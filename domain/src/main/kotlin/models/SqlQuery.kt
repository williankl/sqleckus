package models

sealed class SqlQuery {
    data class JoinStatement(
        val by1: Pair<Column, Table>,
        val by2: Pair<Column, Table>
    )

    data class ConditionStatement(
        val table: Table,
        val column: Column,
        val operator: SqlOperator.Comparator,
        val value: String
    )

    data class Selection(
        val by: Column,
        val equals: String,
        val schema: Schema,
        val table: Table
    ) : SqlQuery()

    data class InnerJoin(
        val schema: Schema,
        val baseTable: Table,
        val baseCondition: ConditionStatement,
        val statements: List<JoinStatement>,
        val conditionsWithLogic: List<SqlOperator.Logic>
    ) : SqlQuery()
}
