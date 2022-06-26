package com.example.githubuserlist.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * [Repository] class to access the data locally [LocalDataSource] or remotely [RemoteDataSource]
 * Both DataSource classes are injected in the constructor via Hilt
 */
@ActivityRetainedScoped
class Repository @Inject constructor(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource) {

    val remote = remoteDataSource
    val local = localDataSource
}