package di

import SQLeckus
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

object SqlConnectionModule {
    operator fun invoke() = DI.Module("sql-kleckus-connection") {
        bind<SQLeckus>() with singleton {
            SQLeckus()
        }
    }
}
