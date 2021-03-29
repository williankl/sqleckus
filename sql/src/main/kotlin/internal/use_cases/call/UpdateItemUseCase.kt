package internal.use_cases.call

import models.SqlCall
import java.sql.Connection

internal object UpdateItemUseCase {
    operator fun invoke(connection: Connection, call: SqlCall.UpdateItem) {
        val updateStatement =
            "UPDATE ${call.schema.name}.${call.table.name}"

        val setStatement =
            call.changeSet.joinToString(separator = ", ") { set ->
                "${set.column.name} = ${set.toValue}"
            }.let { "SET $it" }

        val whereStatement =
            call.condition.let {
                "${it.column.name} ${it.operator.sql} ${it.value}"
            }.let { "WHERE $it" }

        val sql = "$updateStatement $setStatement $whereStatement;"
        println(sql).let { connection.prepareCall(sql).execute() }
    }
}
