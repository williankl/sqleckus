package errors

import java.sql.SQLException

sealed class SQLecusException(val msg: String) : SQLException(msg) {
    object CouldNotParseTypeException : SQLecusException("Could not parse to a valid type")
    object NoDatabaseConnection : SQLecusException("Establish a connection to the database before making a call/query")
    class CouldNotFindSqlFieldOnTable(key: String, schema: String, table: String) : SQLecusException("Could not find $key equivalent on table $schema.$table")

    object InvalidAction : SQLecusException("This is awkward... An invalid call was made")
}
