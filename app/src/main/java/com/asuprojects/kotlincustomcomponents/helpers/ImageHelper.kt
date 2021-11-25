package com.asuprojects.kotlincustomcomponents.helpers

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class ImageHelper {

    companion object {

        /**
         *  Metodo para carregar fotos tiradas pela camera do Android
         *  Corrige orientação da foto e compacta em uma nova imagem JPEG
         *
         * @param imageFile       Imagem original gerada pela camera
         * @param fileDestiny     Destino da nova Imagem gerada
         * @param maxWidth        Largura da foto desejada
         * @param quality         Qualidade da Compactação - valor 0 -> 100
         * @param deleteOriginal  Deletar a imagem original
         * @return Uri  Uri da nova imagem
         * @throws RuntimeException Caso haja um erro ao processar imagem
         */
        fun loadImageScaleCompress(imageFile: File, fileDestiny: String, maxWidth: Int,
                                   quality: Int, deleteOriginal: Boolean): Uri? {
            val fixedBitmap = fixImageOrientation(imageFile.absolutePath, deleteOriginal)
            val file = File(fileDestiny)
            fixedBitmap?.let {bitmap ->
                val originalWidth = bitmap.width
                val originalHeight = bitmap.height
                if(originalWidth > maxWidth){
                    val newHeight = originalHeight / ( originalWidth / maxWidth )
                    val scaledBitmap =
                        Bitmap.createScaledBitmap(bitmap, maxWidth, newHeight, false)
                    val outputStream = ByteArrayOutputStream()
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                    try {
                        file.createNewFile()
                        val fileOutputStream = FileOutputStream(file)
                        fileOutputStream.write(outputStream.toByteArray())
                        fileOutputStream.close()
                    } catch(e: IOException) {
                        e.printStackTrace()
                        throw RuntimeException("Não foi possivel reduzir imagem")
                    }
                }
            }
            return if(file.exists()){
                Uri.fromFile(file)
            }else{
                null
            }
        }

        fun compressBitmapToImagePNG(bitmap: Bitmap, quality: Int, fileDestination: String): File? {
            val calendar = Calendar.getInstance()
            val file = File(fileDestination, "image_${calendar.timeInMillis}.png")
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream)
            try {
                file.createNewFile()
                val fileOutputStream = FileOutputStream(file)
                fileOutputStream.write(outputStream.toByteArray())
                fileOutputStream.close()
            } catch(e: IOException) {
                e.printStackTrace()
                throw RuntimeException("Não foi possivel criar imagem")
            }
            return if(file.exists()){
                file
            }else{
                null
            }
        }

        fun compressBitmapToImageJPEG(bitmap: Bitmap, quality: Int, fileDestination: String): File? {
            val calendar = Calendar.getInstance()
            val file = File(fileDestination, "image_${calendar.timeInMillis}.jpg")
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            try {
                file.createNewFile()
                val fileOutputStream = FileOutputStream(file)
                fileOutputStream.write(outputStream.toByteArray())
                fileOutputStream.close()
            } catch(e: IOException) {
                e.printStackTrace()
                throw RuntimeException("Não foi possivel criar imagem")
            }
            return if(file.exists()){
                file
            }else{
                null
            }
        }

        fun scaleDown(originalBitmap: Bitmap, maxImageSize: Float, filter: Boolean): Bitmap {
            val ratio = Math.min((maxImageSize / originalBitmap.width), (maxImageSize / originalBitmap.height))
            val width = Math.round(ratio * originalBitmap.width)
            val height = Math.round(ratio * originalBitmap.height)
            return Bitmap.createScaledBitmap(originalBitmap, width, height, filter)
        }

        /**
         * Reduz Bitmap baseado na proporcao
         *
         * @param orginalBitmap  Bitmap de Origem
         * @param ratio          Proporcao, valor deve ser de 0.1 a 1.0
         * @param filter         Se a filtragem bilinear deve ou não ser usada ao dimensionar o
         *                       bitmap. Se isso for verdade, a filtragem bilinear será usada quando
         *                       escala com melhor qualidade de imagem à custa de pior desempenho.
         *                       Se isso for falso, a escala do vizinho mais próximo será usada, o que
         *                       terá uma qualidade de imagem pior, mas é mais rápida. Padrão recomendado
         *                       é definir o filtro como 'true', pois o custo da filtragem bilinear é
         *                       normalmente mínimo e a qualidade de imagem aprimorada é significativa.
         * @return O novo bitmap em escala ou o bitmap de origem, se nenhuma escala for necessária
         * @throws IllegalArgumentException  Se ratio for > 1.0f
         */
        fun reduceBitmap(originalBitmap: Bitmap, ratio: Float, filter: Boolean): Bitmap{
            if(ratio > 1.0f){
                throw IllegalArgumentException("Ratio value is not valid, use values from 0.1 to 1.0")
            }
            val width = ( ratio * originalBitmap.width ).toInt()
            val height = ( ratio * originalBitmap.height ).toInt()
            return Bitmap.createScaledBitmap(originalBitmap, width, height, filter)
        }

        fun reduceBitmap(originalBitmap: Bitmap, larguraMaxima: Int): Bitmap {
            val largura = originalBitmap.width
            val altura = originalBitmap.height
            val alturadesejada = altura / ( largura / larguraMaxima )
            return Bitmap.createScaledBitmap(originalBitmap, larguraMaxima, alturadesejada, true)
        }

        fun reduceImage(caminhoFoto: String, larguraMaxima: Int): File {
            val bitmap = BitmapFactory.decodeFile(caminhoFoto)
            val larguraOriginal = bitmap.width
            val alturaOriginal = bitmap.height

            val larguraDestino = larguraMaxima

            val file = File(caminhoFoto)

            if(larguraOriginal > larguraDestino){
                val alturaDestino = alturaOriginal / (larguraOriginal / larguraDestino)
                val scaledBitmap =
                    Bitmap.createScaledBitmap(bitmap, larguraDestino, alturaDestino, true)

                val outputStream = ByteArrayOutputStream()

                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)

                try {
                    file.createNewFile()
                    val fileOutputStream = FileOutputStream(file)
                    fileOutputStream.write(outputStream.toByteArray())
                    fileOutputStream.close()
                } catch(e: IOException) {
                    e.printStackTrace()
                    throw RuntimeException("Não foi possivel reduzir imagem")
                }
            }
            return file
        }

        fun fixImageOrientation(caminhoFoto: String, deleteOriginal: Boolean): Bitmap? {
            var bitmap: Bitmap? = null
            var exif: ExifInterface? = null
            try{
                exif = ExifInterface(caminhoFoto)
            }catch(e: IOException){
                e.printStackTrace()
            }
            exif?.apply {
                val orientation = this.getAttribute(ExifInterface.TAG_ORIENTATION)
                val codigoOrientacao = orientation?.toInt()

                when(codigoOrientacao){
                    ExifInterface.ORIENTATION_NORMAL -> {
                        bitmap = abreImagemERotaciona(caminhoFoto, 0, deleteOriginal)
                    }
                    ExifInterface.ORIENTATION_ROTATE_90 -> {
                        bitmap = abreImagemERotaciona(caminhoFoto, 90, deleteOriginal)
                    }
                    ExifInterface.ORIENTATION_ROTATE_180 -> {
                        bitmap = abreImagemERotaciona(caminhoFoto, 180, deleteOriginal)
                    }
                    ExifInterface.ORIENTATION_ROTATE_270 -> {
                        bitmap = abreImagemERotaciona(caminhoFoto, 270, deleteOriginal)
                    }
                    else -> {
                        bitmap = abreImagemERotaciona(caminhoFoto, 0, deleteOriginal)
                    }
                }
            }

            return bitmap
        }

        fun abreImagemERotaciona(caminhoFoto: String, angulo: Int, deleteOriginal: Boolean): Bitmap {
            // Abre o Bitmap a partir do aminho do File
            val bitmap = BitmapFactory.decodeFile(caminhoFoto)

            //Prepara a operacao de rotacao com o angulo escolhido
            val matrix = Matrix()
            matrix.postRotate(angulo.toFloat())

            //Cria um novo Bitmap a partir do original ja com a rotacao aplicada
            val newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

            if(deleteOriginal){
                val deleteSucces = File(caminhoFoto).delete()
                if(deleteSucces) Log.i("TESTE", "Arquivo Original deletado com sucesso")
            }
            return newBitmap
        }



        private fun useBitmapFactoryExample(context: Context, drawableResource: Int) {
            val options = BitmapFactory.Options()

            options.inPreferQualityOverSpeed = true
            options.inJustDecodeBounds = false
            options.inDither = false
            options.inSampleSize = 1
            options.inScaled = false
            options.inPreferredConfig = Bitmap.Config.ARGB_8888

            val bmpSource = BitmapFactory.decodeResource(context.resources, drawableResource, options)

            val matrix = Matrix()
            matrix.postScale(.1f, .1f)
            val scaledBitmap = Bitmap.createBitmap(
                bmpSource,
                0,
                0,
                bmpSource.width,
                bmpSource.height,
                matrix,
                true
            )
        }

    }
}