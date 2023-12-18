package com.example.divvydrivestaj.ui.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.divvydrivestaj.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun TfRengi():TextFieldColors{
    val ddMavisi = colorResource(id = R.color.dd_mavi)
    val ddMavisiAcik = colorResource(id = R.color.dd_mavi_acik)
    return TextFieldDefaults.textFieldColors(
        disabledLeadingIconColor = ddMavisi,
        focusedLeadingIconColor = ddMavisiAcik,
        unfocusedLeadingIconColor = ddMavisi,
        errorLeadingIconColor = ddMavisi,
        cursorColor = ddMavisi,
        focusedIndicatorColor = ddMavisiAcik, // Değiştirmek istediğiniz renkleri burada belirtebilirsiniz
        unfocusedIndicatorColor = ddMavisi,
        errorIndicatorColor = Color.Red,
        focusedLabelColor = Color.White
    )
}