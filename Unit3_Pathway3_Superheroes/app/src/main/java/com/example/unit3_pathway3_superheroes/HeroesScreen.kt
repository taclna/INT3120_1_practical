package com.example.unit3_pathway3_superheroes

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun HeroesScreen(modifier: Modifier = Modifier) {
    val heroes = loadHero()
    var showTeams by rememberSaveable { mutableStateOf(false) }

    val (teamA, teamB) = heroes.partitionIndexed { index, _ -> index % 2 == 0 }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showTeams = !showTeams }
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Team Up"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = if (showTeams) "Teams" else "Superheroes",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            if (showTeams) {
                if (isLandscape) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        TeamList("Team A", teamA, Modifier.weight(1f))
                        TeamList("Team B", teamB, Modifier.weight(1f))
                    }
                } else {
                    Column(modifier= Modifier.fillMaxHeight()) {
                        TeamList("Team A", teamA, Modifier.weight(1f))
                        TeamList("Team B", teamB, Modifier.weight(1f))
                    }
                }
            } else {
                LazyColumn(
                    modifier = modifier
                        .padding(8.dp)
                        .fillMaxHeight()
                ) {
                    items(heroes) { hero ->
                        HeroCard(
                            nameHeroRes = hero.nameRes,
                            descriptionRes = hero.descriptionRes,
                            heroImageRes = hero.imageRes,
                            modifier = modifier
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TeamList(title: String, heroes: List<Hero>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(heroes) { hero ->
                HeroCard(
                    nameHeroRes = hero.nameRes,
                    descriptionRes = hero.descriptionRes,
                    heroImageRes = hero.imageRes
                )
            }
        }
    }
}

fun <T> List<T>.partitionIndexed(predicate: (Int, T) -> Boolean): Pair<List<T>, List<T>> {
    val first = mutableListOf<T>()
    val second = mutableListOf<T>()
    forEachIndexed { index, item ->
        if (predicate(index, item)) first.add(item) else second.add(item)
    }
    return first to second
}

fun loadHero(): List<Hero> {
    return listOf(
        Hero(R.string.hero1, R.string.description1, R.drawable.android_superhero1),
        Hero(R.string.hero2, R.string.description2, R.drawable.android_superhero2),
        Hero(R.string.hero3, R.string.description3, R.drawable.android_superhero3),
        Hero(R.string.hero4, R.string.description4, R.drawable.android_superhero4),
        Hero(R.string.hero5, R.string.description5, R.drawable.android_superhero5),
        Hero(R.string.hero6, R.string.description6, R.drawable.android_superhero6)
    )
}

@Composable
fun HeroCard(
    nameHeroRes: Int,
    descriptionRes: Int,
    heroImageRes: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .sizeIn(maxHeight = 72.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(id = nameHeroRes),
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = stringResource(id = descriptionRes),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = heroImageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}
