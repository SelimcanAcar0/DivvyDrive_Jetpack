package com.example.divvydrivestaj.config

import com.example.divvydrivestaj.Service.IServis

class APIErisim {
    private val isHome=true
    private  val BASE_URL =if(isHome)"https://192.168.1.102/Staj/" else "https://192.168.2.25/Staj/"
    fun retrofitCalistir():IServis{

        return  RetrofitCalistir.retrofit(BASE_URL).create(IServis::class.java)
    }

}