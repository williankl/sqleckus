package internal.use_cases.query

import models.SqlCode
import java.sql.Connection
import java.sql.ResultSet

@PublishedApi
internal object ExecuteQueryUseCase {
    operator fun invoke(query: SqlCode, connection: Connection): ResultSet =
        "${query.sql};".let {
            println(it)
            connection
                .createStatement()
                .executeQuery(it)
        }
}
