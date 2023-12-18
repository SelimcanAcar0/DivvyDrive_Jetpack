package com.example.divvydrivestaj.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CustomDialogVM:ViewModel(){

    private val _klasorOlusturDialogGoster= MutableLiveData<Boolean>()
    private val _guncelleDialogGoster = MutableLiveData<Boolean>()
    private var _guncelledialogTextField= MutableLiveData("")

    val klasorOlusturDialogGoster:LiveData<Boolean> = _klasorOlusturDialogGoster
    val guncelleDialogGoster:LiveData<Boolean> = _guncelleDialogGoster
    var guncelledialogTextField:LiveData<String> = _guncelledialogTextField

    fun guncelleDialogButonTiklandimi() {
        _guncelleDialogGoster.postValue(true)
    }
    fun guncelleDialogKapat() {
        _guncelleDialogGoster.postValue(false)
    }
    fun guncelleDialogOnaylaButon() {
        _guncelleDialogGoster.postValue(false)
    }
    fun guncelleDiaolgTextFieldDegistir(it:String){
        _guncelledialogTextField.value=it

    }

    fun klasorOlusturDialogAc() {
        _klasorOlusturDialogGoster.value=true
    }
    fun klasorOlusturDialogKapat() {
        _klasorOlusturDialogGoster.value=false
    }
}