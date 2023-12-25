package com.example.divvydrivestaj

import SharedPref
import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.divvydrivestaj.constant.SharedText
import com.example.divvydrivestaj.constant.Tur
import com.example.divvydrivestaj.viewmodel.AnasayfaVM
import com.example.divvydrivestaj.viewmodel.DosyaIslemleriVM
import com.example.divvydrivestaj.viewmodel.KlasorIslemleriVM
import com.example.divvydrivestaj.viewmodel.MenuVM
import com.example.divvydrivestaj.viewmodel.TicketVM
import com.example.divvydrivestaj.widgets.CustomDropDown
import com.example.divvydrivestaj.widgets.KlasorVeDosyaIslemleriDialogWidget
import com.example.divvydrivestaj.widgets.TasiDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Anasayfa(
    context: Context = LocalContext.current,
    klasorIslemleriVM: KlasorIslemleriVM,
    dosyaIslemleriVM: DosyaIslemleriVM,
    anasayfaVM: AnasayfaVM,
    menuVM: MenuVM,
    ticketVM: TicketVM,

    sharedPref: SharedPref = SharedPref(context)

){


    val klasorDonenSonuc by klasorIslemleriVM.klasorListesiDonenSonuc.collectAsState()
    val dosyaDonenSonuc by dosyaIslemleriVM.dosyaListesiDonenSonuc.collectAsState()
    val isGridView by anasayfaVM.isGridView.collectAsState()
    val isRefresing by anasayfaVM.refreshing.collectAsState()
    val fabAcikmi by anasayfaVM.fabAcikmi.collectAsState()
    val mevcutKlasorYolu by klasorIslemleriVM.mevcutKlasorYolu.collectAsState()
    val mevcutKlasorDolumu by klasorIslemleriVM.mevcutKlasorDolumu.collectAsState()
    val ticketID by ticketVM.ticketID.collectAsState()
    val mevcutKlasorAd by klasorIslemleriVM.mevcutKlasorAd.collectAsState()
    val klasorOlusturDialogGoster by menuVM.klasorOlusturDialogGoster.collectAsStateWithLifecycle()
    val guncelleDialogGoster by menuVM.guncelleDialogGoster.collectAsStateWithLifecycle()
    val tasiDialogGoster by menuVM.tasiDialogGoster.collectAsStateWithLifecycle()
    val dialogTFDeger by menuVM.dialogTf.collectAsState()
    val dropDownTiklananAd by menuVM.tiklananAdi.collectAsState()




    




/*TODO Dialog açılınca kapanmıyor - Yükleme İşlemi yapılacak  */
    //TODO Loading stateini yap delayı kaldır
    fun klasorVeDosyaListesiGetir(){
    CoroutineScope(Dispatchers.Main).launch {
        anasayfaVM.refreshDurumDegistir(true)
        Log.e("Ticket Klasor",ticketID)
        klasorIslemleriVM.klasorListesiGetir(ticketID, klasorYolu = mevcutKlasorYolu)
        dosyaIslemleriVM.dosyaListesiGetir(ticketID, klasorYolu = mevcutKlasorYolu)
        delay(2000)
        anasayfaVM.refreshDurumDegistir(false)
    }
}
    fun oncekiKlasoruGetir(){
        CoroutineScope(Dispatchers.Main).launch{
            if(mevcutKlasorDolumu) {
                anasayfaVM.refreshDurumDegistir(true)
                klasorIslemleriVM.mevcutKlasorListesiSonSil()
                delay(500)
                klasorIslemleriVM.klasorListesiGetir(ticketID, klasorYolu = mevcutKlasorYolu)
                dosyaIslemleriVM.dosyaListesiGetir(ticketID, klasorYolu = mevcutKlasorYolu)
                delay(1500)
                anasayfaVM.refreshDurumDegistir(false)
            }
        }
    }

    @Composable
    fun _klasorBolumu(){
        Column {
            if (isRefresing) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.dd_mavi),
                    strokeWidth = 5.dp
                )
            }
            Text(
                text = "Klasörler",
                fontSize = 24.sp
            )
            Divider(
                Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            )

            if (klasorIslemleriVM.klasorListesiBosmu()) Text(text = "Boş")
            else {
                LazyVerticalGrid(
                    columns =
                    if (isGridView) GridCells.Fixed(2) else GridCells.Fixed(
                        1
                    ),
                ) {
                    klasorDonenSonuc?.sonucListe?.let { it1 ->
                        items(it1.count()) { index ->
                            val klasor = klasorDonenSonuc?.sonucListe!![index]
                            Card(Modifier.padding(4.dp)) {
                                Row(modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        CoroutineScope(Dispatchers.Main).launch {

                                            anasayfaVM.refreshDurumDegistir(true)
                                            klasorIslemleriVM.klasorListesiGetir(
                                                ticketID,
                                                klasorYolu = mevcutKlasorYolu + klasor.ad
                                            )
                                            dosyaIslemleriVM.dosyaListesiGetir(
                                                ticketID,
                                                klasorYolu = mevcutKlasorYolu + klasor.ad
                                            )
                                            delay(2000)
                                            klasorIslemleriVM.mevcutKlasorEkle(
                                                klasor.ad
                                            )
                                            anasayfaVM.refreshDurumDegistir(false)
                                        }
                                        Log.e(
                                            "MevcutKlasorListesi+",
                                            mevcutKlasorYolu + klasor.ad
                                        )
                                    }

                                ) {
                                    Row(
                                        modifier = Modifier.padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(modifier = if(isGridView)Modifier.weight(2f)else Modifier.weight(1f)) {
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
                                        CustomDropDown(
                                            tur = Tur.Klasor,
                                            klasorYolu = mevcutKlasorYolu,
                                            ad = klasor.ad,
                                            anasayfaVM = anasayfaVM,
                                            dosyaIslemleriVM = dosyaIslemleriVM,
                                            klasorIslemleriVM = klasorIslemleriVM,
                                            menuVM = menuVM,
                                            ticketVM = ticketVM,
                                            context = context
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

    @Composable
    fun _dosyaBolumu(){
        Column {


            Text(text = "Dosyalar", fontSize = 24.sp)
            Divider(
                Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            )
            if (dosyaIslemleriVM.dosyaListesiBosmu()) Text(text = "Boş")
            else {

                LazyVerticalGrid(
                    columns = if (isGridView) GridCells.Fixed(2) else GridCells.Fixed(
                        1
                    ),
                ) {

                    dosyaDonenSonuc?.sonucListe?.let { it1 ->
                        items(it1.count()) { index ->
                            val dosya = dosyaDonenSonuc?.sonucListe!![index]
                            Card(Modifier.padding(4.dp)) {
                                Row(modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { }) {
                                    Row(
                                        modifier = Modifier.padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(modifier = if(isGridView)Modifier.weight(2f)else Modifier.weight(1f)) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.dosya_icon),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .padding(end = 5.dp),
                                                tint = Color(0xFFFFC107)
                                            )
                                        }
                                        Box(
                                            modifier = Modifier.weight(4f),
                                            contentAlignment = Alignment.CenterStart
                                        ) {
                                            Text(
                                                text = "${dosya.ad} ",
                                                maxLines = 1,
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                        CustomDropDown(
                                            tur = Tur.Dosya,
                                            klasorYolu = mevcutKlasorYolu,
                                            ad = dosya.ad,
                                            anasayfaVM = anasayfaVM,
                                            dosyaIslemleriVM = dosyaIslemleriVM,
                                            klasorIslemleriVM = klasorIslemleriVM,
                                            menuVM = menuVM,
                                            ticketVM = ticketVM,
                                            context = context
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



//    pickedImageUri?.let {
//        Text(it.toString())
//    }
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//        println("Seçilen Dosya ${it.data?.data}")
//        pickedImageUri = it.data?.data
//    }

    BackHandler {
         oncekiKlasoruGetir()
         }
    LaunchedEffect(key1=Unit){

              if(sharedPref.boolAl(SharedText.BeniHatirla,false)) {
                  ticketVM.ticketIDHatirla()
              }

              //TODO benihatıla işaretlenmeden giriş sağlanmıyor
              Log.e("Ticket", ticketID)
              klasorVeDosyaListesiGetir()


 }


    Scaffold (
        topBar = {
           TopAppBar(
               navigationIcon = {
                   if(mevcutKlasorDolumu)
                       IconButton(onClick = {
                           oncekiKlasoruGetir()
                       }) {

                           Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                       }

               },
               title = {
                   Column(modifier = Modifier) {
                       Text(text = mevcutKlasorAd ?: "Duvarım")
                         if(mevcutKlasorYolu.isNotEmpty()) {
                           Text(
                               text = mevcutKlasorYolu,
                               style = MaterialTheme.typography.labelSmall,
                               color = Color(0xFFC0C0C0)
                           )
                       }
                   }
               },
               
               colors = TopAppBarDefaults.smallTopAppBarColors(
                   containerColor = colorResource(id =R.color.dd_mavi),
                   titleContentColor = Color.White
               ),
               actions = {
                   IconButton(onClick = {
                       klasorVeDosyaListesiGetir()
                   }) {
                       Icon(imageVector = Icons.Default.Refresh, contentDescription ="", tint = Color.White)
                   }
                   IconButton(onClick = {
                       anasayfaVM.listeGorunumDegistir()
                   }) {
                       if(isGridView)
                           Icon(imageVector = Icons.Default.List, contentDescription ="", tint = Color.White)
                       else 
                           Icon(painter = painterResource(id = R.drawable.gridview), contentDescription ="", tint = Color.White)
                   }
                   IconButton(onClick = {
                       sharedPref.boolKaydet(SharedText.BeniHatirla,false)
//                       navController.navigate(Sayfalar.GIRIS_EKRAN.page){
//                           popUpTo(Sayfalar.ANASAYFA.page){
//                               inclusive=true
//                           }
//                       }
                   }) {
                      Icon(imageVector = Icons.Default.ExitToApp, contentDescription ="", tint = Color.White )
                   }


               }
               )

        },

        content = {


            Surface(
                modifier = Modifier.padding(top = it.calculateTopPadding())
            ) {
                Column(
                    Modifier
                        .padding(10.dp)
                        .fillMaxSize()
                ) {
                    Box (Modifier.weight(5f)){
                         _klasorBolumu()
                    }

                    Box(Modifier.weight(5f)) {
                        _dosyaBolumu()
                    }
                }
            }
            if(guncelleDialogGoster) {
                KlasorVeDosyaIslemleriDialogWidget(
                    title = "Klasor Guncelle",
                    tfIcerik = "",
                    menuVM=menuVM,
                    onDismiss = {
                        menuVM.guncelleDialogKapat()
                    }, onConfirm = {
                        Log.e("Onconfirm :",ticketID)
                        Log.e("Onconfirm :",mevcutKlasorYolu)
                        Log.e("Onconfirm :",dialogTFDeger)
                        //klasor adi doldurulacak
                        menuVM.klasorGuncelleDialogOnConfirm(context = context, ticketID = ticketID, mevcutKlasor = mevcutKlasorYolu, klasorAdi = dropDownTiklananAd, yeniKlasorAdi = dialogTFDeger)
                        menuVM.guncelleDialogGoster()
                    })
            }
            if(klasorOlusturDialogGoster) {
                KlasorVeDosyaIslemleriDialogWidget(
                    title = "Klasor Oluştur",
                    tfIcerik = "",
                    menuVM = menuVM,
                    onDismiss = {
                        menuVM.klasorOlusturDialogKapat()
                    },
                    onConfirm = {
                        Log.e("tfdeger",dialogTFDeger)
                        menuVM.klasorOlusturDialogOnConfirm(context ,mevcutKlasorYolu)

                    },
                )
            }
            if(tasiDialogGoster){
               TasiDialog(menuVM = menuVM)
            }
        },
        floatingActionButton = {
           Column {


               FabAnimasyon(acildimi = fabAcikmi, buton = {
                   FloatingActionButton(onClick     = {
                       menuVM.klasorOlusturDialogGoster()
                   }, Modifier.padding(bottom = 5.dp)) {
                       Icon(painter = painterResource(id = R.drawable.klasor_olustur_icon), contentDescription = "")
                   }
               })
               FabAnimasyon(acildimi = fabAcikmi, buton = {
                   FloatingActionButton(onClick = {
//                       val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                           .apply {
//                               addCategory(Intent.CATEGORY_OPENABLE)
//                           }
//                       launcher.launch(intent)
                   },Modifier.padding(bottom = 5.dp)) {
                       Icon(painter = painterResource(id = R.drawable.dosya_yukle_icon), contentDescription = "")
                   }
               })
               FloatingActionButton(onClick = {
                   anasayfaVM.fabDurumDegistir()
               }) {
                   if(fabAcikmi)Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription ="")else Icon(imageVector = Icons.Default.Add, contentDescription ="")
               }
           }

                               },
    )


}
@Composable
fun FabAnimasyon(
    acildimi:Boolean,
    buton:@Composable () -> Unit
){
    AnimatedVisibility(
        visible = acildimi,
        enter = fadeIn(initialAlpha = 0f),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))
    )

    {
         buton()
    }
}
