package uz.gita.rickandmortyapi.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.gita.rickandmortyapi.api.ApiInterface
import uz.gita.rickandmortyapi.db.CharacterDatabase
import uz.gita.rickandmortyapi.di.anotation.OtherApiClient
import uz.gita.rickandmortyapi.di.anotation.OtherDatabaseClient
import uz.gita.rickandmortyapi.model.Result
import uz.gita.rickandmortyapi.ui.adapter.paging.CharacterMediator
import javax.inject.Inject

@ExperimentalPagingApi
class CharacterRepositoryImpl @Inject constructor(
    @OtherApiClient
    val api : ApiInterface,
    @OtherDatabaseClient
    val db: CharacterDatabase
) : CharacterRepository {
    override suspend fun getAllInfo(page: Int) = api.getAllCharacters(page)
    override suspend fun insertInfoToData(result : Result) = db.characterDao().insert(result)

    override fun getAllInfoFromDatabase() : Flow<PagingData<Result>> {
        if (db == null) throw IllegalStateException("Database is not initialized")

        val pagingSourceFactory = { db.characterDao().getAllDataWithDB() }
        return Pager(
            config = PagingConfig(pageSize =3 ),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = CharacterMediator(api, db)
        ).flow

        db.characterDao().getAllDataWithDB()
    }
}