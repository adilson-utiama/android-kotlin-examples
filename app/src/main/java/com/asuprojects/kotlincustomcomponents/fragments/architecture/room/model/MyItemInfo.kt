package com.asuprojects.kotlincustomcomponents.fragments.architecture.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_item_info")
class MyItemInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val info: String,
    val tipo: Int
) {
}