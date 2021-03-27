package internal.use_cases.query

import models.SqlQuery
import java.sql.Connection
import java.sql.ResultSet

@PublishedApi
internal object SelectUseCase {
    operator fun invoke(connection: Connection, query: SqlQuery.Selection): ResultSet {
        val selectStatement = "SELECT * FROM ${query.schema.name}.${query.table.name} ${query.table.name}"
        val whereStatement = "WHERE ${query.table.name}.${query.by.name} = ${query.by.name}"

        val sql = "$selectStatement $whereStatement;"

        return println(sql).let { connection.createStatement().executeQuery(sql) }
    }
}
