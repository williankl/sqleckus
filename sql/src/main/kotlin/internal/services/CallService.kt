package internal.services

import internal.use_cases.call.CreateSchemaUseCase
import internal.use_cases.call.CreateTableUseCase
import internal.use_cases.call.InsertIntoTableUseCase
import models.SqlCall
import java.sql.Connection

@PublishedApi
internal object CallService {
    fun handleCall(connection: Connection, call: SqlCall) {
        when(call){
            is SqlCall.CreateSchema -> CreateSchemaUseCase(connection, call)
            is SqlCall.DropSchema -> TODO()
            is SqlCall.CreateTable -> CreateTableUseCase(connection, call)
            is SqlCall.DropTable -> TODO()
            is SqlCall.InsertItem<*> -> connection.insertItem(call as SqlCall.InsertItem<Any>)
            is SqlCall.DeleteItem<*> -> TODO()
            is SqlCall.UpdateItem<*> -> TODO()
        }
    }

    private fun Connection.insertItem(call: SqlCall.InsertItem<Any>){
        InsertIntoTableUseCase(this, call)
    }
}