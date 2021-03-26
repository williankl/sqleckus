package models

sealed class SqlType(val code: String){
    class VarChar(size: Int): SqlType("VARCHAR($size)")
    object Integer: SqlType("INT")
    object BigInt: SqlType("BIGINT")
    object Binary: SqlType("BOOLEAN")
    object Date: SqlType("Date")
}
