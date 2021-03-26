import kotlin.reflect.KClass
import kotlin.reflect.KProperty

internal fun KClass<out Any>.retrieveCallableNames() =
    this.members.map { callable -> callable.name }

internal fun KClass<out Any>.retrieveCallableKeyValuePair(): List<Pair<String, String>> =
    this.members.map { callable ->
        Pair(callable.name, callable.call(this).toString() )
    }