package com.asuprojects.kotlincustomcomponents.helpers

import android.content.Context

class ValueConversionHelper {

    companion object {

        /** complex unit: Value is raw pixels. */
        const val UNIDADE_PX = 0
        /** complex unit: Value is Device Independent Pixels */
        const val UNIDADE_DIP = 1
        /** complex unit: Value is a Scaled Pixel. */
        const val UNIDADE_SP = 2
        /** complex unit: Value is in Points. */
        const val UNIDADE_PT = 3
        /** complex unit: Value is in inches. */
        const val UNIDADE_IN = 4
        /** complex unit: Value is in millimeters. */
        const val UNIDADE_MM = 5

        /**
         *  Conversao de valores pt (Points) para inch (Polegadas)
         *  pt = in / 72 (in to pt)
         *
         *  @param ptValue Valor em pontos (pt)
         *  @return Float Valor convertido para INCH (Polegadas)
         */
        fun convertPTtoINCH(ptValue: Float): Float {
            return ( ptValue / 72 )
        }

        /**
         *  Conversao de valores inch (Polegadas) pata pt (Points)
         *  pt = in / 72 (in to pt)
         *  pt = px * 72 / dpi (px to pt)
         *
         *  @param inchValue Valor em inch (INCH)
         *  @return Int Valor convertido para PT (Points)
         */
        fun convertINCHtoPT(inchValue: Float): Int {
            return ( inchValue * 72 ).toInt()
        }

        /**
         * Converte um valor de dados complexos descompactado, mantendo uma dimensão em sua flutuação final
         * valor em pontos. Os dois parâmetros <var> unit </var> e <var> value </var>
         * são como em {@link #TYPE_DIMENSION}.
         *
         * @param activity Activity
         * @param unidadeConversao A unidade para a qual converter.
         * @param unidadeConverter O valor ao qual aplicar a unidade.
         *
         * @return O valor complexo do ponto flutuante multiplicado pelo valor apropriado
         * métricas dependendo da sua unidade.
        */
        fun converter(context: Context, unidadeConversao: Int, unidadeConverter: Float): Float {
            val metrics = context.resources.displayMetrics

            return when(unidadeConversao){
                UNIDADE_PX -> unidadeConverter
                UNIDADE_DIP -> unidadeConverter * metrics.density
                UNIDADE_SP -> unidadeConverter * metrics.scaledDensity
                UNIDADE_PT -> unidadeConverter * metrics.xdpi * (1.0f/72)
                UNIDADE_IN -> unidadeConverter * metrics.xdpi
                UNIDADE_MM -> unidadeConverter * metrics.xdpi * (1.0f/25.4f)
                else -> 0f
            }
        }

    }
}