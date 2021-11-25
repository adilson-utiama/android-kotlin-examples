package com.asuprojects.kotlincustomcomponents.fragments.architecture.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.asuprojects.kotlincustomcomponents.fragments.architecture.room.model.MyItem

@Dao
interface MyItemDAO : BaseDAO<MyItem> {

    @Query("SELECT * FROM my_item")
    fun all(): LiveData<List<MyItem>>

    @Query("SELECT * FROM my_item")
    fun listAll(): List<MyItem>

}