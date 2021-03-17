import models.*

fun Schema.create(sql: SQLeckus){
    sql.call(SqlCall.CreateSchema(this))
}


fun Schema.addTable(sql: SQLeckus, table: Table){
    sql.call(SqlCall.CreateTable(this, table))
}