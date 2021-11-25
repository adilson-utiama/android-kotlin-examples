package com.asuprojects.kotlincustomcomponents.prototypes.expandableitem

class ItemWithList(
    var title: String,
    var list: MutableList<CheckableItem>,
    var withCheckbox: Boolean,
    var checked: Boolean
) {
}