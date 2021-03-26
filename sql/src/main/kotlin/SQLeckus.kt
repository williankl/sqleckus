import errors.SQLecusException
import internal.services.CallService
import internal.services.QueryService
import models.DatabaseType
import models.SqlCall
import models.SqlQuery
import models.Table
import java.sql.Connection
import java.sql.DriverManager

class SQLeckus {

    private var connection: Connection? = null

    fun retrieveConnection() = connection

    fun startConnection(url: String) {
        if (connection == null) connection = DriverManager.getConnection(url)
    }

    fun startConnection(
        databaseType: DatabaseType,
        host: String,
        port: String,
        db: String,
        usr: String,
        pwd: String
    ) {
        if (connection == null)
            connection = DriverManager.getConnection(
                buildUrl(databaseType, host, port, db, usr, pwd)
            )
    }

    inline fun <reified T> query(query: SqlQuery, onTable: Table): List<T> =
        retrieveConnection()?.let { conn ->
            QueryService.handleQuery(conn,query, onTable)
        } ?: throw SQLecusException.NoDatabaseConnection



    fun call(call: SqlCall) =
        retrieveConnection()?.let { conn ->
            CallService.handleCall(conn, call)
        } ?: throw SQLecusException.NoDatabaseConnection

    private fun buildUrl(databaseType: DatabaseType, host: String, port: String, db: String, usr: String, pwd: String) =
        "jdbc:${databaseType.key}://$host:$port/$db?user=$usr&password=$pwd"
}