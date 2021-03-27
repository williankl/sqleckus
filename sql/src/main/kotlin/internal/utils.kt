import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility

internal fun KClass<out Any>.retrieveCallableNames() =
    this.members.map { callable -> callable.name }

internal fun Any.retrieveCallableKeyValuePair(): List<Pair<String, String>?> =
    this::class.members
        .filter { it.visibility == KVisibility.PUBLIC }
        .map { callable ->
            if (callable is KProperty && callable.visibility == KVisibility.PUBLIC)
                Pair(callable.name, callable.getter.call(this).toString())
            else null
        }
