package applications

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    lateinit var appContext: Context
    override fun onCreate() {
        super.onCreate()
        appContext = this
        startKoin {
            androidContext(appContext)
            modules(appModule)
        }
            //FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

    }
}
