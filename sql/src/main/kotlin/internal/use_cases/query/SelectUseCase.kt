package internal.use_cases.query

import models.SqlQuery
import java.sql.Connection
import java.sql.ResultSet

@PublishedApi
internal object SelectUseCase {
    operator fun invoke(connection: Connection, query: SqlQuery.Selection): ResultSet{
        val selectStatement = "SELECT * FROM ${query.schema.name}.${query.table.name} table"
        val whereStatement = "WHERE ${query.table.name}.${query.by.name} = ${query.by}"

        val sql = "$selectStatement $whereStatement;"

        return connection
            .createStatement()
            .executeQuery(sql)
            .also { println(sql) }
    }
}