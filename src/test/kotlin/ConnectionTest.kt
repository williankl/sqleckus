import models.Schema
import models.SqlCall
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import java.sql.SQLException

class ConnectionTest {

    private var sql: SQLeckus? = null

    private val localUrl =
        "jdbc:postgresql://localhost:5432/test_db?user=postgres&password=1995"

    private val badDriverUrl =
        "jdbc:postgres://localhost:5432/test_db?user=postgres&password=1995"

    private val badAddressUrl =
        "jdbc:postgresql://localhost:5432/test_db?user=postgres&password=1996"

    @Before
    fun `before tests`() {
        sql = SQLeckus()
    }

    @Test
    fun `should connect to local db`() {
        expectThat(sql?.startConnection(localUrl)) isEqualTo Unit
    }

    @Test
    fun `should make query if connected to a db`() {
        sql?.startConnection(localUrl)
        expectThat(
            sql?.call(SqlCall.CreateSchema(Schema("name")))
        ) isEqualTo Unit
    }

    @Test
    fun `should throw error when bad driver url is passed`() {
        expectThrows<SQLException> { sql?.startConnection(badDriverUrl) }
    }

    @Test
    fun `should throw error when bad address url is passed`() {
        expectThrows<SQLException> { sql?.startConnection(badAddressUrl) }
    }

    @Test
    fun `should throw if no connection is made before attempting a call or query`() {
        expectThrows<SQLException> {
            sql?.call(SqlCall.CreateSchema(Schema("name")))
        }
    }
}
