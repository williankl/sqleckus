package internal.use_cases.call

import models.Schema
import models.SqlType
import models.Table
import java.sql.Connection

internal object CreateTableUseCase {
    operator fun invoke(connection: Connection, schema: Schema, table: Table) {
        val createStatement = "CREATE TABLE IF NOT EXISTS ${schema.name}.${table.name}"

        val columnsStatement =
            table.columns
                .joinToString(separator = " , ") { column ->
                    val isPrimaryKey =
                        if (column.type is SqlType.UniqueCandidate)
                            (column.type as SqlType.UniqueCandidate).isPrimaryKey
                        else false

                    "${column.name} ${column.type.code}".let {
                        if (isPrimaryKey) "$it PRIMARY KEY"
                        else it
                    }
                }

        val sql = "$createStatement ($columnsStatement);"
        println(sql).let { connection.prepareCall(sql).execute() }
    }
}
