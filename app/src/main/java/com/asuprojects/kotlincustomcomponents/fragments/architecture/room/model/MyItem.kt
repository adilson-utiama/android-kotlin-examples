package com.asuprojects.kotlincustomcomponents.fragments.architecture.room.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "my_item", foreignKeys = [
        ForeignKey(
            entity = MyItemInfo::class,
            parentColumns = ["id"],
            childColumns = ["infoId"],
            onDelete = CASCADE
        )
    ]
)
data class MyItem(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val data: String,
    val descricao: String,
    val infoId: Long
)