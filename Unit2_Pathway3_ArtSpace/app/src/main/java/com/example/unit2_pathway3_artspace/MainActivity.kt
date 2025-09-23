package com.example.unit2_pathway3_artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.unit2_pathway3_artspace.ui.theme.Unit2_Pathway3_ArtSpaceTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Unit2_Pathway3_ArtSpaceTheme {
                ArtSpaceApp()
            }
        }
    }
}

data class Artwork(
    val imageRes: Int,
    val title: String,
    val artist: String
)

@Composable
fun loadArtworks(): List<Artwork> {
    val context = LocalContext.current
    val res = context.resources

    val images = res.obtainTypedArray(R.array.artworks)
    val titles = res.getStringArray(R.array.artwork_titles)
    val artists = res.getStringArray(R.array.artwork_artists)

    val list = (titles.indices).map { i ->
        Artwork(
            imageRes = images.getResourceId(i, 0),
            title = titles[i],
            artist = artists[i]
        )
    }

    images.recycle()
    return list
}


@Composable
fun ArtSpaceApp() {
    val artworks = loadArtworks()
    var artStep by remember { mutableStateOf(0) }

    val currentArtwork = artworks[artStep]

    ArtCompose(
        artRes = currentArtwork.imageRes,
        artworkTitle = currentArtwork.title,
        artworkArtist = currentArtwork.artist,
        modifier = Modifier.padding(16.dp),
        onNext = { artStep = (artStep + 1) % artworks.size },
        onPrevious = { artStep = (artStep - 1 + artworks.size) % artworks.size }
    )
}


@Composable
fun ArtCompose(
    artRes: Int,
    artworkTitle: String,
    artworkArtist: String,
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(artRes),
            contentDescription = artworkTitle,
            contentScale = ContentScale.Crop,
            modifier = Modifier.weight(1f)
        )

        Column {
            Text(
                text = artworkTitle,
                fontSize = 28.sp
            )
            Text(
                text = artworkArtist,
                fontSize = 18.sp
            )
        }

        Row(modifier = Modifier.padding(top = 16.dp)) {
            Button(onClick = onPrevious, modifier = Modifier.weight(1f)) {
                Text("Previous")
            }
            Button(onClick = onNext, modifier = Modifier.weight(1f)) {
                Text("Next")
            }
        }
    }
}
