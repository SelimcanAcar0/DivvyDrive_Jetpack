package com.example.divvydrivestaj.widgets

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
    ad:String,
    klasorIslemleriVM: KlasorIslemleriVM,
    dosyaIslemleriVM: DosyaIslemleriVM,
    anasayfaVM: AnasayfaVM,
    menuVM: MenuVM,
    ticketVM:TicketVM,
    context:Context
    ) {


    val mevcutKlasor by klasorIslemleriVM.mevcutKlasorYolu.collectAsState()
    val ticketID by ticketVM.ticketID.collectAsState()
    var dropDownGoster by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = {
            menuVM.tiklananAdAl(ad)
            dropDownGoster=true
            Log.e("Dropdown", "açıldı")
        }) {
            Crossfade(targetState = dropDownGoster, label = "", animationSpec = tween(1500)) { value ->
                when (value) {
                    true -> Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null
                    )

                    false -> Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null
                    )
                }
            }
            DropdownMenu(
                expanded = dropDownGoster,
                onDismissRequest = {
                    dropDownGoster = false
                    Log.e("TicketID", ticketID)
                }
            ) {
                menuVM.listItems.forEachIndexed { itemIndex, itemValue ->
                    DropdownMenuItem(
                        onClick = {
                            Toast.makeText(context, itemValue, Toast.LENGTH_SHORT)
                                .show()
                            when (itemIndex) {
                                0 -> {
                                    if (tur == Tur.Klasor) {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            anasayfaVM.refreshDurumDegistir(true)
                                            Log.e("KlasorSil Ticket", ticketID)
                                            klasorIslemleriVM.klasorSil(
                                                ticketID = ticketID,
                                                klasorYolu = mevcutKlasor,
                                                silinecekAdi = ad
                                            )
                                            delay(1000)
                                            klasorIslemleriVM.klasorListesiGetir(
                                                ticketID,
                                                klasorYolu = mevcutKlasor
                                            )
                                            delay(1000)
                                            anasayfaVM.refreshDurumDegistir(false)
                                        }

                                    }
                                    if (tur == Tur.Dosya) {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            anasayfaVM.refreshDurumDegistir(true)
                                            dosyaIslemleriVM.dosyaSil(
                                                ticketID = ticketID,
                                                klasorYolu = mevcutKlasor,
                                                silinecekAdi = ad
                                            )
                                            delay(1000)
                                            dosyaIslemleriVM.dosyaListesiGetir(
                                                ticketID,
                                                klasorYolu = mevcutKlasor
                                            )
                                            delay(1000)
                                            anasayfaVM.refreshDurumDegistir(false)
                                        }
                                    }
                                }

                                1 -> {
                                    menuVM.guncelleDialogGoster()
                                }

                                2 -> {
                                    menuVM.tasiDialogGoster()
                                    menuVM.secilenTurAl(tur)
                                }
                            }
                            dropDownGoster = false
                        },
                        text = { Text(text = itemValue) }
                    )
                }
            }
        }
    }
}