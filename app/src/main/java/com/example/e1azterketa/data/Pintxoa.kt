package com.example.e1azterketa.data

import kotlinx.serialization.Serializable

//Data class JSONa gordetzeko
@Serializable
data class Pintxoa(
    val id:Int,
    val izena:String,
    val prezioa:Double,
    val balorazioa: Double
)
