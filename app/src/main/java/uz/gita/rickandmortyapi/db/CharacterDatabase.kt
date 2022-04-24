package uz.gita.rickandmortyapi.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.gita.rickandmortyapi.model.RemoteKeys
import uz.gita.rickandmortyapi.model.Result
import uz.gita.rickandmortyapi.utils.MyConventors

@Database(entities = [Result::class, RemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(MyConventors::class)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao() : CharacterDao
    abstract fun remoteKeyDao() : RemoteKeysDao
}