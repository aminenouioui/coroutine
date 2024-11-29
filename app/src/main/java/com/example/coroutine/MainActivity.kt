package com.example.coroutine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovementSimulation()
        }
    }
}

@Composable
fun MovementSimulation() {

    var circleXPosition by remember { mutableStateOf(0f) }
    var circleYPosition by remember { mutableStateOf(0f) }
    var isMoving by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val animatedXPosition by animateFloatAsState(targetValue = circleXPosition)
    val animatedYPosition by animateFloatAsState(targetValue = circleYPosition)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {

        Box(
            modifier = Modifier
                .offset(x = animatedXPosition.dp, y = animatedYPosition.dp)
                .size(50.dp)
                .background(Color.Red, CircleShape)
        )


        Spacer(modifier = Modifier.weight(1f))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Start Button
            Button(onClick = {
                if (!isMoving) {
                    isMoving = true
                    coroutineScope.launch {
                        for (i in 0..1000 step 10) {
                            if (!isMoving) break
                            circleXPosition = 0f
                            circleYPosition = i.toFloat()
                            delay(16L)
                        }
                    }
                }
            }) {
                Text(text = "Start Movement")
            }


            Button(onClick = {
                isMoving = false
                circleXPosition = 0f
                circleYPosition = 0f
            }) {
                Text(text = "Reset")
            }
        }
    }
}
