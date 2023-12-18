package com.example.divvydrivestaj.repo

import com.example.divvydrivestaj.config.APIErisim
import com.example.divvydrivestaj.entity.DosyaListesiDonenSonuc
import com.example.divvydrivestaj.entity.DosyaYayinlaBilgi
import com.example.divvydrivestaj.entity.KlasorListesiDonenSonuc
import com.example.divvydrivestaj.entity.KlasorVeDosyaIslemleriDonenSonuc
import com.example.divvydrivestaj.entity.Kullanici
import com.example.divvydrivestaj.entity.Ticket
import retrofit2.Call

class IServisRepo {
    private val apiErisim= APIErisim()
    private val IServis = apiErisim.retrofitCalistir()

     fun mevcutKlasorAl():Call<String>{
        return IServis.MevcutKlasorAl()
    }
    fun ticketAl(kullanici: Kullanici):Call<Ticket>{
        return IServis.TicketAl(kullanici = kullanici)
    }
     fun klasorListesiGetir(ticketID: String,klasorYolu: String): Call<KlasorListesiDonenSonuc> {
        return IServis.KlasorListesiGetir(ticketID,klasorYolu)
    }

    fun klasorOlustur(ticketID: String,klasorAdi:String,klasorYolu: String): Call<KlasorVeDosyaIslemleriDonenSonuc >{
        return IServis.KlasorOlustur(ticketID,klasorAdi,klasorYolu)
    }
    fun klasorSil(ticketID: String,klasorAdi:String,klasorYolu: String): Call<KlasorVeDosyaIslemleriDonenSonuc >{
        return IServis.KlasorSil(ticketID,klasorAdi,klasorYolu)
    }
    fun klasorGuncelle(ticketID: String,klasorYolu: String,klasorAdi:String,yeniKlasorAdi:String):Call<KlasorVeDosyaIslemleriDonenSonuc>{
        return IServis.KlasorGuncelle(ticketID,klasorYolu,klasorAdi,yeniKlasorAdi)
    }
    fun klasorTasi(ticketID: String, klasorYolu: String, klasorAdi:String, yeniKlasorYolu:String):Call<KlasorVeDosyaIslemleriDonenSonuc>{
        return IServis.KlasorTasi(ticketID,klasorYolu,klasorAdi,yeniKlasorYolu)
    }


    fun dosyaListesiGetir(ticketID: String,klasorYolu: String):Call<DosyaListesiDonenSonuc>{
        return IServis.DosyaListesiGetir(ticketID=ticketID, klasorYolu = klasorYolu)
    }

    fun dosyaSil(ticketID: String,klasorYolu: String,dosyaAdi:String,): Call<KlasorVeDosyaIslemleriDonenSonuc>{
        return IServis.DosyaSil(ticketID,klasorYolu,dosyaAdi)
    }
    fun dosyaGuncelle(ticketID: String,klasorYolu: String,dosyaAdi:String,yeniDosyaAdi:String): Call<KlasorVeDosyaIslemleriDonenSonuc>{
        return IServis.DosyaGuncelle(ticketID,klasorYolu,dosyaAdi,yeniDosyaAdi)
    }
    fun dosyaTasi(ticketID: String, klasorYolu: String, dosyaAdi:String, yeniDosyaYolu:String): Call<KlasorVeDosyaIslemleriDonenSonuc>{
        return IServis.DosyaTasi(ticketID,klasorYolu,dosyaAdi,yeniDosyaYolu)
    }
    fun dosyaMetaDataKaydiOlustur(): Call<KlasorVeDosyaIslemleriDonenSonuc>{
        return IServis.DosyaMetaDataKaydiOlustur()
    }
    fun dosyaParcalariYukle(ticketID: String, ID: String, parcaNumarasi:Int): Call<KlasorVeDosyaIslemleriDonenSonuc>{
        return IServis.DosyaParcalariYukle(ticketID,ID,parcaNumarasi)
    }
    fun dosyaYayinla(ticketID: String, ID: String, dosyaYayinlaBilgi: DosyaYayinlaBilgi): Call<KlasorVeDosyaIslemleriDonenSonuc>{
        return IServis.DosyaYayinla(ticketID,ID,dosyaYayinlaBilgi)
    }

    fun dosyaDirektYukle(ticketID: String, ID: String, dosyaYayinlaBilgi: DosyaYayinlaBilgi): Call<KlasorVeDosyaIslemleriDonenSonuc>{
        return IServis.DosyaYayinla(ticketID,ID,dosyaYayinlaBilgi)
    }








}