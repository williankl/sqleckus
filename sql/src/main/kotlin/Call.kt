import internal.use_cases.call.CreateSchemaUseCase
import internal.use_cases.call.CreateTableUseCase
import internal.use_cases.call.DeleteItemUseCase
import internal.use_cases.call.DropSchemaUseCase
import internal.use_cases.call.DropTableUseCase
import internal.use_cases.call.InsertItemUseCase
import internal.use_cases.call.UpdateItemUseCase
import models.Schema
import models.SqlStatement
import models.Table

object Call {
    fun SQLeckusConnection.createSchema(schema: Schema) =
        CreateSchemaUseCase(this.retrieveConnection(), schema)

    fun SQLeckusConnection.dropSchema(
        schema: Schema,
        forceDrop: Boolean = false
    ) = DropSchemaUseCase(this.retrieveConnection(), schema, forceDrop)

    fun SQLeckusConnection.createTable(
        schema: Schema,
        table: Table
    ) = CreateTableUseCase(this.retrieveConnection(), schema, table)

    fun SQLeckusConnection.dropTable(
        schema: Schema,
        table: Table
    ) = DropTableUseCase(this.retrieveConnection(), schema, table)

    inline fun <reified T> SQLeckusConnection.insertItem(
        schema: Schema,
        table: Table,
        item: T
    ) = InsertItemUseCase(this.retrieveConnection(), schema, table, item)

    fun SQLeckusConnection.deleteItem(
        schema: Schema,
        table: Table,
        condition: SqlStatement.TableConditionStatement
    ) = DeleteItemUseCase(this.retrieveConnection(), schema, table, condition)

    fun SQLeckusConnection.updateItem(
        schema: Schema,
        table: Table,
        changeSet: List<SqlStatement.SetStatement>,
        condition: SqlStatement.TableConditionStatement
    ) = UpdateItemUseCase(this.retrieveConnection(), schema, table, changeSet, condition)
}
