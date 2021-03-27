package internal.use_cases.call

import models.SqlCall
import java.sql.Connection

object DropTableUseCase {
    operator fun invoke(connection: Connection, call: SqlCall.DropTable) {
        val dropStatement = "DROP TABLE IF EXISTS ${call.schema.name}.${call.table.name};"

        val sql = "$dropStatement;"
        println(sql).let { connection.prepareCall(sql).execute() }
    }
}
