package com.example.divvydrivestaj

import SharedPref
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.divvydrivestaj.constant.Sayfalar
import com.example.divvydrivestaj.constant.SharedText
import com.example.divvydrivestaj.entity.Kullanici
import com.example.divvydrivestaj.ui.theme.TfRengi
import com.example.divvydrivestaj.viewmodel.AnasayfaVM
import com.example.divvydrivestaj.viewmodel.GirisVM
import com.example.divvydrivestaj.viewmodel.TicketVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GirisEkran(navController: NavHostController,
               context: Context = LocalContext.current,
               sharedPref: SharedPref =SharedPref(context),
               ticketVM: TicketVM,
               anasayfaVM: AnasayfaVM,
               girisVM: GirisVM,
)
{
    val ddMavisi = colorResource(id = R.color.dd_mavi)
    val kullaniciAdi = girisVM.kullaniciAdiTF.collectAsState()
    val sifre = girisVM.sifreTF.collectAsState()
    val sifreGizlensinmi = girisVM.sifreGizlensinmi.collectAsState()
    val beniHatirla = girisVM.beniHatirla.collectAsState()
    val isRefreshing = anasayfaVM.refreshing.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(modifier = Modifier.padding(12.dp), shape = RoundedCornerShape(corner = CornerSize(20.dp)),
                border = BorderStroke(2.dp, color = ddMavisi), elevation = CardDefaults.cardElevation(defaultElevation = 30.dp),) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
                    Image(
                        modifier = Modifier.padding(vertical = 10.dp),
                        painter = painterResource(id = R.drawable.dd_logo),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = kullaniciAdi.value,
                        onValueChange = { girisVM.kullaniciAdiYaz(it)},
                        label = { Text(text = "Kullanıcı Adı") },
                        maxLines = 1,

                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Person, contentDescription ="")
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                        , colors = TfRengi()
                    )

                    OutlinedTextField(

                        value = sifre.value,
                        onValueChange = { girisVM.sifreYaz(it) },
                        label = { Text(text = "Şifre") },
                        maxLines = 1,
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Lock, contentDescription ="")
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                girisVM.sifreGosterDurumDegistir()
                            }) {
                                if(sifreGizlensinmi.value) {
                                    Icon(
                                        painter = painterResource(R.drawable.goster_icon),
                                        contentDescription = null
                                    )
                                }
                                else
                                    Icon(
                                        painter = painterResource(R.drawable.gizle_icon),
                                        contentDescription = null
                                    )
                            }
                        },


                        visualTransformation =if(sifreGizlensinmi.value)PasswordVisualTransformation() else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                            colors = TfRengi()
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Checkbox(
                                checked = beniHatirla.value,
                                onCheckedChange = { girisVM.beniHatirlaDurumDegistir(it) },
                                modifier = Modifier.size(24.dp),
                                colors = CheckboxDefaults.colors(
                                    checkedColor = ddMavisi,
                                    checkmarkColor = Color.White

                                )
                            )
                            Text(text = "Beni Hatırla")
                        }
                    }

                    Button(
                        onClick = {
                               CoroutineScope(Dispatchers.Main).launch {
                                   anasayfaVM.refreshDurumDegistir(true)
                                   val kullanici = Kullanici(kullaniciAdi.value, sifre.value)
                                   ticketVM.ticketAl(kullanici)
                                   delay(2000)
                                   when (ticketVM.ticket.value?.sonuc) {
                                       false -> {
                                           Handler(Looper.getMainLooper()).post {
                                               Toast.makeText(context, "Giriş Başarısız", Toast.LENGTH_SHORT).show()
                                           }
                                       }
                                       true -> {
                                           if(beniHatirla.value){
                                               sharedPref.boolKaydet(SharedText.BeniHatirla,true)
                                               sharedPref.stringKaydet(SharedText.Ticket,ticketVM.ticket.value!!.ID)
                                           }
                                           Log.e("Ticket", "${ticketVM.ticket.value?.sonuc}")
                                           navController.navigate(Sayfalar.ANASAYFA.page) {
                                               popUpTo(Sayfalar.GIRIS_EKRAN.page) {
                                                   inclusive = true
                                               }
                                           }
                                       }
                                       else -> {
                                           Handler(Looper.getMainLooper()).post {
                                               Toast.makeText(context, "Sunucuya Bağlanılamadı", Toast.LENGTH_SHORT).show()
                                           }
                                       }
                                   }

                                   anasayfaVM.refreshDurumDegistir(false)



                            }


                        },
                        modifier = Modifier
                            .padding(vertical = 30.dp, horizontal = 20.dp)
                            .fillMaxWidth(), colors =ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.dd_mavi),
                                contentColor = Color.White
                            )
                    ) {
                        Text(text = "Giriş", fontSize = 16.sp)

                    }
                    if(isRefreshing.value){
                        CircularProgressIndicator(color = colorResource(id = R.color.dd_mavi), strokeWidth = 5.dp)
                    }
                }

        }

    }
}