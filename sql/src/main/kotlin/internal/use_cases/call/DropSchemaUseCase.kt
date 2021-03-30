package internal.use_cases.call

import models.Schema
import java.sql.Connection

internal object DropSchemaUseCase {
    operator fun invoke(
        connection: Connection,
        schema: Schema,
        forceDrop: Boolean = false
    ) {
        val dropStatement = "DROP SCHEMA IF EXISTS ${schema.name}"
        val cascadeStatement = "CASCADE"

        val sql =
            if (forceDrop)
                "$dropStatement $cascadeStatement;"
            else
                "$dropStatement;"
        println(sql).let { connection.prepareCall(sql).execute() }
    }
}
