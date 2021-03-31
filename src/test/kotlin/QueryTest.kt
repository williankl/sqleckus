import Call.createSchema
import Call.createTable
import Call.dropSchema
import Call.insertItem
import Query.and
import Query.executeQuery
import Query.innerJoin
import Query.or
import Query.where
import kotlinx.serialization.Serializable
import models.Column
import models.Schema
import models.SqlOperator
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
            type = SqlType.UniqueCandidate.VarChar(50)
        )

    private val typeTwoColumnOne =
        Column(
            name = "v1",
            type = SqlType.BigInt
        )

    private val typeTwoColumnTwo =
        Column(
            name = "v2",
            type = SqlType.UniqueCandidate.Integer()
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
            dropSchema(schema, forceDrop = true)
            createSchema(schema)
        }
    }

    @After
    fun `after tests`() {
        sql?.closeConnection()
    }

    private fun `insert one item in each`() {
        sql?.apply {
            createTable(schema, t1)
            createTable(schema, t2)

            insertItem(
                schema = schema,
                table = t1,
                item = typeOneClass
            )

            insertItem(
                schema = schema,
                table = t2,
                item = typeTwoClass
            )

            insertItem(
                schema = schema,
                table = t2,
                item = typeTwoClass.copy(v2 = 5)
            )

            insertItem(
                schema = schema,
                table = t2,
                item = typeTwoClass.copy(v1 = 6)
            )
        }
    }

    @Test
    fun `should retrieve item`() {
        `insert one item in each`()
        sql?.apply {
            Query.select(
                schema = schema,
                table = t1
            )
                .executeQuery<TypeOne>(this, t1)
        }
    }

    @Test
    fun `should retrieve item inner joined with other item`() {
        `insert one item in each`()
        sql?.apply {
            Query.select(
                schema = schema,
                table = t1
            )
                .innerJoin(
                    schema = schema,
                    table = t2,
                    on = typeTwoColumnOne,
                    condition = SqlOperator.Comparator.Equals,
                    value = typeOneClass.v1
                )
                .where(
                    schema = schema,
                    table = t2,
                    column = typeTwoColumnOne,
                    condition = SqlOperator.Comparator.Equals,
                    value = typeOneClass.v1
                )
                .executeQuery<TypeOne>(this, t1)
        }
    }

    @Test
    fun `should retrieve item with where using logic operators`() {
        `insert one item in each`()
        sql?.apply {
            Query.select(
                schema = schema,
                table = t1
            )
                .innerJoin(
                    schema = schema,
                    table = t2,
                    on = typeTwoColumnOne,
                    condition = SqlOperator.Comparator.Equals,
                    value = typeOneClass.v1
                )
                .where(
                    schema = schema,
                    table = t2,
                    column = typeTwoColumnOne,
                    condition = SqlOperator.Comparator.Equals,
                    toSchema = schema,
                    toTable = t1,
                    toColumn = typeOneColumnOne
                )
                .or(
                    schema = schema,
                    table = t2,
                    column = typeTwoColumnOne,
                    condition = SqlOperator.Comparator.Equals,
                    value = typeOneClass.v1
                )
                .and(
                    schema = schema,
                    table = t2,
                    column = typeTwoColumnOne,
                    condition = SqlOperator.Comparator.Equals,
                    value = typeOneClass.v1
                )
                .executeQuery<TypeOne>(this, t1)
        }
    }
}
