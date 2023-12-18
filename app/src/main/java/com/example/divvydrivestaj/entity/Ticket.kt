package com.example.divvydrivestaj.entity

import com.google.gson.annotations.SerializedName


data class Ticket(
    @SerializedName("KullaniciAdi")
    val kullaniciAdi:String,
    @SerializedName("ID")
    val ID:String,
    @SerializedName("Sonuc")
    val sonuc:Boolean
)

data class Kullanici(
    @SerializedName("KullaniciAdi")
    val kullaniciAdi: String,
    @SerializedName("Sifre")
    val sifre:String
)
