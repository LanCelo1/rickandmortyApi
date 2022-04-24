package uz.gita.rickandmortyapi.di

import android.content.Context
import androidx.room.Room
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.rickandmortyapi.api.ApiInterface
import uz.gita.rickandmortyapi.db.CharacterDatabase
import uz.gita.rickandmortyapi.di.anotation.OtherApiClient
import uz.gita.rickandmortyapi.di.anotation.OtherDatabaseClient
import uz.gita.rickandmortyapi.ui.adapter.paging.PagerSource
import uz.gita.rickandmortyapi.utils.Constants.BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @OtherApiClient
    @Singleton
    @Provides
    fun bindInfoApi(@ApplicationContext context: Context) : ApiInterface {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY}

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(ChuckInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(ApiInterface::class.java)
    }
    @OtherDatabaseClient
    @Provides
    fun bindDatabase(@ApplicationContext context: Context) : CharacterDatabase {
        val database = Room.databaseBuilder(
            context,
            CharacterDatabase::class.java,
            "info_database"
        ).build()
        return database
    }
    @Provides
    fun bindPagingSource(@OtherApiClient api : ApiInterface, @OtherDatabaseClient db: CharacterDatabase) : PagerSource =  PagerSource(api,db)
}