package models

sealed class SqlCall {
    data class CreateSchema(val schema: Schema) : SqlCall()
    data class DropSchema(val schema: Schema, val forceDrop: Boolean = false) : SqlCall()

    data class CreateTable(val schema: Schema, val table: Table) : SqlCall()
    data class DropTable(val schema: Schema, val table: Table) : SqlCall()

    data class InsertItem<T>(val schema: Schema, val table: Table, val item: T) : SqlCall()

    data class UpdateItem(
        val schema: Schema,
        val table: Table,
        val changeSet: List<SqlStatement.SetStatement>,
        val condition: SqlStatement.TableConditionStatement
    ) : SqlCall()

    data class DeleteItem(
        val schema: Schema,
        val table: Table,
        val condition: SqlStatement.TableConditionStatement
    ) : SqlCall()
}
