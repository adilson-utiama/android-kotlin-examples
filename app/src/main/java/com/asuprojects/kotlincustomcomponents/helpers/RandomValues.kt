package com.asuprojects.kotlincustomcomponents.helpers

class RandomValues {

    companion object {

        fun generateStrings(quantity: Int): MutableList<String>{
            val list = mutableListOf<String>()
            for(string in 1..quantity){
                list.add("Item $string")
            }
            return list
        }

        fun getTexts(): MutableList<String> {
            return mutableListOf(
                "This class is a helper for creating a PDF file for given print attributes. It is useful for implementing printing via the native Android graphics APIs.",
                "This class represents the attributes of a print job. These attributes describe how the printed content should be laid out. For example, the print attributes may state that the content should be laid out on a letter size with 300 DPI (dots per inch) resolution, have a margin of 10 mills (thousand of an inch) on all sides, and be black and white.",
                "The following are top voted examples for showing how to use android.print.pdf.PrintedPdfDocument. These examples are extracted from open source projects. You can vote up the examples you like and your votes will be used in our system to generate more good examples.",
                "This class computes the page width, page height, and content rectangle from the provided print attributes and these precomputed values can be accessed via getPageWidth(), getPageHeight(), and getPageContentRect(), respectively. ",
                "The startPage(int) methods creates pages whose PageInfo is initialized with the precomputed values for width, height, and content rectangle.",
                "Os recursos modernos da linguagem Kotlin permitem que você se concentre em expressar suas ideias e escreva menos código de texto clichê. Dessa forma, também haverá menos código para testar e fazer manutenção.",
                "Melhore a qualidade do seu app com o Kotlin. Os tipos @Nullable e @NonNull são integrados ao sistema de tipos do Kotlin para que você evite NullPointerExceptions. O Kotlin também conta com muitos outros recursos de linguagem para evitar erros comuns de programação."
            )
        }
    }
}