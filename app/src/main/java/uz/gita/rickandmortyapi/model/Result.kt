package uz.gita.rickandmortyapi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.rickandmortyapi.model.Location
import uz.gita.rickandmortyapi.model.Origin
import java.io.Serializable

@Entity(tableName = "result_data")
data class Result(
    @PrimaryKey(autoGenerate = true)
    val id_database : Int? = null,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) : Serializable