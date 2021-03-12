import models.Schema
import models.SqlCall
import models.Table

fun main(){
    val sql = SQLeckus()

    sql.startConnection("wwwwww")
    sql.call(SqlCall.CreateTable(Schema("Schema"), Table("Table", listOf())))
}