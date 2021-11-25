package com.asuprojects.kotlincustomcomponents.fragments.lists.recyclerinside

import com.asuprojects.kotlincustomcomponents.fragments.lists.recyclerinside.models.HorizontalRVModel
import com.asuprojects.kotlincustomcomponents.fragments.lists.recyclerinside.models.VerticalRVModel

data class Colors(
    var objectsArray: ArrayList<VerticalRVModel> = arrayListOf(
        VerticalRVModel(
            "Category #1", arrayListOf("SubCategory #1.1", "SubCategory #1.2"),
            arrayListOf(
                // SubCategory #1.1
                arrayListOf(
                    HorizontalRVModel(
                        "#DA70D6",
                        "Orchid"
                    ),
                    HorizontalRVModel(
                        "#FA8072",
                        "Salmon"
                    ),
                    HorizontalRVModel(
                        "#FDF5E6",
                        "Old Lace"
                    ),
                    HorizontalRVModel(
                        "#00FFFF",
                        "Aqua"
                    ),
                    HorizontalRVModel(
                        "#2E8B57",
                        "Sea Green"
                    )
                ),
                // SubCategory #1.2
                arrayListOf(
                    HorizontalRVModel(
                        "#2F4F4F",
                        "Dark Slate Gray"
                    ),
                    HorizontalRVModel(
                        "#F0FFF0",
                        "Honeydew"
                    ),
                    HorizontalRVModel(
                        "#DCDCDC",
                        "Gainsboro"
                    )
                )
            )
        ),
        VerticalRVModel(
            "Category #2", arrayListOf("SubCategory #2.1"),
            arrayListOf(
                // SubCategory #2.1
                arrayListOf(
                    HorizontalRVModel(
                        "#FFE4B5",
                        "Moccasin"
                    ),
                    HorizontalRVModel(
                        "#AFEEEE",
                        "Pale Turquoise"
                    ),
                    HorizontalRVModel(
                        "#9400D3",
                        "Dark Violet"
                    ),
                    HorizontalRVModel(
                        "#3CB371",
                        "Medium Sea Green"
                    )
                )
            )
        ),
        VerticalRVModel(
            "Category #3", arrayListOf("SubCategory #3.1", "SubCategory #3.2"),
            arrayListOf(
                // SubCategory #3.1
                arrayListOf(
                    HorizontalRVModel(
                        "#FF6347",
                        "Tomato"
                    ),
                    HorizontalRVModel(
                        "#4682B4",
                        "Steel Blue"
                    ),
                    HorizontalRVModel(
                        "#778899",
                        "Light Slate Gray"
                    ),
                    HorizontalRVModel(
                        "#191970",
                        "Midnight Blue"
                    ),
                    HorizontalRVModel(
                        "#A52A2A",
                        "Brown"
                    )
                ),
                // SubCategory #3.2
                arrayListOf(
                    HorizontalRVModel(
                        "#FFF8DC",
                        "Cornsilk"
                    ),
                    HorizontalRVModel(
                        "#FF00FF",
                        "Magenta"
                    ),
                    HorizontalRVModel(
                        "#7CFC00",
                        "Lawn Green"
                    ),
                    HorizontalRVModel(
                        "#000000",
                        "Black"
                    ),
                    HorizontalRVModel(
                        "#00BFFF",
                        "Deep Sky Blue"
                    ),
                    HorizontalRVModel(
                        "#6495ED",
                        "Cornflower Blue"
                    ),
                    HorizontalRVModel(
                        "#FF8C00",
                        "Dark Orange"
                    ),
                    HorizontalRVModel(
                        "#20B2AA",
                        "Light Sea Green"
                    ),
                    HorizontalRVModel(
                        "#FFC0CB",
                        "Pink"
                    )
                )
            )
        ),
        VerticalRVModel(
            "Category #4", arrayListOf("SubCategory #4.1", "SubCategory #4.2"),
            arrayListOf(
                // SubCategory #4.1
                arrayListOf(
                    HorizontalRVModel(
                        "#DDA0DD",
                        "Plum"
                    ),
                    HorizontalRVModel(
                        "#FFF5EE",
                        "Seashell"
                    ),
                    HorizontalRVModel(
                        "#FFDEAD",
                        "Navajo White"
                    ),
                    HorizontalRVModel(
                        "#00FF00",
                        "Lime"
                    ),
                    HorizontalRVModel(
                        "#F0E68C",
                        "Khaki"
                    )
                ),
                // SubCategory #4.2
                arrayListOf(
                    HorizontalRVModel(
                        "#FAEBD7",
                        "Antique White"
                    ),
                    HorizontalRVModel(
                        "#C71585",
                        "Medium Violet Red"
                    ),
                    HorizontalRVModel(
                        "#6B8E23",
                        "Olive Drab"
                    ),
                    HorizontalRVModel(
                        "#FF4500",
                        "Orange Red"
                    ),
                    HorizontalRVModel(
                        "#FFF0F5",
                        "Lavender Blush"
                    )
                )
            )
        ),
        VerticalRVModel(
            "Category #5", arrayListOf("SubCategory #5.1", "SubCategory #5.2"),
            arrayListOf(
                // SubCategory #5.1
                arrayListOf(
                    HorizontalRVModel(
                        "#9966CC",
                        "Amethyst"
                    )
                ),
                // SubCategory #5.2
                arrayListOf(
                    HorizontalRVModel(
                        "#7B68EE",
                        "Medium Slate Blue"
                    ),
                    HorizontalRVModel(
                        "#800000",
                        "Maroon"
                    ),
                    HorizontalRVModel(
                        "#FFA07A",
                        "Light Salmon"
                    ),
                    HorizontalRVModel(
                        "#E6E6FA",
                        "Lavender"
                    ),
                    HorizontalRVModel(
                        "#FFE4C4",
                        "Bisque"
                    )
                )
            )
        )
    )
)