package uz.gita.rickandmortyapi.ui.adapter.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import uz.gita.rickandmortyapi.api.ApiInterface
import uz.gita.rickandmortyapi.db.CharacterDatabase
import uz.gita.rickandmortyapi.di.anotation.OtherApiClient
import uz.gita.rickandmortyapi.di.anotation.OtherDatabaseClient
import uz.gita.rickandmortyapi.model.Result
import java.lang.Exception
import javax.inject.Inject

class PagerSource @Inject constructor (
    @OtherApiClient
    val api : ApiInterface,
    @OtherDatabaseClient
    val db: CharacterDatabase
) : PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.minus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: 1
        var prevKey: Int? = null
        var nextKey: Int? = null
        var info: List<Result> = emptyList()
        return try {
            Log.d("TTT","PagerSource")
            val response = api.getAllCharacters(page)
            if (response.isSuccessful) {
                info = checkNotNull(response?.body()?.results?.map { it })
                prevKey = if (page == 1) null else page - 1
                nextKey = if (info.isEmpty()) null else page + 1
            }
            info.forEach {
                /**
                 * Insert to database
                 * */
                db.characterDao().insert(it)
            }
            LoadResult.Page(
                data = info,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            Log.e("TTT","PagerZSource${e.message}")
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.e("TTT","PagerSource ${e.message()}")
            return LoadResult.Error(e)
        }
    }
}
