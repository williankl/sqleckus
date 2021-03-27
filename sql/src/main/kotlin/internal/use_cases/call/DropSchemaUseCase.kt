package internal.use_cases.call

import models.SqlCall
import java.sql.Connection

object DropSchemaUseCase {
    operator fun invoke(connection: Connection, call: SqlCall.DropSchema) {
        val dropStatement = "DROP SCHEMA IF EXISTS ${call.schema.name}"
        val cascadeStatement = "CASCADE"

        val sql =
            if(call.forceDrop)
                "$dropStatement $cascadeStatement;"
            else
                "$dropStatement;"
        println(sql).let { connection.prepareCall(sql).execute() }
    }
}
