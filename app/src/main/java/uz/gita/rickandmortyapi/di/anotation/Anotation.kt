package uz.gita.rickandmortyapi.di.anotation

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherApiClient


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherDatabaseClient