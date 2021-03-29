package models

sealed class SqlStatement {
    data class ConditionStatement(
        val table: Table,
        val column: Column,
        val operator: SqlOperator.Comparator,
        val value: Any
    ) : SqlStatement()

    data class TableConditionStatement(
        val column: Column,
        val operator: SqlOperator.Comparator,
        val value: Any
    ) : SqlStatement()

    data class SetStatement(
        val column: Column,
        val toValue: Any
    ) : SqlStatement()
}
