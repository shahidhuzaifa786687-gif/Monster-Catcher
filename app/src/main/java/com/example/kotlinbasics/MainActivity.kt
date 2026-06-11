package com.example.kotlinbasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.BoxWithConstraints
import com.example.kotlinbasics.ui.theme.KotlinBasicsTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinBasicsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding).padding(30.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var gameStarted by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }
    var timeLeft by remember { mutableStateOf(30) }
    var highScore by remember { mutableStateOf(0) }
    var monsterX by remember { mutableStateOf(0f) }
    var monsterY by remember { mutableStateOf(0f) }
    var currentCharacter by remember { mutableStateOf("👻") }
    val animatedX by animateFloatAsState(
        targetValue = monsterX,

    )
    val animatedY by animateFloatAsState(
        targetValue = monsterY,

    )




    LaunchedEffect(gameStarted) {
        if (!gameStarted) return@LaunchedEffect

        while (true) {
            delay(1000)
            if (timeLeft > 0) timeLeft--
        }
    }

    LaunchedEffect(gameStarted) {
        if (!gameStarted) return@LaunchedEffect

        while (timeLeft > 0) {
            delay(1500)
            val chance = Random.nextInt(3)
            when (chance) {
                0 -> currentCharacter = "👻"
                1 -> currentCharacter = "💀"
                2 -> currentCharacter = "🤦‍♀️"
            }
            delay(1000)
            currentCharacter = "👻"

            monsterX = Random.nextInt(-130, 130).toFloat()
            monsterY = Random.nextInt(0, 300).toFloat()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "👻Monster catcher👻",
            color = Color.Red,
            fontSize = 30.sp
        )
        Text(
            text = "🏆Score=$score",
            color = Color.Yellow,
            fontSize = 30.sp
        )
        Text(
            text = " ⭐ HighScore=$highScore",
            color = Color.Yellow,
            fontSize = 30.sp
        )
        Text(
            text = "⏳TimeLeft=$timeLeft",
            color = Color.Yellow,
            fontSize = 30.sp

        )
        if (!gameStarted) {
            Button(onClick = {
                gameStarted = true
                score = 0
                timeLeft = 30
            }) {
                Text("Start Game")
            }
        } else if (timeLeft > 0) {
            Text(
                text = currentCharacter,
                color = Color.Red,
                fontSize = 90.sp,
                modifier = Modifier.offset(x = animatedX.dp, y = animatedY.dp)
                    .clickable {
                when (currentCharacter) {
                    "👻" -> {
                        score++
                        if (score > highScore) highScore = score
                    }
                    "💀" -> if (score > 0) score--
                    "🤦‍♀️" -> {
                        score=0
                    }
                }

                monsterX = Random.nextInt(-150, 150).toFloat()
                monsterY = Random.nextInt(0, 400).toFloat()
            }
            )
        } else {
            Text("Game Over!", color = Color.White, fontSize = 40.sp)
            Button(onClick = {
                score = 0
                timeLeft = 30
                gameStarted = false
            }) {
                Text("Play Again")
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinBasicsTheme {
        Greeting("Android")
    }
}