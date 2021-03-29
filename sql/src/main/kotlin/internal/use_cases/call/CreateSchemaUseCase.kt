package internal.use_cases.call

import models.SqlCall
import java.sql.Connection

internal object CreateSchemaUseCase {
    operator fun invoke(connection: Connection, call: SqlCall.CreateSchema) {
        val sql = "CREATE SCHEMA IF NOT EXISTS ${call.schema.name};"
        println(sql).let { connection.prepareCall(sql).execute() }
    }
}
