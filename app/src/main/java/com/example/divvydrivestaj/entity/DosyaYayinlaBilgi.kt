package com.example.divvydrivestaj.entity

import com.google.gson.annotations.SerializedName

data class DosyaYayinlaBilgi(
    @SerializedName("DosyaAdi")
    val dosyaAdi:String,
    @SerializedName("KlasorYolu")
    val klasorYolu:String
)