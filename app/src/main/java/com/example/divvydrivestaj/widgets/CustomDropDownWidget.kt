package com.example.divvydrivestaj.widgets

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.example.divvydrivestaj.constant.Tur
import com.example.divvydrivestaj.viewmodel.AnasayfaVM
import com.example.divvydrivestaj.viewmodel.DosyaIslemleriVM
import com.example.divvydrivestaj.viewmodel.KlasorIslemleriVM
import com.example.divvydrivestaj.viewmodel.MenuVM
import com.example.divvydrivestaj.viewmodel.TicketVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CustomDropDown(
    tur: Tur, klasorYolu:String, ad:String,

     klasorIslemleriVM: KlasorIslemleriVM =KlasorIslemleriVM(),
    dosyaIslemleriVM: DosyaIslemleriVM =DosyaIslemleriVM(),
    anasayfaVM: AnasayfaVM =AnasayfaVM(),
    dialogVM: MenuVM =MenuVM(),
    ) {
    val context = LocalContext.current

    val ticketVM=TicketVM(context)
    val listItems = arrayOf("Sil", "Güncelle", "Taşı")
    val mevcutKlasor by klasorIslemleriVM.mevcutKlasorYolu.collectAsState()
    val ticketID by ticketVM.ticketID.collectAsState()
    val acildimi = remember {
        mutableStateOf(false)
    }


    Box(
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = {
            acildimi.value=true
        }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Open Options"
            )
        }
        DropdownMenu(
        expanded = acildimi.value,
            onDismissRequest = {acildimi.value=false
            Log.e("TicketID",ticketID)
            }
        ) {
            listItems.forEachIndexed { itemIndex, itemValue ->
                 DropdownMenuItem(
                    onClick = {
                        Toast.makeText(context, itemValue, Toast.LENGTH_SHORT)
                            .show()
                        when(itemIndex){
                            0->{
                                if(tur==Tur.Klasor){
                                     CoroutineScope(Dispatchers.Main).launch {
                                        anasayfaVM.refreshDurumDegistir(true)
                                         Log.e("KlasorSil Ticket",ticketID)
                                         klasorIslemleriVM.klasorSil(ticketID = ticketID,klasorYolu=mevcutKlasor, silinecekAdi = ad)
                                         delay(1000)
                                         klasorIslemleriVM.klasorListesiGetir( ticketID, klasorYolu = mevcutKlasor)
                                         delay(1000)
                                         anasayfaVM.refreshDurumDegistir(false)
                                    }

                                }
                                if(tur==Tur.Dosya){
                                    CoroutineScope(Dispatchers.Main).launch {
                                        anasayfaVM.refreshDurumDegistir(true)
                                        dosyaIslemleriVM.dosyaSil(ticketID = ticketID,klasorYolu=klasorYolu, silinecekAdi = ad)
                                        delay(1000)
                                        dosyaIslemleriVM.dosyaListesiGetir(ticketID, klasorYolu = mevcutKlasor)
                                        delay(1000)
                                        anasayfaVM.refreshDurumDegistir(false)
                                    }
                                }
                            }
                            1->{
                                    dialogVM.guncelleDialogGoster()
                            }
                            2->{

                            }
                        }
                        acildimi.value=false
                    },
                    text = {Text(text = itemValue)}
                )
            }
        }
    }
}