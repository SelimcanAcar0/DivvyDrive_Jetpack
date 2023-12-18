package com.example.divvydrivestaj.widgets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.divvydrivestaj.viewmodel.MenuVM


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KlasorVeDosyaIslemleriDialogWidget(
    title:String,
    tfIcerik:String,
    onDismiss:()->Unit,
    onConfirm:()->Unit,
    dialogVM: MenuVM =MenuVM()
) {

    val tf=dialogVM.klasorOlusturDialogTF.collectAsState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(title)
        },
                text = {
                    TextField(
                        value =tf.value,
                        label = { Text(text = tfIcerik)},
                        onValueChange = {
                           dialogVM.klasorOlusturDialogTFGuncelle(it)
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                            }
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
            }) {
                Text("Onayla")

            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(text = "İptal")

            }
        }
    )
}

