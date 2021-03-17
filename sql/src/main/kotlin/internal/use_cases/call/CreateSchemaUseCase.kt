package internal.use_cases.call

import models.SqlCall
import java.sql.Connection

object CreateSchemaUseCase {
    operator fun invoke(connection: Connection, call: SqlCall.CreateSchema){
        val sql = "CREATE SCHEMA ${call.schema.name};"
        connection
            .prepareCall(sql)
            .execute()
            .also { println(sql) }
    }
}