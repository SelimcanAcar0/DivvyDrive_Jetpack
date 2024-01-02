package com.example.divvydrivestaj.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.divvydrivestaj.entity.DosyaYayinlaBilgi
import com.example.divvydrivestaj.entity.KlasorListesiDonenSonuc
import com.example.divvydrivestaj.entity.KlasorVeDosyaIslemleriDonenSonuc
import com.example.divvydrivestaj.repo.IServisRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class dosyaYuklemeVM:ViewModel() {
    private val repository = IServisRepo()
    private val _secilenDosya = MutableStateFlow<String?>(null)
    private val _dosyaAdi = MutableStateFlow<String?>(null)
    private val _dosyaHash = MutableStateFlow<String?>(null)
    private val _klasorVeDosyaIslemleriDonenSonuc = MutableStateFlow<KlasorVeDosyaIslemleriDonenSonuc?>(null)

    val secilenDosya:StateFlow<String?>get() = _secilenDosya;
    val dosyaAdi:StateFlow<String?>get() = _dosyaAdi;
    val dosyaHash:StateFlow<String?> get() = _dosyaHash;
    val klasorVeDosyaIslemleriDonenSonuc:StateFlow<KlasorVeDosyaIslemleriDonenSonuc?> get() = _klasorVeDosyaIslemleriDonenSonuc

    fun dosyaSec(dosya:String){
        _secilenDosya.value=dosya
    }
    fun dosyaAdiAyarla(adi:String){
        _dosyaAdi.value=adi;
    }
    fun _dosyaHash(hash:String){
        _dosyaHash.value=hash;
    }

    fun dosyaMetaDataKaydiOlustur(){
        try {
            viewModelScope.launch {
                val call = repository.dosyaMetaDataKaydiOlustur()
                call.enqueue(object : Callback<KlasorVeDosyaIslemleriDonenSonuc> {
                    override fun onFailure(call: Call<KlasorVeDosyaIslemleriDonenSonuc>, t: Throwable) {
                        Log.e("retrofit", "call failed")
                    }
                    override fun onResponse(
                        call: Call<KlasorVeDosyaIslemleriDonenSonuc>,
                        response: Response<KlasorVeDosyaIslemleriDonenSonuc>
                    ) {
                        if(response.isSuccessful) {
                            _klasorVeDosyaIslemleriDonenSonuc.value = response.body()
                            Log.e("dosyaMetaDataKaydıOlustur","${klasorVeDosyaIslemleriDonenSonuc.value}")
                        }else Log.e("dosyaMetaDataKaydıOlustur","Erişilemedi")
                    }

                })
            }}catch (e:Exception){
            Log.e("klasorListesiGetir",e.message.toString())
        }

    }
    fun dosyaParcalariYukle(ticketID:String,id:String,parcaNumarasi:Int){
        try {
            viewModelScope.launch {
                val call = repository.dosyaParcalariYukle(ticketID,id,parcaNumarasi)
                call.enqueue(object : Callback<KlasorVeDosyaIslemleriDonenSonuc> {
                    override fun onFailure(call: Call<KlasorVeDosyaIslemleriDonenSonuc>, t: Throwable) {
                        Log.e("retrofit", "call failed")
                    }
                    override fun onResponse(
                        call: Call<KlasorVeDosyaIslemleriDonenSonuc>,
                        response: Response<KlasorVeDosyaIslemleriDonenSonuc>
                    ) {
                        if(response.isSuccessful) {
                            _klasorVeDosyaIslemleriDonenSonuc.value = response.body()
                            Log.e("dosyaParcalariYukle","${klasorVeDosyaIslemleriDonenSonuc.value}")
                        }else Log.e("dosyaParcalariYukle","Erişilemedi")
                    }

                })
            }}catch (e:Exception){
            Log.e("klasorListesiGetir",e.message.toString())
        }

    }
    fun dosyaYayinla(ticketID:String,id:String,dosyaYayinlaBilgi: DosyaYayinlaBilgi){
        try {
            viewModelScope.launch {
                val call = repository.dosyaYayinla(ticketID,id,dosyaYayinlaBilgi)
                call.enqueue(object : Callback<KlasorVeDosyaIslemleriDonenSonuc> {
                    override fun onFailure(call: Call<KlasorVeDosyaIslemleriDonenSonuc>, t: Throwable) {
                        Log.e("retrofit", "call failed")
                    }
                    override fun onResponse(
                        call: Call<KlasorVeDosyaIslemleriDonenSonuc>,
                        response: Response<KlasorVeDosyaIslemleriDonenSonuc>
                    ) {
                        if(response.isSuccessful) {
                            _klasorVeDosyaIslemleriDonenSonuc.value = response.body()
                            Log.e("dosyaYayinla","${klasorVeDosyaIslemleriDonenSonuc.value}")
                        }else Log.e("dosyaYayinla","Erişilemedi")
                    }

                })
            }}catch (e:Exception){
            Log.e("klasorListesiGetir",e.message.toString())
        }

    }



}