package models

sealed class SqlCode(val sql: String) {
    class Executable(sql: String) : SqlCode(sql)
    class Final(sql: String) : SqlCode(sql)
}
