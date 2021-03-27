package internal.services

import internal.use_cases.call.*
import internal.use_cases.call.CreateTableUseCase
import internal.use_cases.call.InsertIntoTableUseCase
import models.SqlCall
import java.sql.Connection

@PublishedApi
internal object CallService {
    fun handleCall(connection: Connection, call: SqlCall) {
        when (call) {
            is SqlCall.CreateSchema -> CreateSchemaUseCase(connection, call)
            is SqlCall.DropSchema -> DropSchemaUseCase(connection, call)
            is SqlCall.CreateTable -> CreateTableUseCase(connection, call)
            is SqlCall.DropTable -> DropTableUseCase(connection, call)
            is SqlCall.DeleteItem -> DeleteItemUseCase(connection, call)
            is SqlCall.UpdateItem -> UpdateItemUseCase(connection, call)
            is SqlCall.InsertItem<*> -> InsertIntoTableUseCase(connection, call as SqlCall.InsertItem<Any>)
        }
    }
}
