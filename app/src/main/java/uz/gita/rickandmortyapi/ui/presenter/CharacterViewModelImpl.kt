package uz.gita.rickandmortyapi.ui.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uz.gita.rickandmortyapi.model.Result
import uz.gita.rickandmortyapi.repository.CharacterRepository
import uz.gita.rickandmortyapi.ui.adapter.paging.PagerSource
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CharacterViewModelImpl @Inject constructor(
    val repository: CharacterRepository,
    val pagerSource: PagerSource
) : CharacterViewModel,ViewModel() {
    override var getAllInfoFromDb: Flow<PagingData<Result>> =
        repository.getAllInfoFromDatabase().cachedIn(viewModelScope)

    override var getAllInfo: Flow<PagingData<Result>> = Pager<Int, Result>(
        PagingConfig(pageSize = 3)) {
        pagerSource
    }.flow.cachedIn(viewModelScope)

    override val getAllInfoLiveData = MutableSharedFlow<List<Result>>()
    override val getAllInfoFromDatabaseLiveData = MutableSharedFlow<List<Result>>()
    override val getErrorMessageLiveData = MutableSharedFlow<String>()
    override fun getAllInfo(page: Int) {
        Log.d("TTT","getAllInfo")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getAllInfo(page)
                if (response.isSuccessful) {
                    response?.body()?.let {
                        Log.d("TTT","viewmodel ${it.results}")
                        getAllInfoLiveData.emit(it.results!!)
                    }
                } else {
                    getErrorMessageLiveData.emit(response.message())
                }
            } catch (e: Exception) {
                getErrorMessageLiveData.emit(e.message.toString())
            }
        }
    }
    override fun insertDataToDatabase(result: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertInfoToData(result)
        }
    }

    override fun getAllInfoFromDatabase(): Flow<PagingData<Result>> {
        return repository.getAllInfoFromDatabase().cachedIn(viewModelScope)
    }

}