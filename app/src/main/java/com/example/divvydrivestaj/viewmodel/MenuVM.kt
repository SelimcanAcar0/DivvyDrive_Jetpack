package com.example.divvydrivestaj.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MenuVM:ViewModel() {
      private val klasorIslemleriVM=KlasorIslemleriVM()
      private val anasayfaVM=AnasayfaVM()


      private val _guncelleDialogGoster = MutableStateFlow(false)
      private val _klasorOlusturDialogGoster = MutableStateFlow(false)
      private val _dropdownGoster = MutableStateFlow(false)
      private val _tasiDialogGoster=MutableStateFlow(false)
      private val _dialogTf=MutableStateFlow("")
      private val _tiklananDropDownAd = MutableStateFlow("")


       val guncelleDialogGoster:StateFlow<Boolean> get() = _guncelleDialogGoster
       val klasorOlusturDialogGoster:StateFlow<Boolean> get() = _klasorOlusturDialogGoster
       val dropdownGoster:StateFlow<Boolean>  = _dropdownGoster
       val dialogTf:StateFlow<String> get() = _dialogTf
    val tiklananAdi:StateFlow<String> get() =_tiklananDropDownAd

    val tasiDialogGoster:StateFlow<Boolean> get() = _tasiDialogGoster



      fun klasorOlusturDialogOnConfirm(context: Context,mevcutKlasor:String) {
          CoroutineScope(Dispatchers.Main).launch {
              klasorIslemleriVM.klasorOlustur(
                  "2b6c9bb0-1483-4a55-bf92-9274f2394ab7",
                  klasorAdi = dialogTf.value,
                  klasorYolu = mevcutKlasor
              )
              anasayfaVM.refreshDurumDegistir(true)
              delay(2000)
              if (klasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.sonuc == false) {
                  Toast.makeText(
                      context,
                      klasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.mesaj,
                      Toast.LENGTH_SHORT
                  ).show()
                  return@launch
              }
              Toast.makeText(
                  context,
                  klasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.mesaj,
                  Toast.LENGTH_SHORT
              ).show()

              anasayfaVM.refreshDurumDegistir(false)
          }
      }
    fun klasorGuncelleDialogOnConfirm(context: Context, ticketID: String, mevcutKlasor: String, klasorAdi: String,yeniKlasorAdi:String){
        CoroutineScope(Dispatchers.Main).launch {
            klasorIslemleriVM.klasorGuncelle(ticketID= ticketID, klasorYolu = mevcutKlasor,klasorAdi= klasorAdi, yeniKlasorAdi = yeniKlasorAdi)
            delay(2000)
            if (klasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.sonuc == false) {
                Toast.makeText(
                    context,
                    klasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.mesaj,
                    Toast.LENGTH_SHORT
                ).show()
                return@launch
            }
            Toast.makeText(
                context,
                klasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.mesaj,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    fun dosyaGuncelleDialogOnConfirm(){

    }
    fun klasorOlusturDialogGoster() {
        _klasorOlusturDialogGoster.value= true
    }

    fun klasorOlusturDialogKapat() {
        _klasorOlusturDialogGoster.value=false
    }

    fun guncelleDialogGoster() {
        _guncelleDialogGoster.value= true
    }

    fun guncelleDialogKapat() {
        _guncelleDialogGoster.value=false
    }

    fun dropDownGoster(value:Boolean) {
        _dropdownGoster.value= value
    }

    fun tasiDialogGoster(){
        _tasiDialogGoster.value=true
    }
    fun tasiDialogKapat(){
        _tasiDialogGoster.value=false
    }

      fun klasorOlusturDialogTFGuncelle(it:String){
          _dialogTf.value=it
          Log.e("kTF",_dialogTf.value)
      }

    fun tiklananAdAl(klasorAd:String){
        _tiklananDropDownAd.value=klasorAd
    }


}