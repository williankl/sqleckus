import errors.SQLecusException
import models.DatabaseType
import java.sql.Connection
import java.sql.DriverManager

class SQLeckusConnection {
    private var connection: Connection? = null

    fun retrieveConnection() = connection ?: throw SQLecusException.NoDatabaseConnection

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

    fun closeConnection() {
        connection
            ?.let { it.close() }
            ?.also { connection = null }
    }

    private fun buildUrl(databaseType: DatabaseType, host: String, port: String, db: String, usr: String, pwd: String) =
        "jdbc:${databaseType.key}://$host:$port/$db?user=$usr&password=$pwd"
}
