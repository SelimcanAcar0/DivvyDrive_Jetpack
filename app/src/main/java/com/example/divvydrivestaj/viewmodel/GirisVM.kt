package com.example.divvydrivestaj.viewmodel

import SharedPref
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.divvydrivestaj.constant.SharedText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GirisVM(context: Context):ViewModel() {
    private val sharedPref=SharedPref(context)
    private val _sifreGizlensinmi=  MutableStateFlow(false)
    private val _kullanicAdiTF =  MutableStateFlow("")
    private val _sifreTF =  MutableStateFlow("")
    private val _beniHatirla = MutableStateFlow(sharedPref.boolAl(SharedText.BeniHatirla, false))


    val sifreGizlensinmi:StateFlow<Boolean> get() = _sifreGizlensinmi.asStateFlow()
    val kullaniciAdiTF:StateFlow<String> get() =_kullanicAdiTF.asStateFlow()
    val sifreTF:StateFlow<String> get() =_sifreTF.asStateFlow()
    val beniHatirla:StateFlow<Boolean> get() = _beniHatirla.asStateFlow()


    fun kullaniciAdiYaz(text:String){
        _kullanicAdiTF.value=text
    }

    fun sifreYaz(text:String){
        _sifreTF.value=text
    }
    fun sifreGosterDurumDegistir(){
        _sifreGizlensinmi.value= !_sifreGizlensinmi.value!!
    }

    fun beniHatirlaDurumDegistir(it:Boolean){
        _beniHatirla.value=it
    }

}