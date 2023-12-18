package com.example.divvydrivestaj.entity

import com.google.gson.annotations.SerializedName

data class Dosya (
    @SerializedName("ID")
    val ID:Int,
    @SerializedName("Adi")
    val ad:String,
    @SerializedName("Boyut")
    val boyut:Int)
