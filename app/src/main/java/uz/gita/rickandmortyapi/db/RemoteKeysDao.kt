package uz.gita.rickandmortyapi.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.gita.rickandmortyapi.model.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE repoId = :id")
    fun remoteKeysResultDataId(id: String): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    fun clearRemoteKeys()
}