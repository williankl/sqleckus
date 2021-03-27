package models

sealed class SqlQuery {
    data class Selection(
        val by: Column,
        val equals: String,
        val schema: Schema,
        val table: Table
    ) : SqlQuery()

    data class InnerJoin(
        val schema: Schema,
        val baseTable: Table,
        val baseCondition: SqlStatement.ConditionStatement,
        val statements: List<SqlStatement.JoinStatement>,
        val conditionsWithLogic: List<SqlOperator.Logic>
    ) : SqlQuery()
}
