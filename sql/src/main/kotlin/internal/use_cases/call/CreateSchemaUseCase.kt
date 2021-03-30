package internal.use_cases.call

import models.Schema
import java.sql.Connection

internal object CreateSchemaUseCase {
    operator fun invoke(connection: Connection, schema: Schema) {
        val sql = "CREATE SCHEMA IF NOT EXISTS ${schema.name};"
        println(sql).let { connection.prepareCall(sql).execute() }
    }
}
