package models

sealed class SqlOperator(val sql: String) {
    sealed class Logic(sql: String, val condition: SqlQuery.ConditionStatement): SqlOperator(sql){
        class And(condition: SqlQuery.ConditionStatement): Logic("AND", condition)
        class Or(condition: SqlQuery.ConditionStatement): Logic("OR", condition)
    }

    sealed class Comparator(sql: String): SqlOperator(sql){
        object LowerThan: Comparator("<")
        object HigherThan: Comparator(">")
        object Equals: Comparator("=")
    }
}