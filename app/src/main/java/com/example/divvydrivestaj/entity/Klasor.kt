package com.example.divvydrivestaj.entity

import com.google.gson.annotations.SerializedName

data class Klasor(
    @SerializedName("ID")
    val ID:Int,
    @SerializedName("Adi")
    val ad:String)
