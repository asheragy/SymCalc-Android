package org.cerion.symcalcapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import org.cerion.symcalc.expression.Expr
import org.cerion.symcalc.function.core.N
import org.cerion.symcalcapp.ui.theme.SymCalcTheme

class DebugActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SymCalcTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Input()
                }
            }
        }
    }
}

@Composable
fun Input() {
    var input by remember { mutableStateOf("") }
    val isChecked = remember { mutableStateOf(true) }

    Column {
        TextField(
            value = input,
            textStyle = TextStyle(fontSize = 30.sp),
            onValueChange = { input = it }
        )
        Checkbox(checked = isChecked.value, onCheckedChange = {
            isChecked.value = it
        })
        val expr = Expr.parse(input)
        Text(
            fontSize = 16.sp,
            text = if (isChecked.value)
            N(expr).eval().toString()
        else
            Expr.parse(input).eval().toString()
        )
    }

}

@Preview(showBackground = true)
@Composable
fun defaultPreview() {
    SymCalcTheme {
        Input()
    }
}