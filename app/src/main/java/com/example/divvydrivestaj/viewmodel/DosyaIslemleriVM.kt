package com.example.divvydrivestaj.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.divvydrivestaj.entity.DosyaListesiDonenSonuc
import com.example.divvydrivestaj.entity.DosyaYayinlaBilgi
import com.example.divvydrivestaj.entity.KlasorVeDosyaIslemleriDonenSonuc
import com.example.divvydrivestaj.repo.IServisRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DosyaIslemleriVM:ViewModel() {
    private val repository = IServisRepo()
    private val _klasorVeDosyaIslemleriDonenSonuc = MutableStateFlow<KlasorVeDosyaIslemleriDonenSonuc?>(null)
    private val _dosyaListesiDonenSonuc = MutableStateFlow<DosyaListesiDonenSonuc?>(null)

    val dosyaListesiDonenSonuc: StateFlow<DosyaListesiDonenSonuc?> get() = _dosyaListesiDonenSonuc.asStateFlow()
    val klasorVeDosyaIslemleriDonenSonuc:StateFlow<KlasorVeDosyaIslemleriDonenSonuc?> get() = _klasorVeDosyaIslemleriDonenSonuc.asStateFlow()


    fun dosyaListesiGetir(ticketID: String,klasorYolu:String){
        try {
            viewModelScope.launch {
                  val call=repository.dosyaListesiGetir(ticketID,klasorYolu)

              call.enqueue(object : Callback<DosyaListesiDonenSonuc> {
                  override fun onFailure(call: Call<DosyaListesiDonenSonuc>, t: Throwable) {
                      Log.e("retrofit", "call failed")
                  }
                  override fun onResponse(
                      call: Call<DosyaListesiDonenSonuc>,
                      response: Response<DosyaListesiDonenSonuc>
                  ) {
                      if(response.isSuccessful) {
                          _dosyaListesiDonenSonuc.value = response.body()
                      }else Log.e("KlasorListesiGetir","Erişilemedi")
                  }

              })
            }}catch (e:Exception){
            Log.e("klasorListesiGetir",e.message.toString())
        }
    }

    fun dosyaListesiBosmu():Boolean{
        val sonuc= _dosyaListesiDonenSonuc.value?.sonucListe.isNullOrEmpty()
        Log.e("Bosmu dosya",sonuc.toString())
        return sonuc
    }
    fun dosyaSil(ticketID: String, klasorYolu:String, silinecekAdi:String){
        try {
            viewModelScope.launch {
                val call = repository.dosyaSil(ticketID,klasorYolu,silinecekAdi)
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

                        }else Log.e("KlasorListesiGetir","Erişilemedi")
                    }

                })
            }}catch (e:Exception){
            Log.e("klasorListesiGetir",e.message.toString())
        }
    }

    fun dosyaGuncelle(ticketID: String, klasorYolu: String,dosyaAdi: String,yeniKlasorAdi: String){
        try {
            viewModelScope.launch {
                val call = repository.dosyaGuncelle(ticketID,klasorYolu,dosyaAdi,yeniKlasorAdi)
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

    fun dosyaTasi(ticketID: String, klasorYolu: String, dosyaAdi:String, yeniDosyaYolu:String){
        try {
            viewModelScope.launch {
                val call = repository.dosyaTasi(ticketID,klasorYolu,dosyaAdi,yeniDosyaYolu)
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
                            Log.e("KlasorSil","${klasorVeDosyaIslemleriDonenSonuc.value}")
                        }else Log.e("KlasorSil","Erişilemedi")
                    }

                })
            }}catch (e:Exception){
            Log.e("klasorListesiGetir",e.message.toString())
        }
    }
    fun dosyaParcalariYukle(ticketID: String, ID: String, parcaNumarasi:Int){

    }
    fun dosyaYayinla(ticketID: String, ID: String, dosyaYayinlaBilgi: DosyaYayinlaBilgi){

    }
    fun dosyaDirektYukle(ticketID: String, ID: String, dosyaYayinlaBilgi: DosyaYayinlaBilgi){

    }
}