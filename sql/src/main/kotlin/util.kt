import errors.SQLecusException
import models.Column
import models.SqlType
import models.Table
import kotlin.reflect.KClass

private const val strType = "kotlin.String"
private const val floatType = "kotlin.Float"
private const val intType = "kotlin.Int"
private const val boolType = "kotlin.Boolean"

fun generateTableForClass(klass: KClass<*>) =
    klass.constructors.first()
        .parameters
        .map { it.name.orEmpty() to it.type.toString() }
        .map { primitiveType ->
            when (primitiveType.second) {
                strType -> Column(
                    name = primitiveType.first,
                    type = SqlType.UniqueCandidate.VarChar(255)
                )
                floatType -> Column(
                    name = primitiveType.first,
                    type = SqlType.Float
                )
                intType -> Column(
                    name = primitiveType.first,
                    type = SqlType.UniqueCandidate.Integer()
                )
                boolType -> Column(
                    name = primitiveType.first,
                    type = SqlType.Binary
                )
                else -> throw SQLecusException.CouldNotParseTypeException
            }
        }
        .let { columns ->
            Table(
                name = klass.simpleName.orEmpty(),
                columns = columns
            )
        }
