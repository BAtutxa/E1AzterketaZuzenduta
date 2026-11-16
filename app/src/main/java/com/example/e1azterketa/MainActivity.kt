package com.example.e1azterketa

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e1azterketa.data.DatuakIrakurri
import com.example.e1azterketa.ui.theme.E1AzterketaTheme
import com.example.e1azterketa.data.Pintxoa
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Button

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            E1AzterketaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LeihoNagusia(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LeihoNagusia(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val datuak = remember { DatuakIrakurri().datuakKargatu(context, "datuak.json" ) }
    val salmentaKop = datuak.size
    var salmentaTot = 0.0
    for (datua in datuak)
        salmentaTot += datua.prezioa

    Column(modifier = Modifier.padding(5.dp)) {
        Text(
            text = "Eguneko Salmentak",
            fontSize = 25.sp,

            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Samenta guztiak: $salmentaKop - $salmentaTot €"
        )
        SalmentaBerria()
        Salmentak(datuak)

    }
}

@Composable
fun SalmentaBerria (){
    var id: Int? by remember { mutableStateOf (0)  }
    var izena: String? by remember { mutableStateOf ("")  }
    var prezioa: Double? by remember { mutableStateOf(0.0) }
    var balorazioa: Double? by remember { mutableStateOf(0.0) }
    var salmenta: String by remember { mutableStateOf("") }


    Card(modifier = Modifier.fillMaxWidth()
        .padding(5.dp)) {
            Column(){
                TextField(
                    value ="$id",
                    onValueChange =  {id = it.toIntOrNull()}
                )
                TextField(
                    value ="$izena",
                    onValueChange =  {izena = it}
                )
                TextField(
                    value ="$prezioa",
                    onValueChange =  {prezioa = it.toDoubleOrNull()}
                )
                TextField(
                    value ="$balorazioa",
                    onValueChange =  {balorazioa = it.toDoubleOrNull()}
                )
                Button(
                    onClick = ({salmenta = "$id $izena $prezioa $balorazioa"})
                ){
                    Text(text="Gorde")
                }
                Text (text="Kaixo: $salmenta")
            }
    }
}

@Composable
fun Salmentak (salmentak:List<Pintxoa>){
    LazyColumn() {
        items(salmentak){salmenta -> PintxoCard(salmenta) }
    }
}

@Composable
fun PintxoCard (pintxoa: Pintxoa, modifier: Modifier = Modifier){
    Card(modifier = Modifier.padding(10.dp)
        .fillMaxWidth()) {
        Row(modifier = Modifier.padding(10.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Box(modifier = Modifier.size(100.dp)){
                Image(
                    painter = painterResource(R.drawable.pintxoa),
                    contentDescription = "Pintxoa",
                    contentScale = ContentScale.Fit,
                )
                Text(
                    text = "${pintxoa.balorazioa}",
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth()
                        .padding(end = 20.dp)
                )
            }
            Column(modifier = Modifier.padding(5.dp)
                .fillMaxWidth()) {
                Text(
                    text = pintxoa.izena,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Salmenta id: ${pintxoa.id} Prezioa: ${pintxoa.prezioa}"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeihoNagusiaPreview() {
    E1AzterketaTheme {
        SalmentaBerria()
        //PintxoCard(Pintxoa(1,"3PAG2",4.0,5.0))
    }
}