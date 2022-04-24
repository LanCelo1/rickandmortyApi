package uz.gita.rickandmortyapi.db

import androidx.paging.PagingSource
import androidx.room.*
import uz.gita.rickandmortyapi.model.Result

@Dao
interface CharacterDao {
    @Query("SELECT * FROM result_data")
    fun getAllDataWithDB() : PagingSource<Int, Result>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(result : Result)

    @Query("DELETE FROM result_data")
    suspend fun clearAllResults()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<Result>)
}