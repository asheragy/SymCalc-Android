package org.cerion.symcalcapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.cerion.symcalcapp.ui.theme.SymCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SymCalcTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator() {
    var display by remember { mutableStateOf("2+2") }

    val onClick = { key: String ->
        display += key
    }

    Column {

        TextField(value = display, onValueChange = {}, textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End))
        KeyPad(onClick)
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SymCalcTheme {
        Calculator()
    }
}