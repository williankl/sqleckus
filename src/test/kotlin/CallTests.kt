import models.*
import org.junit.After
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

    private val secondKlass =
        TestClass(
            integer = 10,
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

    private val schema = Schema(name = "testSchema")

    @Before
    fun `before tests`() {
        sql = SQLeckus()
        sql?.run {
            startConnection(localUrl)
            call(SqlCall.DropSchema(schema, forceDrop = true))
            call(SqlCall.CreateSchema(schema))
        }
    }

    @After
    fun `after tests`(){
        sql?.closeConnection()
    }

    @Test
    fun `should correctly create table in the database`() {
        sql?.run { call(SqlCall.CreateTable(schema, table)) }
    }

    @Test
    fun `should correctly insert an item to a table in the database`() {
        sql?.run {
            call(SqlCall.CreateTable(schema, table))

            call(
                SqlCall.InsertItem(
                    schema = schema,
                    table = table,
                    item = klass
                )
            )
        }
    }

    @Test
    fun `should correctly drop a table in the database`() {
        sql?.run {
            call(SqlCall.CreateTable(schema, table))
            call(SqlCall.DropTable(schema = schema, table = table))
        }
    }

    @Test
    fun `should drop correctly a table with content in the database`() {
        sql?.run {
            call(SqlCall.CreateTable(schema, table))
            call(
                SqlCall.InsertItem(
                    schema = schema,
                    table = table,
                    item = klass
                )
            )
            call(SqlCall.DropTable(schema = schema, table = table))
        }
    }

    @Test
    fun `should correctly delete an item from the database`() {
        sql?.run {
            call(
                SqlCall.CreateTable(schema, table)
            )

            call(
                SqlCall.InsertItem(
                    schema = schema,
                    table = table,
                    item = klass
                )
            )

            call(
                SqlCall.InsertItem(
                    schema = schema,
                    table = table,
                    item = secondKlass
                )
            )

            call(
                SqlCall.DeleteItem(
                    schema = schema,
                    table = table,
                    condition = SqlStatement.TableConditionStatement(
                        column = testColumnOne,
                        operator = SqlOperator.Comparator.Equals,
                        value = 5
                    )
                )
            )
        }
    }

    @Test
    fun `should correctly update an item from the database`() {
        sql?.run {
            call(
                SqlCall.CreateTable(schema, table)
            )

            call(
                SqlCall.InsertItem(
                    schema = schema,
                    table = table,
                    item = klass
                )
            )

            call(
                SqlCall.UpdateItem(
                    schema = schema,
                    table = table,
                    changeSet = listOf(
                        SqlStatement.SetStatement(
                            column = testColumnOne,
                            toValue = 7
                        )
                    ),
                    condition = SqlStatement.TableConditionStatement(
                        column = testColumnOne,
                        operator = SqlOperator.Comparator.Equals,
                        value = 5
                    )
                )
            )
        }
    }
}
