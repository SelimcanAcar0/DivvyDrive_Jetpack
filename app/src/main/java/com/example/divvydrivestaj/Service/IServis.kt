@file:Suppress("FunctionName")

package com.example.divvydrivestaj.Service
import com.example.divvydrivestaj.entity.DosyaListesiDonenSonuc
import com.example.divvydrivestaj.entity.DosyaYayinlaBilgi
import com.example.divvydrivestaj.entity.KlasorListesiDonenSonuc
import com.example.divvydrivestaj.entity.KlasorVeDosyaIslemleriDonenSonuc
import com.example.divvydrivestaj.entity.Kullanici
import com.example.divvydrivestaj.entity.Ticket
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface IServis {
    @POST("MevcutKlasoruAl")
     fun MevcutKlasorAl():Call<String>
    @POST("TicketAl")
    fun TicketAl(@Body kullanici: Kullanici):Call<Ticket>

    @GET("KlasorListesiGetir")
     fun KlasorListesiGetir(@Query("ticketID") ticketID: String, @Query("klasorYolu") klasorYolu: String): Call<KlasorListesiDonenSonuc>

    @POST("KlasorOlustur")
    fun KlasorOlustur(@Query("ticketID")ticketID: String,@Query("klasorAdi")klasorAdi:String,@Query("klasorYolu")klasorYolu: String): Call<KlasorVeDosyaIslemleriDonenSonuc>

    @DELETE("KlasorSil")
    fun KlasorSil(@Query("ticketID")ticketID: String, @Query("klasorAdi")klasorAdi:String, @Query("klasorYolu")klasorYolu: String): Call<KlasorVeDosyaIslemleriDonenSonuc>


    @PUT("KlasorGuncelle")
    fun KlasorGuncelle(@Query("ticketID")ticketID: String,@Query("klasorYolu")klasorYolu: String,@Query("klasorAdi")klasorAdi:String,@Query("yeniKlasorAdi")yeniKlasorAdi:String): Call<KlasorVeDosyaIslemleriDonenSonuc>

    @PUT("KlasorTasi")
    fun KlasorTasi(@Query("ticketID") ticketID: String, @Query("klasorYolu") klasorYolu: String,@Query("KlasorAdi") dosyaAdi: String,@Query("yeniDosyaYolu") yeniDosyaYolu: String): Call<KlasorVeDosyaIslemleriDonenSonuc>

    @GET("DosyaListesiGetir")
    fun DosyaListesiGetir(@Query("ticketID") ticketID: String, @Query("klasorYolu") klasorYolu: String): Call<DosyaListesiDonenSonuc>

    @POST("DosyaOlustur")
    fun DosyaOlustur(): Call<KlasorVeDosyaIslemleriDonenSonuc>

    @DELETE("DosyaSil")
    fun DosyaSil(@Query("ticketID") ticketID: String, @Query("klasorYolu") klasorYolu: String,@Query("dosyaAdi")dosyaAdi:String):Call<KlasorVeDosyaIslemleriDonenSonuc>

    @PUT("DosyaGuncelle")
    fun DosyaGuncelle(@Query("ticketID") ticketID: String, @Query("klasorYolu") klasorYolu: String,@Query("dosyaAdi") dosyaAdi: String,@Query("yeniDosyaAdi") yeniDosyaAdi: String): Call<KlasorVeDosyaIslemleriDonenSonuc>

    @PUT("DosyaTasi")
     fun DosyaTasi(@Query("ticketID") ticketID: String, @Query("klasorYolu") klasorYolu: String,@Query("dosyaAdi") dosyaAdi: String,@Query("yeniDosyaYolu") yeniDosyaYolu: String): Call<KlasorVeDosyaIslemleriDonenSonuc>

    @POST("DosyaMetaDataKaydiOlustur")
     fun DosyaMetaDataKaydiOlustur(): Call<KlasorVeDosyaIslemleriDonenSonuc>

    @POST("DosyaParcalariYukle")
    fun DosyaParcalariYukle(@Query("ticketID") ticketID: String, @Query("ID") id: String, @Query("parcaNumarasi") parcaNumarasi:Int): Call<KlasorVeDosyaIslemleriDonenSonuc>

    @POST("DosyaYayinla")
    fun DosyaYayinla(@Query("ticketID") ticketID: String, @Query("ID") id: String,@Body dosyaYayinlaBilgi: DosyaYayinlaBilgi): Call<KlasorVeDosyaIslemleriDonenSonuc>

    @POST("DosyaDirektYukle")
    fun DosyaDirektYukle(@Query("ticketID") ticketID: String, @Body dosyaYayinlaBilgi: DosyaYayinlaBilgi): Call<KlasorVeDosyaIslemleriDonenSonuc>

}