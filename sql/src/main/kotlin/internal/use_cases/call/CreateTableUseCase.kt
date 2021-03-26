package internal.use_cases.call

import models.SqlCall
import java.sql.Connection

internal object CreateTableUseCase {
    operator fun invoke(connection: Connection, call: SqlCall.CreateTable) {
        val createStatement = "CREATE TABLE IF NOT EXISTS ${call.schema.name}.${call.table.name}"

        val columnsStatement =
            call.table.columns
                .joinToString(separator = " , ") {
                    "${it.name} ${it.type.code}"
                }

        val sql = "$createStatement($columnsStatement);"

        println(sql).let { connection.prepareCall(sql).execute() }
    }
}
