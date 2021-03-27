package internal.use_cases.call

import models.SqlCall
import java.sql.Connection

internal object DeleteItemUseCase {
    operator fun invoke(connection: Connection, call: SqlCall.DeleteItem) {
        val deleteStatement = "DELETE FROM ${call.schema.name}.${call.table.name}"

        val whereStatement =
            call.condition.let {
                "WHERE ${it.column.name} ${it.operator.sql} ${it.value}"
            }

        val sql = "$deleteStatement $whereStatement;"
        println(sql).let { connection.prepareCall(sql).execute() }
    }
}
