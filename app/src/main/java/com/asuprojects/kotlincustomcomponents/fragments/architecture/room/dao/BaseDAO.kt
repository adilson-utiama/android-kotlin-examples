package com.asuprojects.kotlincustomcomponents.fragments.architecture.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDAO<T> {

    @Insert
    fun insert(obj: T): Long

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obg: T)
}