package com.asuprojects.kotlincustomcomponents.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.exifinterface.media.ExifInterface
import com.asuprojects.kotlincustomcomponents.R

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageUtilsK {

    companion object {

        const val IMAGE_VERTICAL:Int = 1
        const val IMAGE_HORIZONTAL:Int = 2

        fun bitmapToFile(imagePath: String, bitmap: Bitmap) {
            try{
                val file = File(imagePath)
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                try {
                    if(!file.exists()){
                        file.createNewFile()
                    }
                    val fileOutputStream = FileOutputStream(file)
                    fileOutputStream.write(outputStream.toByteArray())
                    fileOutputStream.close()
                } catch (e: IOException) {
                    throw RuntimeException()
                }
            }catch(e: IOException){
                throw IOException("Error: ${e.message}")
            }
        }

        fun drawableToBitmap(context: Context, drawableResource: Int): Bitmap {
            try{
                return BitmapFactory.decodeResource(context.resources, drawableResource)
            }catch(e: Exception){
                throw Exception("Is not a Drawable resource")
            }
        }

        fun getBitmap(context: Context, imagePath: String): Bitmap {
            return try{
                BitmapFactory.decodeFile(imagePath)
            } catch(e: Exception){
                //throw Exception("File is not a valid bitmap")
                drawableToBitmap(context, R.drawable.take_photo_bg)
            }
        }

        fun getImageOrientation(imagePath: String): Int {
            val image = File(imagePath)
            if(image.exists()){
                val bitmap = BitmapFactory.decodeFile(imagePath)
                val width = bitmap.width
                val height = bitmap.height
                return if(height > width){
                    IMAGE_VERTICAL
                }else{
                    IMAGE_HORIZONTAL
                }
            }else{
                throw IOException("File not exist")
            }
        }

        fun generateRoundedBitmapDrawable(
            context: Context,
            bitmap: Bitmap,
            cornerRadius: Float = 25f
        ): RoundedBitmapDrawable {
            val roundedBitmap = RoundedBitmapDrawableFactory.create(context.resources, bitmap)
            roundedBitmap.cornerRadius = cornerRadius
            return roundedBitmap
        }

        /**
         *  Gera um thumbnail do video
         */
        fun retrieveVideoFrameFromVideo(context: Context, videoPath: String): Bitmap? {
            var bitmap: Bitmap? = null
            var mediaMetaDataretriever: MediaMetadataRetriever? = null
            try {
                mediaMetaDataretriever = MediaMetadataRetriever()
                mediaMetaDataretriever.setDataSource(context, Uri.parse(videoPath))

                val bitmapThumb = mediaMetaDataretriever.getFrameAtTime(
                    1,
                    MediaMetadataRetriever.OPTION_CLOSEST
                )

                bitmap = bitmapThumb
            } catch (e: Exception) {
                throw Exception("Exception in retrieveVideoFrameFromVideo: ${e.message}")
            } finally {
                mediaMetaDataretriever?.release()
            }

            return bitmap
        }

        fun retrieveVideoThumbFromVideo(context: Context, videoPath: String,
                                        imageDestination: String, ratio: Float = 1.0F, quality: Int = 100): File {
            val fileDestination = File(imageDestination)
            //var bitmap: Bitmap? = null
            var mediaMetaDataretriever: MediaMetadataRetriever? = null
            try {
                mediaMetaDataretriever = MediaMetadataRetriever()
                mediaMetaDataretriever.setDataSource(context, Uri.parse(videoPath))

                val bitmapThumb = mediaMetaDataretriever.getFrameAtTime(
                    1,
                    MediaMetadataRetriever.OPTION_CLOSEST
                )

                 //val bitmap = bitmapThumb
                val outputStream = ByteArrayOutputStream()
                bitmapThumb?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                if(!fileDestination.exists()){
                    fileDestination.createNewFile()
                }
                val fileOutputStream = FileOutputStream(fileDestination)
                fileOutputStream.write(outputStream.toByteArray())
                fileOutputStream.close().also {
                    createScaledRotatedBitmapFile(fileDestination, ratio, quality)
                }

            } catch (e: Exception) {
                throw Exception("Exception in retrieveVideoFrameFromVideo: ${e.message}")
            } finally {
                mediaMetaDataretriever?.release()
            }

            return fileDestination
        }

        @Throws(IllegalArgumentException::class)
        fun scaleBitmap(bitmap: Bitmap, targetWidth: Int): Bitmap {
            if (targetWidth > 0) {
                val originalWidth = bitmap.width
                val originalHeight = bitmap.height
                try {
                    val targetHeight = originalHeight / (originalWidth / targetWidth)
                    return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false)
                } catch (e: Exception) {

                }
            } else {
                throw IllegalArgumentException("Invalid Target Width Value.")
            }
            return bitmap
        }

        fun compressBitmap(bitmap: Bitmap, quality: Int) {
            //Bitmap.CompressFormat.JPEG, Bitmap.CompressFormat.PNG, Bitmap.CompressFormat.WEBP
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        }

        fun loadImage(filePath: String): Bitmap? {
            var exif: ExifInterface? = null
            try {
                exif = ExifInterface(filePath)
            } catch (e: IOException) {
                throw IOException(e.message)
            }
            exif.getAttribute(ExifInterface.TAG_ORIENTATION)?.also {
                when (it.toInt()) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> {
                        return openImageAndRotate(filePath, 90f)
                    }
                    ExifInterface.ORIENTATION_ROTATE_180 -> {
                        return openImageAndRotate(filePath, 180f)
                    }
                    ExifInterface.ORIENTATION_ROTATE_270 -> {
                        return openImageAndRotate(filePath, 270f)
                    }
                    ExifInterface.ORIENTATION_NORMAL -> {
                        return openImageAndRotate(filePath, 0f)
                    }
                }
            }
            return null
        }

        private fun openImageAndRotate(filePath: String, angle: Float): Bitmap {
            try {
                val bitmap = BitmapFactory.decodeFile(filePath)
                // Prepara a operação de rotação com o ângulo escolhido
                val matrix = Matrix()
                matrix.postRotate(angle)

                //Cria um novo bitmap a partir do original ja com a rotacao corrigida
                return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            } catch (e: Exception) {
                throw  RuntimeException(e.message)
            }
        }

        fun loadBitmap(imagePath: String): Bitmap {
            val file = File(imagePath)
            if(!file.exists()) throw IOException("File not exists")
            val angle = getCorrectImageAngle(imagePath)
            val bitmap = BitmapFactory.decodeFile(imagePath)
            val matrix = Matrix()
            matrix.postRotate(angle)
            return  Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }

        fun getCorrectImageAngle(imagePath: String): Float {
            var angle = 0f
            var exif: ExifInterface? = null
            try {
                exif = ExifInterface(imagePath)
            } catch (e: IOException) {
                throw IOException(e.message)
            }
            exif.getAttribute(ExifInterface.TAG_ORIENTATION)?.also {
                when (it.toInt()) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> {
                        angle = 90f
                    }
                    ExifInterface.ORIENTATION_ROTATE_180 -> {
                        angle = 180f
                    }
                    ExifInterface.ORIENTATION_ROTATE_270 -> {
                        angle = 270f
                    }
                    ExifInterface.ORIENTATION_NORMAL -> {
                        angle = 0f
                    }
                }
            }
            return angle
        }

        fun createScaledRotatedBitmapFile(imagePath: String, ratio: Float, quality: Int = 100) {
            val imageFile = File(imagePath)
            if(imageFile.exists()){
                createScaledRotatedBitmapFile(imageFile, ratio, quality)
            }else{
                throw RuntimeException("File must exist to proceed")
            }
        }

        fun createScaledRotatedBitmapFile(imageFile: File, ratio: Float, quality: Int = 100) {
            if(imageFile.exists()){
                if (ratio > 1.0 || ratio < 0.0) {
                    throw IllegalArgumentException("Invalid ratio value, must to be from 0.0 to 1.0")
                }
                if (quality > 100 || quality < 0) {
                    throw IllegalArgumentException("Invalid quality value, must to be from 0 to 100")
                }
                try {
                    val angle = getCorrectImageAngle(imageFile.absolutePath)
                    val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
                    val targetWidth = (bitmap.width * ratio).toInt()
                    val targetHeight = (bitmap.height * ratio).toInt()
                    // Prepara a operação de rotação com o ângulo escolhido
                    val matrix = Matrix()
                    matrix.postRotate(angle)

                    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false)

                    //Cria um novo bitmap a partir do original ja com a rotacao corrigida
                    val createdBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.width, scaledBitmap.height, matrix, true)

                    val file = File(imageFile.absolutePath)
                    val outputStream = ByteArrayOutputStream()
                    createdBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                    try {
                        if(!file.exists()){
                            file.createNewFile()
                        }
                        val fileOutputStream = FileOutputStream(file)
                        fileOutputStream.write(outputStream.toByteArray())
                        fileOutputStream.close()
                    } catch (e: IOException) {
                        throw RuntimeException()
                    }

                } catch (e: Exception) {
                    throw  RuntimeException(e.message)
                }
            }else{
                throw RuntimeException("File must exist to proceed")
            }
        }

        fun createScaledRotatedBitmap(imageFile: File, ratio: Float, quality: Int = 100): Bitmap {
            if(imageFile.exists()){
                if (ratio > 1.0 || ratio < 0.0) {
                    throw IllegalArgumentException("Invalid ratio value, must to be from 0.0 to 1.0")
                }
                if (quality > 100 || quality < 0) {
                    throw IllegalArgumentException("Invalid quality value, must to be from 0 to 100")
                }
                try {
                    val angle = getCorrectImageAngle(imageFile.absolutePath)
                    val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
                    val targetWidth = (bitmap.width * ratio).toInt()
                    val targetHeight = (bitmap.height * ratio).toInt()
                    // Prepara a operação de rotação com o ângulo escolhido
                    val matrix = Matrix()
                    matrix.postRotate(angle)

                    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false)

                    //Cria um novo bitmap a partir do original ja com a rotacao corrigida
                    val createdBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.width, scaledBitmap.height, matrix, true)

                    val file = File(imageFile.absolutePath)
                    val outputStream = ByteArrayOutputStream()
                    createdBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                    try {
                        if(!file.exists()){
                            file.createNewFile()
                        }
                        val fileOutputStream = FileOutputStream(file)
                        fileOutputStream.write(outputStream.toByteArray())
                        fileOutputStream.close()
                    } catch (e: IOException) {
                        throw RuntimeException()
                    }

                    return createdBitmap

                } catch (e: Exception) {
                    throw  RuntimeException(e.message)
                }
            }else{
                throw RuntimeException("File must exist to proceed")
            }
        }

//        fun reduceAndRotateImage(imagePath: String, ratio: Float, quality: Int) {
//            if (ratio > 1.0 || ratio < 0.0) {
//                throw IllegalArgumentException("Invalid ratio value, must to be from 0.0 to 1.0")
//            }
//            if (quality > 100 || quality < 0) {
//                throw IllegalArgumentException("Invalid quality value, must to be from 0 to 100")
//            }
//            //val bitmap = BitmapFactory.decodeFile(imagePath)
//            val bitmap = loadImage(imagePath)!!
//            val targetWidth = (bitmap.width * ratio).toInt()
//            val targetHeight = (bitmap.height * ratio).toInt()
//
//            val file = File(imagePath)
//            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false)
//            val outputStream = ByteArrayOutputStream()
//            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
//            try {
//                file.createNewFile()
//                val fileOutputStream = FileOutputStream(file)
//                fileOutputStream.write(outputStream.toByteArray())
//                fileOutputStream.close()
//            } catch (e: IOException) {
//                throw RuntimeException()
//            }
//        }

        fun reduceImage(imagePath: String, ratio: Float, quality: Int) {
            if (ratio > 1.0 || ratio < 0.0) {
                throw IllegalArgumentException("Invalid ratio value, must to be from 0.0 to 1.0")
            }
            if (quality > 100 || quality < 0) {
                throw IllegalArgumentException("Invalid quality value, must to be from 0 to 100")
            }
            val bitmap = BitmapFactory.decodeFile(imagePath)
            val targetWidth = (bitmap.width * ratio).toInt()
            val targetHeight = (bitmap.height * ratio).toInt()

            val file = File(imagePath)
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false)
            val outputStream = ByteArrayOutputStream()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            try {
                file.createNewFile()
                val fileOutputStream = FileOutputStream(file)
                fileOutputStream.write(outputStream.toByteArray())
                fileOutputStream.close()
            } catch (e: IOException) {
                throw RuntimeException()
            }
        }

        fun rescaleBitmap(imagePath: String, ratio: Float, quality: Int): Bitmap {
            if (ratio > 1.0 || ratio < 0.0) {
                throw IllegalArgumentException("Invalid ratio value, must to be from 0.0 to 1.0")
            }
            if (quality > 100 || quality < 0) {
                throw IllegalArgumentException("Invalid quality value, must to be from 0 to 100")
            }
            val bitmap = BitmapFactory.decodeFile(imagePath)
            val targetWidth = (bitmap.width * ratio).toInt()
            val targetHeight = (bitmap.height * ratio).toInt()
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false)
            val outputStream = ByteArrayOutputStream()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            return scaledBitmap
        }

        fun rescaleBitmap(bitmap: Bitmap, ratio: Float, quality: Int): Bitmap {
            if (ratio > 1.0 || ratio < 0.0) {
                throw IllegalArgumentException("Invalid ratio value, must to be from 0.0 to 1.0")
            }
            if (quality > 100 || quality < 0) {
                throw IllegalArgumentException("Invalid quality value, must to be from 0 to 100")
            }
            val targetWidth = (bitmap.width * ratio).toInt()
            val targetHeight = (bitmap.height * ratio).toInt()
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false)
            val outputStream = ByteArrayOutputStream()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            return scaledBitmap
        }


    } //end companion object scope




}