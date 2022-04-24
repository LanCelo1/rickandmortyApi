package uz.gita.rickandmortyapi.ui.presenter

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import uz.gita.rickandmortyapi.model.Result

interface CharacterViewModel {
    fun getAllInfo(page: Int)
    var getAllInfoFromDb : Flow<PagingData<Result>>
    val getAllInfoLiveData : SharedFlow<List<Result>>
    val getErrorMessageLiveData : SharedFlow<String>
    val getAllInfoFromDatabaseLiveData : SharedFlow<List<Result>>
    var getAllInfo : Flow<PagingData<Result>>
    fun getAllInfoFromDatabase(): Flow<PagingData<Result>>
    fun insertDataToDatabase(result: Result)
}