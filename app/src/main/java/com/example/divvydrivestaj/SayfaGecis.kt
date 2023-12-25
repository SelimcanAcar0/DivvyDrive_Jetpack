package com.example.divvydrivestaj

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.divvydrivestaj.constant.Sayfalar
import com.example.divvydrivestaj.viewmodel.AnasayfaVM
import com.example.divvydrivestaj.viewmodel.DosyaIslemleriVM
import com.example.divvydrivestaj.viewmodel.GirisVM
import com.example.divvydrivestaj.viewmodel.KlasorIslemleriVM
import com.example.divvydrivestaj.viewmodel.MenuVM
import com.example.divvydrivestaj.viewmodel.TicketVM


@Composable
fun SayfaGecis(
    context: Context= LocalContext.current,
    anasayfaVM: AnasayfaVM = AnasayfaVM(),
    dosyaIslemleriVM: DosyaIslemleriVM = DosyaIslemleriVM(),
    klasorIslemleriVM: KlasorIslemleriVM = KlasorIslemleriVM(),
    menuVM: MenuVM= MenuVM(),
    ticketVM: TicketVM= TicketVM(context),
    girisVM: GirisVM= GirisVM(context)
){

    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = Sayfalar.BASLANGIC_EKRANI.page ){
        composable(Sayfalar.BASLANGIC_EKRANI.page){
            BaslangicEkrani(navController)
        }
        composable(Sayfalar.GIRIS_EKRAN.page){
            GirisEkran(
                navController = navController,
                ticketVM = ticketVM,
                anasayfaVM = anasayfaVM,
                girisVM = girisVM,
            )
        }
        composable(Sayfalar.ANASAYFA.page){
            Anasayfa(
                klasorIslemleriVM= klasorIslemleriVM,
                dosyaIslemleriVM = dosyaIslemleriVM,
                anasayfaVM =  anasayfaVM,
                menuVM = menuVM,
                ticketVM = ticketVM,
            )
        }
    }
}