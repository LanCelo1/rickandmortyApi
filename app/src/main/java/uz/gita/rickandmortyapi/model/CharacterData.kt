package uz.gita.rickandmortyapi.model

import java.io.Serializable

data class CharacterData(
    val info: Info,
    val results: List<Result>
): Serializable
