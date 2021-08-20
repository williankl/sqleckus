package models

sealed class SqlType(val code: String) {
    sealed class UniqueCandidate(code: String, val isPrimaryKey: Boolean) : SqlType(code) {
        class VarChar(size: Int, isPrimaryKey: Boolean = false) : UniqueCandidate("VARCHAR($size)", isPrimaryKey)
        class Integer(isPrimaryKey: Boolean = false) : UniqueCandidate("INT", isPrimaryKey)
    }
    object Float : SqlType("FLOAT")
    object BigInt : SqlType("BIGINT")
    object Binary : SqlType("BOOLEAN")
    object Date : SqlType("Date")
}
