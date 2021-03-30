package models

sealed class SqlOperator(val sql: String) {
    sealed class Logic(sql: String) : SqlOperator(sql) {
        object And : Logic("AND")
        object Or : Logic("OR")
    }

    sealed class Comparator(sql: String) : SqlOperator(sql) {
        object SmallerThan : Comparator("<")
        object BiggerThan : Comparator(">")
        object Equals : Comparator("=")
    }
}
