import di.SqlConnectionModule
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import services.SqlConnection

object SQLeckus: DIAware {
    override val di = DI{
        import(SqlConnectionModule())
    }

    private val sql: SqlConnection by instance()

    operator fun invoke() = sql
}