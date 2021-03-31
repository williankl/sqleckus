package internal

import errors.SQLecusException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import models.Column
import models.SqlType
import models.Table
import java.sql.ResultSet

@PublishedApi
internal object JsonHelper {

    @PublishedApi
    internal inline fun <reified T> T.retrieveItemValuePair() =
        Json.encodeToJsonElement(this)
            .jsonObject
            .entries
            .map { Pair(it.key, it.value.jsonPrimitive.content) }

    @PublishedApi
    @OptIn(ExperimentalStdlibApi::class)
    internal inline fun <reified T> parseResult(resultSet: ResultSet, mapWith: Table): List<T> =
        resultSet.apply { next() }
            .let { set ->
                val result =
                    buildList<T> {
                        while (set.isAfterLast.not()) {
                            val pairs = buildList {
                                mapWith.columns.forEach {
                                    add(it.keyValuePair(set))
                                }
                            }
                            add(Json.decodeFromString(pairs.toJsonString()))
                            set.next()
                        }
                    }
                result
            }

    @PublishedApi
    internal fun Column.keyValuePair(set: ResultSet) =
        when (type) {
            is SqlType.UniqueCandidate.VarChar,
            is SqlType.Date -> Pair(this.name, set.getString(this.name))

            is SqlType.UniqueCandidate.Integer,
            is SqlType.BigInt -> Pair(this.name, set.getInt(this.name))

            is SqlType.Binary -> Pair(this.name, set.getBoolean(this.name))
        }

    @PublishedApi
    internal fun List<Pair<String, Any>>.toJsonString() =
        joinToString(separator = ",") { (key, value) ->
            when (value) {
                is String -> "\"$key\":\"$value\""
                is Int,
                is Boolean -> "\"$key\":$value"
                else -> throw SQLecusException.CouldNotParseTypeException
            }
        }.let { "{$it}" }
}
