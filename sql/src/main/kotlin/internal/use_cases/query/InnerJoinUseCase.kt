package internal.use_cases.query

import models.SqlQuery
import java.lang.IllegalArgumentException
import java.sql.Connection
import java.sql.ResultSet

@PublishedApi
internal object InnerJoinUseCase {
    operator fun invoke(connection: Connection, query: SqlQuery.InnerJoin): ResultSet {
        if (query.statements.size <= 1)
            throw IllegalArgumentException("InnerJoin statement list must have at least 2 statements")

        val selectClause = "SELECT * FROM"
        val baseTable = "${query.schema.name}.${query.baseTable.name} ${query.baseTable.name}"

        val innerJoins =
            query.statements.joinToString(separator = " ") { statement ->
                val innerJoinStatement =
                    "INNER JOIN ${query.schema.name}.${statement.by1.first.name} ${statement.by1.first.name}"
                val onStatement =
                    "ON ${statement.by1.first.name}.${statement.by1.second.name} = ${statement.by2.first.name}.${statement.by2.second.name}"
                "$innerJoinStatement $onStatement"
            }

        val whereClause =
            "WHERE ${query.baseCondition.table.name}.${query.baseCondition.column.name} ${query.baseCondition.operator.sql} ${query.baseCondition.value}"

        val logicClauses =
            query.conditionsWithLogic.joinToString(separator = " ") { operator ->
                "${operator.sql} ${operator.condition.table.name}.${operator.condition.column.name} ${operator.condition.operator.sql} ${operator.condition.value}"
            }

        val sql = "$selectClause $baseTable $innerJoins $whereClause $logicClauses;"

        return println(sql).let { connection.createStatement().executeQuery(sql) }
    }
}
