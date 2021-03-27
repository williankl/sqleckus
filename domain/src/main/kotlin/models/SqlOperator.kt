package models

sealed class SqlOperator(val sql: String) {
    sealed class Logic(sql: String, val condition: SqlStatement.ConditionStatement) : SqlOperator(sql) {
        class And(condition: SqlStatement.ConditionStatement) : Logic("AND", condition)
        class Or(condition: SqlStatement.ConditionStatement) : Logic("OR", condition)
    }

    sealed class Comparator(sql: String) : SqlOperator(sql) {
        object SmallerThan : Comparator("<")
        object BiggerThan : Comparator(">")
        object Equals : Comparator("=")
    }
}
