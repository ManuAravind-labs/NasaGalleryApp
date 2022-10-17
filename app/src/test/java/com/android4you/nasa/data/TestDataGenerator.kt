package com.android4you.nasa.data

import com.android4you.nasa.domain.model.ImageModelItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class TestDataGenerator {

    private var imageList: List<ImageModelItem> = ArrayList()

    fun getImageListResponse(): List<ImageModelItem> {
        val data = getJson("Success.json")
        val gson = Gson()
        val type = object : TypeToken<List<ImageModelItem>>() {}.type
        imageList = gson.fromJson(data, type)
        return imageList
    }

    fun getImageListEmptyResponse(): List<ImageModelItem> {
        return emptyList()
    }

    /**
     * Read json from resource
     */
    private fun getJson(path: String): String {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        val content = reader.readText()
        reader.close()
        return content
    }
}