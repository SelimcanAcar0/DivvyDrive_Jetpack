package com.example.divvydrivestaj.entity

import com.google.gson.annotations.SerializedName

data class KlasorListesiDonenSonuc (
    @SerializedName("Mesaj")
    val mesaj:String="",
    @SerializedName("SonucListe")
    var sonucListe:List<Klasor> = emptyList(),
    @SerializedName("Sonuc")
    val sonuc:Boolean =false
)
data class DosyaListesiDonenSonuc(
    @SerializedName("Mesaj")
    val mesaj:String ="",
    @SerializedName("SonucListe")
    var sonucListe:List<Dosya> = emptyList(),
    @SerializedName("Sonuc")
    val sonuc:Boolean = false
)
data class KlasorVeDosyaIslemleriDonenSonuc(
    @SerializedName("Mesaj")
    val mesaj:String="",
    @SerializedName("Sonuc")
    val sonuc:Boolean=false
)



