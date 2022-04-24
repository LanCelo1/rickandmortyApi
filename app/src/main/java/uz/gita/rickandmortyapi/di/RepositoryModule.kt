package uz.gita.rickandmortyapi.di

import androidx.paging.ExperimentalPagingApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.rickandmortyapi.repository.CharacterRepository
import uz.gita.rickandmortyapi.repository.CharacterRepositoryImpl

@ExperimentalPagingApi
@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun bindRepository(repositoryImpl: CharacterRepositoryImpl) : CharacterRepository
}