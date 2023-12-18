package com.example.divvydrivestaj

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.divvydrivestaj.constant.Sayfalar


@Composable
fun SayfaGecis(){
    val navController= rememberNavController()

    NavHost(navController = navController, startDestination = Sayfalar.BASLANGIC_EKRANI.page ){
        composable(Sayfalar.BASLANGIC_EKRANI.page){
            BaslangicEkrani(navController)
        }
        composable(Sayfalar.GIRIS_EKRAN.page){
            GirisEkran(navController)
        }
        composable(Sayfalar.ANASAYFA.page){
            Anasayfa()
        }
    }
}