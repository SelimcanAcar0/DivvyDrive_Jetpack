package com.example.divvydrivestaj.widgets

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.divvydrivestaj.R
import com.example.divvydrivestaj.constant.Tur
import com.example.divvydrivestaj.viewmodel.DosyaIslemleriVM
import com.example.divvydrivestaj.viewmodel.KlasorIslemleriVM
import com.example.divvydrivestaj.viewmodel.MenuVM
import com.example.divvydrivestaj.viewmodel.TicketVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TasiDialog(
    menuVM: MenuVM,
    klasorIslemleriVM: KlasorIslemleriVM,
    yeniKlasorIslemleriVM:KlasorIslemleriVM=KlasorIslemleriVM(),
    dosyaIslemleriVM: DosyaIslemleriVM,
    ticketVM: TicketVM,
    tur: Tur,
    ad:String
) {

    val yeniVMklsaorYolu by yeniKlasorIslemleriVM.mevcutKlasorYolu.collectAsState()
    val secileninKlasorYolu by klasorIslemleriVM.mevcutKlasorYolu.collectAsState()
    val klasorDonenSonuc by yeniKlasorIslemleriVM.klasorListesiDonenSonuc.collectAsState()
    val ticketID by ticketVM.ticketID.collectAsState()
    val yeniMevcutKlasorYolu by yeniKlasorIslemleriVM.mevcutKlasorYolu.collectAsState()
    val mevcutKlasorDolumu by yeniKlasorIslemleriVM.mevcutKlasorDolumu.collectAsState()


    LaunchedEffect(key1 = Unit){
        yeniKlasorIslemleriVM.klasorListesiGetir(ticketID=ticketID, klasorYolu = yeniVMklsaorYolu)
    }
    fun oncekiKlasoruGetir(){
        CoroutineScope(Dispatchers.Main).launch{
            if(mevcutKlasorDolumu) {
                yeniKlasorIslemleriVM.mevcutKlasorListesiSonSil()
                yeniKlasorIslemleriVM.klasorListesiGetir(ticketID, klasorYolu = yeniVMklsaorYolu)
                delay(3000)
            }
        }
    }
    @Composable
    fun _klasorBolumu(){
        Column (Modifier.fillMaxSize()){
            Divider(
                Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            )

            if (klasorIslemleriVM.klasorListesiBosmu()) Text(text = "Boş")
            else {
                LazyVerticalGrid(
                    columns =
                    GridCells.Fixed(
                        1
                    ),
                ) {
                    klasorDonenSonuc?.sonucListe?.let { it1 ->
                        items(it1.count()) { index ->
                            val klasor = klasorDonenSonuc?.sonucListe!![index]
                            Card(
                                Modifier
                                    .padding(4.dp)
                                    .fillMaxSize()) {
                                Row(modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        CoroutineScope(Dispatchers.Main).launch {

                                            yeniKlasorIslemleriVM.klasorListesiGetir(
                                                ticketID = ticketID,
                                                klasorYolu = yeniMevcutKlasorYolu + klasor.ad
                                            )
                                            delay(2000)
                                            yeniKlasorIslemleriVM.mevcutKlasorEkle(
                                                klasor.ad
                                            )

                                        }
                                    }

                                ) {
                                    Row(
                                        modifier = Modifier.padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(modifier = Modifier.weight(1f)) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.klasor_icon),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .padding(end = 5.dp),
                                                tint = Color(0xFFFFC107)
                                            )
                                        }
                                        Box(modifier = Modifier.weight(4f), contentAlignment = Alignment.CenterStart) {
                                            Text(
                                                text = klasor.ad,
                                                maxLines = 1,
                                                textAlign = TextAlign.Start
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }



            AlertDialog(
                modifier = Modifier.fillMaxSize(),
                onDismissRequest = {
                   menuVM.tasiDialogKapat()
                },
                title = {
                    Text("Taşı")
                },
                text = {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                      Column(Modifier.padding(4.dp)) {
                          if(mevcutKlasorDolumu) {
                              IconButton(onClick = {
                                  oncekiKlasoruGetir()
                              }) {
                                  Icon(
                                      imageVector = Icons.Default.ArrowBack,
                                      contentDescription = ""
                                  )
                              }
                          }
                           _klasorBolumu()
                      }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                when (tur) {
                                    Tur.Dosya -> {
                                        dosyaIslemleriVM.dosyaTasi(
                                            ticketID = ticketID,
                                            klasorYolu = secileninKlasorYolu,
                                            dosyaAdi = ad,
                                            yeniDosyaYolu = yeniVMklsaorYolu
                                        )
                                        delay(2000)
                                        if (yeniKlasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.sonuc == false) {
                                            Log.e("Taşı",yeniKlasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.mesaj.toString())
//
//                                            Handler(Looper.getMainLooper()).post {
//                                                Toast.makeText(
//                                                    context,
//                                                    klasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.mesaj,
//                                                    Toast.LENGTH_SHORT
//                                                ).show()}
                                                return@launch

                                        }
                                        Log.e("Taşı",klasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.mesaj.toString())
                                        Log.e("Taşı",ticketID)
                                        Log.e("Taşı",secileninKlasorYolu)
                                        Log.e("Taşı",ad)
                                        Log.e("Taşı",yeniVMklsaorYolu)
//
//                                        Handler(Looper.getMainLooper()).post {
//                                            Toast.makeText(
//                                                context,
//                                                klasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.mesaj,
//                                                Toast.LENGTH_SHORT
//                                            ).show()
//                                        }

                                    }

                                    Tur.Klasor -> {
                                        klasorIslemleriVM.klasorTasi(
                                            ticketID = ticketID,
                                            klasorYolu = secileninKlasorYolu,
                                            klasorAdi = ad,
                                            yeniKlasorYolu = yeniVMklsaorYolu
                                        )
                                        delay(2000)
                                        if (yeniKlasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.sonuc == false) {
                                            Log.e("Taşı",yeniKlasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.mesaj.toString())
//
//                                            Handler(Looper.getMainLooper()).post {
//                                                Toast.makeText(
//                                                    context,
//                                                    klasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.mesaj,
//                                                    Toast.LENGTH_SHORT
//                                                ).show()
//                                            }
                                            return@launch
                                        }
                                        Log.e("Taşı",klasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.mesaj.toString())
                                        Log.e("Ticket Taşı",ticketID)
                                        Log.e("Secilen Klasor Yolu Taşı",secileninKlasorYolu)
                                        Log.e("Secilen Klasor Adı Taşı",ad)
                                        Log.e("Yeni Klasor Yolu Taşı",yeniVMklsaorYolu)

                                    //
//
//                                        Handler(Looper.getMainLooper()).post {
//                                            Toast.makeText(
//                                                context,
//                                                klasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.value?.mesaj,
//                                                Toast.LENGTH_SHORT
//                                            ).show()
//                                        }
                                    }
                                }
                            }
                        },
                    ) {
                        Text("Taşı")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                                  menuVM.tasiDialogKapat()
                        },
                    ) {
                        Text("İptal")
                    }
                }
            )

        }
