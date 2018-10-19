package id.arieridwan.sportdbsample

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.GsonBuilder
import id.arieridwan.sportdbsample.data.repository.MatchRepository
import id.arieridwan.sportdbsample.data.repository.MatchRepositoryImpl
import id.arieridwan.sportdbsample.data.service.ApiService
import id.arieridwan.sportdbsample.ui.lastmatch.LastMatchPresenter
import id.arieridwan.sportdbsample.ui.nextmatch.NextMatchPresenter
import id.arieridwan.sportdbsample.ui.today.TodayPresenter
import id.arieridwan.sportdbsample.util.CoroutinesContextProvider
import okhttp3.OkHttpClient
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by arieridwan on 30/09/18.
 */

class BaseApplication: Application() {

    private val module : Module = module {
        single<ApiService> {
            val okHttpClient =  OkHttpClient.Builder().build()
            val gson = GsonBuilder().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            retrofit.create(ApiService::class.java)
        }
        // Single instance of MatchRepository
        single<MatchRepository> { MatchRepositoryImpl(get()) }
        single { CoroutinesContextProvider() }
        // Simple Presenter Factory
        factory { LastMatchPresenter(get(), get()) }
        factory { NextMatchPresenter(get(), get()) }
        factory { TodayPresenter(get(), get()) }
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        startKoin(this, listOf(module))
    }

}