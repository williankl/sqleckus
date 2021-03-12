package models

sealed class SqlQuery{
    data class JoinStatement(
        val by1: Column,
        val by2: Column,
        val t1: Table,
        val t2: Table
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
        ): SqlQuery()

    data class InnerJoin(
        val schema: Schema,
        val baseTable: Table,
        val statements: List<JoinStatement>,
        val baseCondition: ConditionStatement,
        val conditionsWithLogic: List<SqlOperator.Logic>
        ): SqlQuery()
}
