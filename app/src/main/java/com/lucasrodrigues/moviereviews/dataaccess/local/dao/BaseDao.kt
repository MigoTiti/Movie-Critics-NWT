package com.lucasrodrigues.moviereviews.dataaccess.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSynchronous(vararg items: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSynchronous(item: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: T): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg items: T): Completable

    @Update
    fun updateSynchronous(item: T): Int

    @Update
    fun update(item: T): Completable
}