package org.cerion.symcalcapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.cerion.symcalcapp.ui.theme.SymCalcTheme

@Composable
fun KeyPad(onKeyPress: (key: String) -> Unit) {
    Row(Modifier.fillMaxWidth()) {
        Column(Modifier.weight(0.75f)) {
            Row(Modifier.weight(1.0f)) {
                Key("7", onKeyPress)
                Key("8", onKeyPress)
                Key("9", onKeyPress)
            }
            Row(Modifier.weight(1.0f)) {
                Key("4", onKeyPress)
                Key("5", onKeyPress)
                Key("6", onKeyPress)
            }
            Row(Modifier.weight(1.0f)) {
                Key("1", onKeyPress)
                Key("2", onKeyPress)
                Key("3", onKeyPress)
            }
            Row(Modifier.weight(1.0f)) {
                Key("0", onKeyPress)
                Key(".", onKeyPress)
                Key("=", onKeyPress)
            }
        }
        Column(Modifier.weight(0.25f).fillMaxHeight()) {
            Key("DEL", onKeyPress)
            Key("/", onKeyPress)
            Key("X", onKeyPress)
            Key("-", onKeyPress)
            Key("+", onKeyPress)
        }
    }
}

@Composable
fun ColumnScope.Key(label: String, onKeyPress: (key: String) -> Unit) {
    Button(onClick = { onKeyPress(label) }, Modifier.fillMaxWidth().weight(1f)) {
        Text(text = label)
    }
}

@Composable
fun RowScope.Key(label: String, onKeyPress: (key: String) -> Unit) {
    Button(onClick = { onKeyPress(label) }, Modifier.fillMaxHeight().weight(1f)) {
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