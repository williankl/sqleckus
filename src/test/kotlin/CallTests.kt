import models.*
import org.junit.Before
import org.junit.Test

class CallTests {

    private var sql: SQLeckus? = null

    private val localUrl =
        "jdbc:postgresql://localhost:5432/test_db?user=postgres&password=1995"

    data class TestClass(
        val integer: Int,
        val text: String
    )

    private val klass =
        TestClass(
            integer = 5,
            text = "text"
        )

    private val testColumnOne =
        Column(
            name = "integer",
            type = SqlType.BigInt
        )

    private val testColumnTwo =
        Column(
            name = "text",
            type = SqlType.VarChar(50)
        )

    private val table =
        Table(
            name = "testTable",
            columns = listOf(
                testColumnOne,
                testColumnTwo
            )
        )

    private val badTable =
        Table(
            name = "badTestTable",
            columns = listOf(
                testColumnOne,
                testColumnOne
            )
        )

    private val schema = Schema(name = "testSchema")

    @Before
    fun `before tests`() {
        sql = SQLeckus()
        sql?.startConnection(localUrl)
        sql?.call(SqlCall.CreateSchema(schema))
    }

    @Test
    fun `should correctly insert an table to the database`() {
        sql?.call(
            SqlCall.CreateTable(schema, table)
        )

        sql?.call(
            SqlCall.InsertItem(
                schema = schema,
                table = table,
                item = klass
            )
        )
    }
}
