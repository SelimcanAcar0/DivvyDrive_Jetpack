package com.example.divvydrivestaj

import SharedPref
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.divvydrivestaj.constant.Sayfalar
import com.example.divvydrivestaj.constant.SharedText
import kotlinx.coroutines.delay

@Composable
fun BaslangicEkrani(navController: NavController){
    val alpha= remember {
        Animatable(0f)
    }

    val sharedPref=SharedPref(LocalContext.current)
    LaunchedEffect(key1 = true){
        alpha.animateTo(1f, animationSpec = tween(700))
        delay(1000)

        if(!sharedPref.boolAl(SharedText.BeniHatirla,false)) {
            navController.navigate(Sayfalar.GIRIS_EKRAN.page) {
                popUpTo(Sayfalar.BASLANGIC_EKRANI.page) { inclusive = true }
            }
        }
        else{
            navController.navigate(Sayfalar.ANASAYFA.page) {
                popUpTo(Sayfalar.BASLANGIC_EKRANI.page) { inclusive = true }
            }
        }

    }
    Box(modifier = Modifier
        .fillMaxSize()
        .fillMaxHeight(), contentAlignment = Alignment.Center){
        Image(painter = painterResource(id = R.drawable.baslangic_arkaplani), contentDescription = "", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillHeight)
        Image(painter = painterResource(id = R.drawable.dd_logo), contentDescription ="", modifier = Modifier
            .fillMaxSize().alpha(alpha.value)
            .padding(horizontal = 20.dp), contentScale = ContentScale.Fit)
    }
}