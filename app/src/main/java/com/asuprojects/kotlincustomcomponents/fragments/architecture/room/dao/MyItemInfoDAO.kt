package com.asuprojects.kotlincustomcomponents.fragments.architecture.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.asuprojects.kotlincustomcomponents.fragments.architecture.room.model.MyItemInfo

@Dao
interface MyItemInfoDAO : BaseDAO<MyItemInfo> {

    @Query("SELECT * FROM my_item_info")
    fun all(): LiveData<List<MyItemInfo>>

    @Query("SELECT * FROM my_item_info")
    fun listAll(): List<MyItemInfo>

}