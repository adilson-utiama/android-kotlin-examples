package com.asuprojects.kotlincustomcomponents.fragments.lists.recyclerinside.models

import com.asuprojects.kotlincustomcomponents.fragments.lists.recyclerinside.models.HorizontalRVModel

data class VerticalRVModel(
    val category: String,
    val subcategory: ArrayList<String>,
    val colors: ArrayList<ArrayList<HorizontalRVModel>>
)