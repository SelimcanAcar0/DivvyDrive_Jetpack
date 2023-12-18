package com.example.divvydrivestaj.widgets
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.divvydrivestaj.R
import com.example.divvydrivestaj.viewmodel.AnasayfaVM
import com.example.divvydrivestaj.viewmodel.KlasorIslemleriVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialogWidget(tfDeger:String, dialogGoster: (Boolean) -> Unit) {
    val anasayfaVM=AnasayfaVM()
    val klasorIslemleriVM=KlasorIslemleriVM()
    val tfError = remember { mutableStateOf("") }
    val tf = remember { mutableStateOf(tfDeger) }
    val klasorOlusturDonenSonuc = klasorIslemleriVM.klasorVeDosyaIslemleriDonenSonuc.collectAsState()
    val context= LocalContext.current
    Dialog(onDismissRequest = { dialogGoster(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            border = BorderStroke(width = 2.dp, color = colorResource(id = R.color.dd_mavi)),

        ) {
            Card(modifier = Modifier) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Klasor Oluştur",

                                )
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "",
                                tint = colorResource(android.R.color.darker_gray),
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                                    .clickable { dialogGoster(false) }
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    BorderStroke(
                                        width = 2.dp,
                                        color = colorResource(id = if (tfError.value.isEmpty()) R.color.dd_mavi else R.color.dd_mavi_acik)
                                    ),
                                    shape = RoundedCornerShape(50)
                                ),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            placeholder = { Text(text = "Klasör Adı") },
                            value =  tf.value,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            onValueChange = {
                                Log.e("tf",it)
                                tf.value = it
                            })

                        Spacer(modifier = Modifier.height(20.dp))

                        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                            Button(
                                onClick =  {
                                    CoroutineScope(Dispatchers.Main).launch {

                                        klasorIslemleriVM.klasorOlustur(
                                            "2b6c9bb0-1483-4a55-bf92-9274f2394ab7",
                                            tf.value,
                                            klasorIslemleriVM.mevcutKlasorYolu.value!!,
                                        )
                                        anasayfaVM.refreshDurumDegistir(true)
                                        delay(2000)
                                        klasorIslemleriVM.klasorListesiGetir( "2b6c9bb0-1483-4a55-bf92-9274f2394ab7", klasorIslemleriVM.mevcutKlasorYolu.value!!)
                                        anasayfaVM.refreshDurumDegistir(false)
                                    }

                                        if(klasorOlusturDonenSonuc.value?.sonuc==false){
                                            Toast.makeText(context, klasorOlusturDonenSonuc.value?.mesaj, Toast.LENGTH_SHORT).show()
                                            return@Button
                                        }

                                         Log.e("if",klasorOlusturDonenSonuc.value?.mesaj.toString())
                                        dialogGoster(false)


                                 },

                                shape = RoundedCornerShape(50.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Text(text = "Oluştur")
                            }
                        }
                    }
                }
            }
        }
    }
}