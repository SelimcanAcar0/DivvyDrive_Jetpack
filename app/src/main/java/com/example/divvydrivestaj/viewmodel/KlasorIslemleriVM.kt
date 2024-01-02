package com.example.divvydrivestaj.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.divvydrivestaj.entity.KlasorListesiDonenSonuc
import com.example.divvydrivestaj.entity.KlasorVeDosyaIslemleriDonenSonuc
import com.example.divvydrivestaj.repo.IServisRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KlasorIslemleriVM:ViewModel() {
    private val repository = IServisRepo()

    private var _mevcutKlasorYolu= MutableStateFlow("")
    private val _mevcutKlasorListesiArray= mutableListOf<String>()
    private val _klasorListesiDonenSonuc = MutableStateFlow<KlasorListesiDonenSonuc?>(null)
    private val _klasorVeDosyaIslemleriDonenSonuc = MutableStateFlow<KlasorVeDosyaIslemleriDonenSonuc?>(null)
    private val _mevcutKlasorBosmu= MutableStateFlow(false)
    private val _mevcutKlasorAd= MutableStateFlow<String?>(null)


    val klasorListesiDonenSonuc:StateFlow<KlasorListesiDonenSonuc?> get() = _klasorListesiDonenSonuc.asStateFlow()
    val klasorVeDosyaIslemleriDonenSonuc:StateFlow<KlasorVeDosyaIslemleriDonenSonuc?> get() = _klasorVeDosyaIslemleriDonenSonuc.asStateFlow()
    val mevcutKlasorYolu:StateFlow<String> get() =_mevcutKlasorYolu.asStateFlow()
    val mevcutKlasorDolumu:StateFlow<Boolean> get() = _mevcutKlasorBosmu.asStateFlow()
    val mevcutKlasorAd:StateFlow<String?> get() = _mevcutKlasorAd.asStateFlow()





     private fun mevcutKlasorYoluAl(){
        _mevcutKlasorYolu.value=""
        var mecvut=""
        for(item in _mevcutKlasorListesiArray){
            mecvut+= "$item/"
        }
        Log.e("MevcutKlasorAl metod", "$mecvut ")
        Log.e("MevcutKlasorListesi",_mevcutKlasorListesiArray.toString())
         if(_mevcutKlasorListesiArray.isNotEmpty()) {
             _mevcutKlasorAd.value = _mevcutKlasorListesiArray[_mevcutKlasorListesiArray.lastIndex]
         }
         else{
             _mevcutKlasorAd.value="Duvarım"
         }
        _mevcutKlasorYolu.value=mecvut
    }
    fun mevcutKlasorEkle(klasorAdi:String){
        _mevcutKlasorListesiArray.add(klasorAdi)
        Log.e("MevcutKlasorEkle",_mevcutKlasorListesiArray.toString())
        mevcutKlasorYoluAl()
        mevcutKlasorDolumuKontrol()
    }
    fun mevcutKlasorListesiSonSil(){
        _mevcutKlasorListesiArray.removeLast()
        Log.e("Mevcutklasorboyut",_mevcutKlasorListesiArray.count().toString())
        mevcutKlasorYoluAl()
        mevcutKlasorDolumuKontrol()
    }
    private fun mevcutKlasorDolumuKontrol(){
        val sonuc= _mevcutKlasorListesiArray.isNotEmpty()
        Log.e("Bosmu mevcut",sonuc.toString())
        _mevcutKlasorBosmu.value=sonuc
    }

    fun klasorListesiGetir(ticketID: String,klasorYolu: String){
        try {
        viewModelScope.launch {
            val call = repository.klasorListesiGetir(ticketID,klasorYolu)

          call.enqueue(object : Callback<KlasorListesiDonenSonuc> {
              override fun onFailure(call: Call<KlasorListesiDonenSonuc>, t: Throwable) {
                  Log.e("retrofit", "call failed")
              }
              override fun onResponse(
                  call: Call<KlasorListesiDonenSonuc>,
                  response: Response<KlasorListesiDonenSonuc>
              ) {
                  if(response.isSuccessful) {
                      _klasorListesiDonenSonuc.value = response.body()
//                      _mevcutKlasorListesiArray.add("$klasorYolu\\")

                  }else Log.e("KlasorListesiGetir","Erişilemedi")
              }

          })
        }}catch (e:Exception){
            Log.e("klasorListesiGetir",e.message.toString())
        }
    }
    fun klasorListesiBosmu():Boolean{
        val sonuc= _klasorListesiDonenSonuc.value?.sonucListe.isNullOrEmpty()
        Log.e("Bosmu",sonuc.toString())
        return sonuc
    }
    fun klasorOlustur(ticketID: String, klasorAdi: String, klasorYolu: String){
        try {
            viewModelScope.launch {
                val call = repository.klasorOlustur(ticketID,klasorAdi,klasorYolu)
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
                            Log.e("KlasorOlustur","${klasorVeDosyaIslemleriDonenSonuc.value}")
                        }else Log.e("KlasorOlustur","Erişilemedi")
                    }

                })
            }}catch (e:Exception){
            Log.e("klasorListesiGetir",e.message.toString())
        }

    }

    fun klasorSil(ticketID: String, silinecekAdi: String, klasorYolu: String){
        try {
            viewModelScope.launch {
                val call = repository.klasorSil(ticketID,silinecekAdi,klasorYolu)
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
                            Log.e("KlasorSil","${klasorVeDosyaIslemleriDonenSonuc.value}")
                        }else Log.e("KlasorSil","Erişilemedi")
                    }

                })
            }}catch (e:Exception){
            Log.e("klasorListesiGetir",e.message.toString())
        }

    }
    fun klasorGuncelle(ticketID: String, klasorYolu: String,klasorAdi: String,yeniKlasorAdi: String){
        try {
            viewModelScope.launch {
                val call = repository.klasorGuncelle(ticketID=ticketID,klasorYolu=klasorYolu,klasorAdi=klasorAdi, yeniKlasorAdi = yeniKlasorAdi)
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
                            Log.e("KlasorSil","${klasorVeDosyaIslemleriDonenSonuc.value}")
                        }else Log.e("KlasorSil","Erişilemedi")
                    }

                })
            }}catch (e:Exception){
            Log.e("klasorListesiGetir",e.message.toString())
        }

    }
    fun klasorTasi(ticketID: String, klasorYolu: String, klasorAdi:String, yeniKlasorYolu:String){
        try {
            viewModelScope.launch {
                val call = repository.klasorTasi(ticketID,klasorYolu,klasorAdi,yeniKlasorYolu)
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
                            Log.e("KlasorSil","${klasorVeDosyaIslemleriDonenSonuc.value}")
                        }else Log.e("KlasorSil","Erişilemedi")
                    }

                })
            }}catch (e:Exception){
            Log.e("klasorListesiGetir",e.message.toString())
        }

    }

}