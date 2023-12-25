package com.example.divvydrivestaj.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.divvydrivestaj.viewmodel.DosyaIslemleriVM
import com.example.divvydrivestaj.viewmodel.KlasorIslemleriVM
import com.example.divvydrivestaj.viewmodel.MenuVM

@Composable
fun TasiDialog(
    menuVM: MenuVM,
    klasorIslemleriVM: KlasorIslemleriVM,
    dosyaIslemleriVM: DosyaIslemleriVM
) {

            AlertDialog(
                onDismissRequest = {
                   menuVM.tasiDialogKapat()
                },
                title = {
                    Text("Compose Dialog with Card")
                },
                text = {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {

                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                                  //klasorTasi
                                  //dosyaTasi
                        },
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                                  menuVM.tasiDialogKapat()
                        },
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
