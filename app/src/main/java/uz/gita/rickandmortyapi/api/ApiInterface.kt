package uz.gita.rickandmortyapi.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.gita.rickandmortyapi.model.CharacterData

interface ApiInterface {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page : Int = 1
    ):Response<CharacterData>


}