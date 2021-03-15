package internal

import errors.SQLecusException
import internal.services.CallService
import internal.services.QueryService
import models.DatabaseType
import models.SqlCall
import models.SqlQuery
import models.Table
import services.SqlConnection
import java.sql.Connection
import java.sql.DriverManager
import kotlin.reflect.KClass

internal class SqlImplementation: SqlConnection {

    private var connection: Connection? = null

    private fun buildUrl(databaseType: DatabaseType, host: String, port: String, db: String, usr: String, pwd: String) =
        "jdbc:${databaseType.key}://$host:$port/$db?user=$usr&password=$pwd"

    override fun startConnection(url: String) {
        if (connection == null) connection = DriverManager.getConnection(url)
    }

    override fun startConnection(
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

    override fun query(query: SqlQuery, onTable: Table, klass: KClass<*>): List<KClass<*>> =
        connection?.let { conn ->
            QueryService.handleQuery(conn,query, onTable)
        } ?: throw SQLecusException.NoDatabaseConnection



    override fun call(call: SqlCall) =
        connection?.let { conn ->
            CallService.handleCall(conn,call)
        } ?: throw SQLecusException.NoDatabaseConnection
}