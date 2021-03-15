package internal.services

import errors.SQLecusException
import internal.use_cases.query.InnerJoinUseCase
import internal.use_cases.query.SelectUseCase
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.Column
import models.SqlQuery
import models.SqlType
import models.Table
import java.sql.Connection
import java.sql.ResultSet

internal object QueryService {
    inline fun <reified T> handleQuery(connection: Connection, sqlQuery: SqlQuery, onTable: Table): List<T> =
        when(sqlQuery){
            is SqlQuery.Selection -> transformQuery(SelectUseCase(connection, sqlQuery), onTable)
            is SqlQuery.InnerJoin -> transformQuery(InnerJoinUseCase(connection, sqlQuery), onTable)
        }


    private inline fun <reified T> transformQuery(resultSet: ResultSet, table: Table): List<T> =
        resultSet.apply { next() }
            .let { set ->
                if (set.isAfterLast) return@let listOf<T>()

                val result = mutableListOf<T>()

                while(set.isAfterLast.not()){
                    val pairs = mutableListOf<Pair<String, Any>>()
                    table.columns.forEach {
                        pairs.add(it.keyValuePair(set))
                    }
                    result.add(Json.decodeFromString(pairs.toJsonString()))
                    set.next()
                }

                result
            }


    private fun Column.keyValuePair(set: ResultSet) =
        when(type){
            is SqlType.VarChar,
            is SqlType.LongText,
            is SqlType.Date -> Pair(this.name, set.getString(this.name))

            is SqlType.Integer,
            is SqlType.BigInt -> Pair(this.name, set.getInt(this.name))

            is SqlType.Binary -> Pair(this.name, set.getBoolean(this.name))
        }

    private fun List<Pair<String, Any>>.toJsonString() =
        joinToString(separator = ","){ (key, value) ->
            when(value){
                is String,
                is Int,
                is Boolean -> "\"$key\":\"$value\""
                else -> throw SQLecusException.CouldNotParseTypeException
            }
        }.let { "{$it}" }

}