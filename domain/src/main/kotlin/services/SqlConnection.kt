package services

import models.DatabaseType
import models.SqlCall
import models.SqlQuery
import models.Table
import kotlin.reflect.KClass

interface SqlConnection {
    fun startConnection(url: String)
    fun startConnection(
        databaseType: DatabaseType,
        host: String,
        port: String,
        db: String,
        usr: String,
        pwd: String
    )

    fun query(query: SqlQuery, onTable: Table, klass: KClass<*>): List<KClass<*>>?
    fun call(call: SqlCall)
}