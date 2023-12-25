package com.example.divvydrivestaj.widgets

import android.content.Context
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    tur: Tur,
    klasorYolu:String,
    ad:String,
    klasorIslemleriVM: KlasorIslemleriVM,
    dosyaIslemleriVM: DosyaIslemleriVM,
    anasayfaVM: AnasayfaVM,
    menuVM: MenuVM,
    ticketVM:TicketVM,
    context:Context
    ) {



    val listItems = arrayOf("Sil", "Güncelle", "Taşı")
    val mevcutKlasor by klasorIslemleriVM.mevcutKlasorYolu.collectAsState()
    val ticketID by ticketVM.ticketID.collectAsState()
    val dropDownGoster by menuVM.dropdownGoster.collectAsStateWithLifecycle()



    Box(
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = {
            menuVM.tiklananAdAl(ad)
            menuVM.dropDownGoster(true)
            Log.e("Dropdown","açıldı")
        }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Open Options"
            )
        }
        DropdownMenu(
        expanded = dropDownGoster,
            onDismissRequest = { menuVM.dropDownGoster(false)
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
                                    menuVM.guncelleDialogGoster()
                            }
                            2->{
                                    menuVM.tasiDialogGoster()
                            }
                        }
                        menuVM.dropDownGoster(false)
                    },
                    text = {Text(text = itemValue)}
                )
            }
        }
    }
}