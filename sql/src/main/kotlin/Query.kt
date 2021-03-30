import internal.JsonHelper
import internal.use_cases.query.AndOrUseCase
import internal.use_cases.query.ExecuteQueryUseCase
import internal.use_cases.query.InnerJoinUseCase
import internal.use_cases.query.SelectUseCase
import internal.use_cases.query.WhereUseCase
import models.Column
import models.Schema
import models.SqlCode
import models.SqlOperator
import models.Table

object Query {
    fun select(
        schema: Schema,
        table: Table,
        columns: List<Column>? = null
    ) = SelectUseCase(schema, table, columns)

    fun SqlCode.innerJoin(
        schema: Schema,
        table: Table,
        on: Column,
        condition: SqlOperator,
        value: Any
    ) = InnerJoinUseCase(this, schema, table, on, condition, value)

    fun SqlCode.where(
        table: Table,
        column: Column,
        condition: SqlOperator,
        value: Any
    ) = WhereUseCase(this, table, column, condition, value)

    fun SqlCode.TerminalLogic.and(
        table: Table,
        column: Column,
        condition: SqlOperator,
        value: Any
    ) = AndOrUseCase(this, SqlOperator.Logic.And, table, column, condition, value)

    fun SqlCode.TerminalLogic.or(
        table: Table,
        column: Column,
        condition: SqlOperator,
        value: Any
    ) = AndOrUseCase(this, SqlOperator.Logic.Or, table, column, condition, value)

    inline fun <reified T> SqlCode.Executable.executeQuery(connection: SQLeckusConnection, mapWith: Table) =
        JsonHelper.parseResult<T>(ExecuteQueryUseCase(this, connection.retrieveConnection()), mapWith)

    inline fun <reified T> SqlCode.TerminalLogic.executeQuery(connection: SQLeckusConnection, mapWith: Table) =
        JsonHelper.parseResult<T>(ExecuteQueryUseCase(this, connection.retrieveConnection()), mapWith)
}
