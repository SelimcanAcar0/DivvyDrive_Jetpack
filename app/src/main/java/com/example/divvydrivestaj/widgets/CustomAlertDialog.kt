package com.example.divvydrivestaj.widgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun CustomAlertDialog(baslik:String,icerik:String, onaylaButonIslev:() -> Unit,reddetButonIslev:()->Unit) {
    val openDialog = remember { mutableStateOf(false)  }
    AlertDialog(
        text = { Text(text =(baslik)) },
        title =  { Text(text =(icerik)) },
        onDismissRequest ={
               openDialog.value=false
        } ,
        dismissButton = {
             Button(
                 onClick = reddetButonIslev
             ) {
                      Text(text = "Hayır")
             }
        },
        confirmButton = {
              Button(onClick = onaylaButonIslev ) {
                  Text(text = "Evet")
              }

        })}
