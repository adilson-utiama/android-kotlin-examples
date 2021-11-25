package com.asuprojects.kotlincustomcomponents.prototypes.recyclerviewpager

sealed class MyPageScrollState {
    class Idle : MyPageScrollState()
    class Dragging: MyPageScrollState()
    class Settling: MyPageScrollState()
}
