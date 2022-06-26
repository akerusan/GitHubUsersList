package com.example.githubuserlist.di

import android.content.Context
import androidx.room.Room
import com.example.githubuserlist.data.database.UsersDatabase
import com.example.githubuserlist.data.network.GithubUsersApi
import com.example.githubuserlist.utils.Constants.Companion.USERS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * A [Module] for the database
 * Provides an instance of [UsersDatabase] to Hilt, which can inject it when necessary
 */
@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        UsersDatabase::class.java,
        USERS_DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideDao(database: UsersDatabase) = database.usersDao()

}