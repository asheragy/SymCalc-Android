package org.cerion.symcalcapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.cerion.symcalcapp.ui.theme.SymCalcTheme

@Composable
fun KeyPad(onKeyPress: (key: Key) -> Unit) {
    Row(Modifier.fillMaxWidth()) {
        Column(Modifier.weight(0.75f)) {
            Row(Modifier.weight(1.0f)) {
                Key("7") { onKeyPress(Key.NUM_7) }
                Key("8") { onKeyPress(Key.NUM_8) }
                Key("9") { onKeyPress(Key.NUM_9) }
            }
            Row(Modifier.weight(1.0f)) {
                Key("4") { onKeyPress(Key.NUM_4) }
                Key("5") { onKeyPress(Key.NUM_5) }
                Key("6") { onKeyPress(Key.NUM_6) }
            }
            Row(Modifier.weight(1.0f)) {
                Key("1") { onKeyPress(Key.NUM_1) }
                Key("2") { onKeyPress(Key.NUM_2) }
                Key("3") { onKeyPress(Key.NUM_3) }
            }
            Row(Modifier.weight(1.0f)) {
                Key("0") { onKeyPress(Key.NUM_0) }
                Key(".") { onKeyPress(Key.DOT) }
                Key("=") { onKeyPress(Key.EVAL) }
            }
        }
        Column(Modifier.weight(0.25f).fillMaxHeight()) {
            Key("DEL") { onKeyPress(Key.DEL) }
            Key("/") { onKeyPress(Key.DIVIDE) }
            Key("*") { onKeyPress(Key.TIMES) }
            Key("-") { onKeyPress(Key.MINUS) }
            Key("+") { onKeyPress(Key.PLUS) }
        }
    }
}

@Composable
fun ColumnScope.Key(label: String, onKeyPress:() -> Unit) {
    Button(onClick = { onKeyPress() }, Modifier.fillMaxWidth().weight(1f)) {
        Text(text = label)
    }
}

@Composable
fun RowScope.Key(label: String, onKeyPress:() -> Unit) {
    Button(onClick = { onKeyPress() }, Modifier.fillMaxHeight().weight(1f)) {
        Text(text = label)
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SymCalcTheme {
        KeyPad(onKeyPress = {})
    }
}