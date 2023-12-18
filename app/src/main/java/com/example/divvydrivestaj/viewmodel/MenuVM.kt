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

      private val _guncelleDialogGoster = MutableStateFlow(false)
      private val _klasorOlusturDialogGoster = MutableStateFlow(false)
      private val _dropdownGoster = MutableStateFlow(false)
      private val _klasorOlusturDialogTF= MutableStateFlow("")
      private var _klasorOlusturAdi:String=""

       val klasorOlusturDialogTF:StateFlow<String> get() = _klasorOlusturDialogTF
       val guncelleDialogGoster:StateFlow<Boolean> get() = _guncelleDialogGoster
       val klasorOlusturDialogGoster:StateFlow<Boolean> get() = _klasorOlusturDialogGoster
       val dropdownGoster:StateFlow<Boolean> get() = _dropdownGoster



      fun klasorOlusturDialogOnConfirm(context: Context,mevcutKlasor:String,tfDeger:String) {
          CoroutineScope(Dispatchers.Main).launch {
              klasorIslemleriVM.klasorOlustur(
                  "2b6c9bb0-1483-4a55-bf92-9274f2394ab7",
                  _klasorOlusturAdi,
                  mevcutKlasor
              )

              delay(2000)
              if (klasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.sonuc == false) {
                  Toast.makeText(
                      context,
                      klasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.mesaj,
                      Toast.LENGTH_SHORT
                  ).show()
                  return@launch
              }
          }
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

    fun dropDownGoster() {
        _dropdownGoster.value= !_dropdownGoster.value
    }

      fun klasorOlusturDialogTFGuncelle(it:String){
          _klasorOlusturDialogTF.value=it
          Log.e("kTF",_klasorOlusturDialogTF.value)
      }


}sd