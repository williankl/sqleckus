package models

sealed class DatabaseType(val key: String) {
    object Postgres : DatabaseType("postgresql")
    object MySql : DatabaseType("mysql")
}
