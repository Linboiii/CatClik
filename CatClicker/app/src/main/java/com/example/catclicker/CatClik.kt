package com.example.catclicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class CatClickerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CatClickerScreen()
            }
        }
    }
}

@Composable
fun CatClickerScreen() {
    var coins by remember { mutableLongStateOf(0L) }
    var coinsPerTap by remember { mutableDoubleStateOf(1.0) }
    var upgradeCost by remember { mutableLongStateOf(10L) }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val nextCoinsPerTap = coinsPerTap * 1.5

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 40.dp, horizontal = 24.dp)
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.85f)),
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Your Coins", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    Spacer(Modifier.height(4.dp))
                    Text("$coins", fontSize = 42.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00C853))
                    Spacer(Modifier.height(4.dp))
                    Text("${coinsPerTap.toInt()} coins per tap", fontSize = 13.sp, color = Color.Gray)
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Tap the Cat!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(12.dp))

                Image(
                    painter = painterResource(id = if (isPressed) R.drawable.co else R.drawable.cc),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            coins += coinsPerTap.toLong()
                        }
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    if (isPressed) "Meow!" else "Purr~",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Give Me Your Coin", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Next upgrade: +${(nextCoinsPerTap - coinsPerTap).toInt()} coins per tap",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(Modifier.height(14.dp))

                    val canUpgrade = coins >= upgradeCost
                    val neededCoins = upgradeCost - coins

                    Button(
                        onClick = {
                            if (canUpgrade) {
                                coins -= upgradeCost
                                coinsPerTap *= 1.5
                                upgradeCost *= 2
                            }
                        },
                        enabled = canUpgrade,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50),
                            disabledContainerColor = Color(0xFFD3D3D3)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            if (canUpgrade) "Pay for $upgradeCost coins" else "Find $neededCoins more coins",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}