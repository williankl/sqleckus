package models

sealed class SqlCode(val sql: String) {
    class Executable(sql: String) : SqlCode(sql)
    class TerminalLogic(sql: String) : SqlCode(sql)
}
