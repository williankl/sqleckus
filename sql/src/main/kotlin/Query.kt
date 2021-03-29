import internal.JsonHelper
import internal.use_cases.query.ExecuteQueryUseCase
import internal.use_cases.query.InnerJoinUseCase
import internal.use_cases.query.SelectUseCase
import internal.use_cases.query.WhereUseCase
import models.SqlCode
import models.SqlQuery
import models.Table

object Query {
    fun select(selection: SqlQuery.Selection) =
        SelectUseCase(selection)

    fun SqlCode.innerJoin(innerJoin: SqlQuery.InnerJoin) =
        InnerJoinUseCase(this, innerJoin)

    fun SqlCode.where(where: SqlQuery.Where) =
        WhereUseCase(this, where)

    inline fun <reified T> SqlCode.Executable.executeQuery(connection: SQLeckusConnection, mapWith: Table) =
        JsonHelper.parseResult<T>(ExecuteQueryUseCase(this, connection.retrieveConnection()), mapWith)

    inline fun <reified T> SqlCode.Final.executeQuery(connection: SQLeckusConnection, mapWith: Table) =
        JsonHelper.parseResult<T>(ExecuteQueryUseCase(this, connection.retrieveConnection()), mapWith)
}
