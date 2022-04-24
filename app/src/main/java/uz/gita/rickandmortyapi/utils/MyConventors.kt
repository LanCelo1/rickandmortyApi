package uz.gita.rickandmortyapi.utils

import androidx.room.TypeConverter
import uz.gita.rickandmortyapi.model.Location
import uz.gita.rickandmortyapi.model.Origin
import com.google.gson.Gson


class MyConventors {
        @TypeConverter
        fun fromLocation(location : Location) : String = location.name

        @TypeConverter
        fun toLocation(name : String) : Location = Location(name,name)

        @TypeConverter
        fun fromOrigin(origin : Origin) : String = origin.name

        @TypeConverter
        fun toOrigin(name : String) : Origin = Origin(name,name)

        @TypeConverter
        fun listToJson(value: List<String>?) = Gson().toJson(value)

        @TypeConverter
        fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}