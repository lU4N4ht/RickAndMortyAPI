package br.senai.sp.jandira.rickandmorty.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.rickandmorty.model.Character
import br.senai.sp.jandira.rickandmorty.model.Results
import br.senai.sp.jandira.rickandmorty.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ListAllCharacters(modifier: Modifier = Modifier) {

    var characterList by remember {
        mutableStateOf(listOf<Character>())
    }
    val charactersCall = RetrofitFactory()
        .getCharacterService()
        .getAllCharacters()

    charactersCall.enqueue(
        object : Callback<Results>{
            override fun onResponse(p0: Call<Results>, response: Response<Results>) {
                characterList = response.body()!!.results
            }

            override fun onFailure(p0: Call<Results>, p1: Throwable) {
            }
        }
    )
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(color = 0xFFD8F6FA)
    ){
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "The Rick and Morty API",
                fontSize = 24.sp,
                fontWeight = FontWeight.W900,
                color = Color(color = 0xFF265f62)
            )

            LazyColumn(
                modifier = Modifier.padding(top = 25.dp)
            ){
                items(characterList) {
                    CharacterCard(it)
                }
            }
        }

    }
}

@Composable
fun CharacterCard(character: Character) {
    val context = LocalContext.current
    Card (
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
                Toast
                    .makeText(context, "Personagem: ${character.id}", Toast.LENGTH_SHORT)
                    .show()
            },
        colors = CardDefaults.cardColors(Color(color = 0xffb4dad0)),

        ) {
        Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start

            ) {
            Card(
                modifier = Modifier
                    .size(80.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(Color(color = 0xFFd5e9f0))
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription =  ""
                )
            }

            Column (
                modifier = Modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center

            ){
                Text(
                    text = character.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff265f62)

                )
                Text(
                    text = character.species,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF317579)

                )
            }
        }

    }
}

@Preview
@Composable
private fun CharacterCardPreview() {
//    CharacterCard()
}

@Preview
@Composable
private fun AllCharactersPreview() {
    ListAllCharacters()


}


