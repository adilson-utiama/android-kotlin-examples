package com.asuprojects.kotlincustomcomponents.helpers

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.*
import java.io.*


class FileManager {

    companion object {

        fun saveFileFromUri(context: Context, uri: Uri, destinationFile: File) {
            //GlobalScope.launch(Dispatchers.IO) {
                var inputStream: InputStream? = null
                var bufferedOutputStream: BufferedOutputStream? = null
                try {
                    inputStream = context.contentResolver.openInputStream(uri)
                    bufferedOutputStream = BufferedOutputStream(
                        FileOutputStream(
                            destinationFile,
                            false
                        )
                    )
                    val buf = ByteArray(1024)
                    inputStream!!.read(buf)
                    do {
                        bufferedOutputStream.write(buf)
                    } while (inputStream.read(buf) != -1)
                } catch (e: IOException) {
                    Log.e("ERROR", ">>> Error: ${e.message}")
                } finally {
                    try {
                        inputStream?.close()
                        bufferedOutputStream?.close()
                    } catch(e: IOException) {
                        Log.e("ERROR", ">>> Error: ${e.message}")
                    }
                }
            //}
        }

        fun saveFileFromUri(context: Context, uri: Uri, destinationPath: String) {
            GlobalScope.launch(Dispatchers.IO) {
                var inputStream: InputStream? = null
                var bufferedOutputStream: BufferedOutputStream? = null
                try {
                    inputStream = context.contentResolver.openInputStream(uri)
                    bufferedOutputStream = BufferedOutputStream(
                        FileOutputStream(
                            destinationPath,
                            false
                        )
                    )
                    val buf = ByteArray(1024)
                    inputStream!!.read(buf)
                    do {
                        bufferedOutputStream.write(buf)
                    } while (inputStream.read(buf) != -1)
                } catch (e: IOException) {
                    Log.e("ERROR", ">>> Error: ${e.message}")
                } finally {
                    try {
                        inputStream?.close()
                        bufferedOutputStream?.close()
                    } catch(e: IOException) {
                        Log.e("ERROR", ">>> Error: ${e.message}")
                    }
                }
            }
        }

        fun copyFile(context: Context, filePath: String) {
            val directory = context.getDir("teste", Context.MODE_PRIVATE)
            try{
                context.openFileOutput("image", Context.MODE_PRIVATE).use {
                    it.write(filePath.toByteArray())
                }
            }catch (e: IOException) {

            }

        }

        /**
         *  Arquivo e gravado em 'filesDir' : /data/user/0/com.asuprojects.dailynotes/files
         */
        fun writeContentTextFile(context: Context, fileName: String, fileContent: String) {
            try{
                context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                    it.write(fileContent.toByteArray())
                }
            }catch (err: Exception) {

            }
        }

        @Throws(Exception::class)
        fun createNestedDirectory(context: Context, dirName: String): File? {
            //Directory getDir() : /data/user/0/com.asuprojects.dailynotes/app_{dirName}}
            return context.getDir(dirName, Context.MODE_PRIVATE)
        }

        fun writeToFile(data: String, context: Context) {
            try {
                val outputStreamWriter = OutputStreamWriter(
                    context.openFileOutput(
                        "config.txt",
                        Context.MODE_PRIVATE
                    )
                )
                outputStreamWriter.write(data)
                outputStreamWriter.close()
            } catch (e: IOException) {
                Log.e("Exception", "File write failed: ${e.message}")
            }
        }

        fun readFromFile(context: Context, fileName: String) {
            var ret = ""
            val inputStream = context.openFileInput(fileName)
            try {
                if (inputStream != null) {
                    val inputStreamReader = InputStreamReader(inputStream)
                    val bufferedReader = BufferedReader(inputStreamReader)
                    var receiveString: String? = ""
                    val stringBuilder = StringBuilder()
                    while (bufferedReader.readLine().also { receiveString = it } != null) {
                        stringBuilder.append("\n").append(receiveString)
                    }
                    inputStream.close()
                    ret = stringBuilder.toString()
                }
            } catch (e: FileNotFoundException) {
                Log.e("TESTE", "File not found: ${e.message}")
            } catch (e: IOException) {
                Log.e("TESTE", "Can not read file: ${e.message}")
            }

        }

        fun saveBitmapToFile(bitmap: Bitmap, file: File) {
            var out: FileOutputStream? = null
            try {
                out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out)
            } catch (e: IOException) {
                Log.e("TESTE", "Can not save the bitmap: ${e.message}")
            } finally {
                out?.close()
            }
        }

    }
}