package internal.use_cases.call

import models.Schema
import models.Table
import java.sql.Connection

internal object DropTableUseCase {
    operator fun invoke(connection: Connection, schema: Schema, table: Table) {
        val dropStatement = "DROP TABLE IF EXISTS ${schema.name}.${table.name};"

        val sql = "$dropStatement;"
        println(sql).let { connection.prepareCall(sql).execute() }
    }
}
