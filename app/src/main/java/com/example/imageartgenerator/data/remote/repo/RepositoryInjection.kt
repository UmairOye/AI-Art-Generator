package com.example.imageartgenerator.data.remote.repo

import com.example.imageartgenerator.domain.repository.ArtRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryInjection {

    @Provides
    @Singleton

    fun provideRepository(artRepositoryImplementation: ArtRepositoryImplementation): ArtRepository{
        return artRepositoryImplementation
    }

}