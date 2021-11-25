package com.asuprojects.kotlincustomcomponents.helpers

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream


class ZipFileManager {

    companion object {

        @Throws(IOException::class)
        fun zip(files: Array<String>, zipFile: String) {
            var origin: BufferedInputStream
            val zipOutputStream = ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile)))
            zipOutputStream.use { out ->
                val bufferSize = 6 * 1024
                val data = ByteArray(bufferSize)
                for (i in files.indices) {
                    val fi = FileInputStream(files[i])
                    origin = BufferedInputStream(fi, bufferSize)
                    try {
                        val entry =
                            ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1))
                        out.putNextEntry(entry)
                        var count: Int
                        while (origin.read(data, 0, bufferSize).also { count = it } != -1) {
                            out.write(data, 0, count)
                        }
                    } finally {
                        origin.close()
                    }
                }
            }
        }

        fun unzip(zipFile: String, location: String) {
            try {
                val f = File(location)
                if (!f.isDirectory) {
                    f.mkdirs()
                }
                ZipInputStream(FileInputStream(zipFile)).use { zin ->
                    var zipEntry: ZipEntry
                    while (zin.nextEntry.also { zipEntry = it } != null) {
                        val path = location + File.separator + zipEntry.name
                        if (zipEntry.isDirectory) {
                            val unzipFile = File(path)
                            if (!unzipFile.isDirectory) {
                                unzipFile.mkdirs()
                            }
                        } else {
                            FileOutputStream(path, false).use { fout ->
                                var c: Int = zin.read()
                                while (c != -1) {
                                    fout.write(c)
                                    c = zin.read()
                                }
                                zin.closeEntry()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}