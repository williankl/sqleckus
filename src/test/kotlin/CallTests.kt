
import Call.createSchema
import Call.createTable
import Call.deleteItem
import Call.dropSchema
import Call.dropTable
import Call.insertItem
import Call.updateItem
import kotlinx.serialization.Serializable
import models.Column
import models.Schema
import models.SqlCall
import models.SqlOperator
import models.SqlStatement
import models.SqlType
import models.Table
import org.junit.After
import org.junit.Before
import org.junit.Test

class CallTests {

    private var sql: SQLeckusConnection? = null

    private val localUrl =
        "jdbc:postgresql://localhost:5432/test_db?user=postgres&password=1995"

    @Serializable
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
        sql = SQLeckusConnection()
        sql?.run {
            startConnection(localUrl)
            dropSchema(SqlCall.DropSchema(schema, forceDrop = true))
            createSchema(SqlCall.CreateSchema(schema))
        }
    }

    @After
    fun `after tests`() {
        sql?.closeConnection()
    }

    @Test
    fun `should correctly create table in the database`() {
        sql?.run {
            createTable(SqlCall.CreateTable(schema, table))
        }
    }

    @Test
    fun `should correctly insert an item to a table in the database`() {
        sql?.run {
            createTable(SqlCall.CreateTable(schema, table))

            insertItem(
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
            createTable(SqlCall.CreateTable(schema, table))
            dropTable(SqlCall.DropTable(schema = schema, table = table))
        }
    }

    @Test
    fun `should drop correctly a table with content in the database`() {
        sql?.run {
            createTable(SqlCall.CreateTable(schema, table))
            insertItem(
                SqlCall.InsertItem(
                    schema = schema,
                    table = table,
                    item = klass
                )
            )
            dropTable(SqlCall.DropTable(schema = schema, table = table))
        }
    }

    @Test
    fun `should correctly delete an item from the database`() {
        sql?.run {
            createTable(
                SqlCall.CreateTable(schema, table)
            )

            insertItem(
                SqlCall.InsertItem(
                    schema = schema,
                    table = table,
                    item = klass
                )
            )

            insertItem(
                SqlCall.InsertItem(
                    schema = schema,
                    table = table,
                    item = secondKlass
                )
            )

            deleteItem(
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
            createTable(
                SqlCall.CreateTable(schema, table)
            )

            insertItem(
                SqlCall.InsertItem(
                    schema = schema,
                    table = table,
                    item = klass
                )
            )

            updateItem(
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
