package di

import internal.SqlImplementation
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import services.SqlConnection

object SqlConnectionModule {
    operator fun invoke() = DI.Module("sql-connection"){
        bind<SqlConnection>() with singleton {
            SqlImplementation()
        }
    }
}