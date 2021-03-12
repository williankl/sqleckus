package models

sealed class SqlCall {
    data class CreateSchema(val schema: Schema): SqlCall()
    data class DropSchema(val schema: Schema): SqlCall()

    data class CreateTable(val schema: Schema, val table: Table): SqlCall()
    data class DropTable(val schema: Schema, val table: Table): SqlCall()

    data class InsertItem<T>(val schema: Schema, val table: Table, val item: T): SqlCall()
    data class DeleteItem<T>(val schema: Schema, val table: Table, val item: T): SqlCall()
    data class UpdateItem<T>(val schema: Schema, val table: Table, val item: T): SqlCall()
}