import internal.use_cases.call.CreateSchemaUseCase
import internal.use_cases.call.CreateTableUseCase
import internal.use_cases.call.DeleteItemUseCase
import internal.use_cases.call.DropSchemaUseCase
import internal.use_cases.call.DropTableUseCase
import internal.use_cases.call.InsertItemUseCase
import internal.use_cases.call.UpdateItemUseCase
import models.SqlCall

object Call {
    fun SQLeckusConnection.createSchema(call: SqlCall.CreateSchema) =
        CreateSchemaUseCase(this.retrieveConnection(), call)

    fun SQLeckusConnection.dropSchema(call: SqlCall.DropSchema) =
        DropSchemaUseCase(this.retrieveConnection(), call)

    fun SQLeckusConnection.createTable(call: SqlCall.CreateTable) =
        CreateTableUseCase(this.retrieveConnection(), call)

    fun SQLeckusConnection.dropTable(call: SqlCall.DropTable) =
        DropTableUseCase(this.retrieveConnection(), call)

    inline fun <reified T> SQLeckusConnection.insertItem(call: SqlCall.InsertItem<T>) =
        InsertItemUseCase(this.retrieveConnection(), call)

    fun SQLeckusConnection.deleteItem(call: SqlCall.DeleteItem) =
        DeleteItemUseCase(this.retrieveConnection(), call)

    fun SQLeckusConnection.updateItem(call: SqlCall.UpdateItem) =
        UpdateItemUseCase(this.retrieveConnection(), call)
}
