package internal.use_cases.call

import models.Schema
import models.Table
import java.sql.Connection

internal object CreateTableUseCase {
    operator fun invoke(connection: Connection, schema: Schema, table: Table) {
        val createStatement = "CREATE TABLE IF NOT EXISTS ${schema.name}.${table.name}"

        val columnsStatement =
            table.columns
                .joinToString(separator = " , ") {
                    "${it.name} ${it.type.code}"
                }

        val sql = "$createStatement ($columnsStatement);"
        println(sql).let { connection.prepareCall(sql).execute() }
    }
}
