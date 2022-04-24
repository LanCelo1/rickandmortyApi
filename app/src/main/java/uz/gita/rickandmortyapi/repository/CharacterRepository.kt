package uz.gita.rickandmortyapi.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import uz.gita.rickandmortyapi.model.CharacterData
import uz.gita.rickandmortyapi.model.Result

interface CharacterRepository {
    suspend fun getAllInfo(page: Int) : Response<CharacterData>
    suspend fun insertInfoToData(result: Result)

    fun getAllInfoFromDatabase() : Flow<PagingData<Result>>
}