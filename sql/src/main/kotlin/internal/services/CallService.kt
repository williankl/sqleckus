package internal.services

import internal.use_cases.call.CreateTableUseCase
import internal.use_cases.call.InsertIntoTableUseCase
import models.SqlCall
import java.sql.Connection

internal object CallService {
    fun handleCall(connection: Connection, call: SqlCall) {
        when(call){
            is SqlCall.CreateSchema -> TODO()
            is SqlCall.DropSchema -> TODO()
            is SqlCall.CreateTable -> CreateTableUseCase(connection, call)
            is SqlCall.DropTable -> TODO()
            is SqlCall.InsertItem<*> -> InsertIntoTableUseCase(connection, call)
            is SqlCall.DeleteItem<*> -> TODO()
            is SqlCall.UpdateItem<*> -> TODO()
        }
    }
}