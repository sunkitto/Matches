package com.sunkitto.matches.di

import android.content.Context
import androidx.room.Room
import com.sunkitto.matches.data.database.MatchesDatabase
import com.sunkitto.matches.data.repository.StatisticsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideMatchesDatabase(
        @ApplicationContext context: Context
    ): MatchesDatabase =
        Room.databaseBuilder(
            context = context,
            klass = MatchesDatabase::class.java,
            name = "matches.db"
        ).build()

    @Provides
    fun provideStatisticsRepository(
        matchesDatabase: MatchesDatabase
    ): StatisticsRepositoryImpl =
        StatisticsRepositoryImpl(matchesDatabase = matchesDatabase)
}