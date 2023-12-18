package com.example.divvydrivestaj.viewmodel


import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class AnasayfaVM: ViewModel() {
    private val _refreshing= MutableStateFlow(false)
    private val _isGridView= MutableStateFlow(false)
    private val _fabAcikmi = MutableStateFlow(false)
    private val _pickedImageUri = MutableStateFlow<Uri?>(null)


    val refreshing:StateFlow<Boolean> get()= _refreshing
    val isGridView:StateFlow<Boolean> get() =_isGridView
    val fabAcikmi:StateFlow<Boolean> get() =_fabAcikmi

     fun refreshDurumDegistir(durum:Boolean){
        _refreshing.value=durum
    }
    fun listeGorunumDegistir(){
        _isGridView.value=!_isGridView.value
    }
    fun fabDurumDegistir(){
        _fabAcikmi.value=!_fabAcikmi.value
    }








}


