package com.example.e1azterketa.data
import android.content.Context
import android.util.Log
import kotlinx.serialization.json.Json

class DatuakIrakurri {

    //Funtzioa JSONa irakurtzeko

    fun datuakKargatu(context:Context, fitx: String): List<Pintxoa> {
        return try {
            val jsonString = context.assets.open(fitx)
                .bufferedReader()
                .use { it.readText() }

            Log.d ("EL string: ",jsonString)

            Json { ignoreUnknownKeys = true }
                .decodeFromString<List<Pintxoa>>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }


}