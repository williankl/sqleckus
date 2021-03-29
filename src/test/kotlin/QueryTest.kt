import Call.createSchema
import Call.createTable
import Call.dropSchema
import Call.insertItem
import Query.executeQuery
import Query.innerJoin
import Query.where
import kotlinx.serialization.Serializable
import models.Column
import models.Schema
import models.SqlCall
import models.SqlOperator
import models.SqlQuery
import models.SqlType
import models.Table
import org.junit.After
import org.junit.Before
import org.junit.Test

class QueryTest {
    private var sql: SQLeckusConnection? = null

    private val localUrl =
        "jdbc:postgresql://localhost:5432/test_db?user=postgres&password=1995"

    @Serializable
    data class TypeOne(
        val v1: Int,
        val v2: String
    )

    @Serializable
    data class TypeTwo(
        val v1: Int,
        val v2: Int
    )

    private val typeOneClass =
        TypeOne(
            v1 = 1,
            v2 = "text"
        )

    private val typeTwoClass =
        TypeTwo(
            v1 = 1,
            v2 = 10
        )

    private val typeOneColumnOne =
        Column(
            name = "v1",
            type = SqlType.BigInt
        )

    private val typeOneColumnTwo =
        Column(
            name = "v2",
            type = SqlType.VarChar(50)
        )

    private val typeTwoColumnOne =
        Column(
            name = "v1",
            type = SqlType.BigInt
        )

    private val typeTwoColumnTwo =
        Column(
            name = "v2",
            type = SqlType.Integer
        )

    private val t1 =
        Table(
            name = "t1",
            columns = listOf(
                typeOneColumnOne,
                typeOneColumnTwo
            )
        )

    private val t2 =
        Table(
            name = "t2",
            columns = listOf(
                typeTwoColumnOne,
                typeTwoColumnTwo
            )
        )

    private val schema =
        Schema("schema")

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

    private fun `insert one item in each`() {
        sql?.apply {
            createTable(SqlCall.CreateTable(schema, t1))
            createTable(SqlCall.CreateTable(schema, t2))

            insertItem(
                SqlCall.InsertItem(
                    schema = schema,
                    table = t1,
                    item = typeOneClass
                )
            )

            insertItem(
                SqlCall.InsertItem(
                    schema = schema,
                    table = t2,
                    item = typeTwoClass
                )
            )

            insertItem(
                SqlCall.InsertItem(
                    schema = schema,
                    table = t2,
                    item = typeTwoClass.copy(v2 = 5)
                )
            )

            insertItem(
                SqlCall.InsertItem(
                    schema = schema,
                    table = t2,
                    item = typeTwoClass.copy(v1 = 6)
                )
            )
        }
    }

    @Test
    fun `should retrieve item`() {
        `insert one item in each`()
        sql?.apply {
            Query.select(
                SqlQuery.Selection(
                    schema = schema,
                    table = t1
                )
            )
                .executeQuery<TypeOne>(this, t1)
        }
    }

    @Test
    fun `should retrieve item inner joined with other item`() {
        `insert one item in each`()
        sql?.apply {
            Query.select(
                SqlQuery.Selection(
                    schema = schema,
                    table = t1
                )
            )
                .innerJoin(
                    SqlQuery.InnerJoin(
                        schema = schema,
                        table = t2,
                        on = typeTwoColumnOne,
                        condition = SqlOperator.Comparator.Equals,
                        value = typeOneClass.v1
                    )
                )
                .where(
                    SqlQuery.Where(
                        table = t2,
                        column = typeTwoColumnOne,
                        condition = SqlOperator.Comparator.Equals,
                        value = typeOneClass.v1
                    )
                )
                .executeQuery<TypeOne>(this, t1)
        }
    }
}
