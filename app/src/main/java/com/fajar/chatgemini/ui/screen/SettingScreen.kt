package com.fajar.chatgemini.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fajar.chatgemini.R
import com.fajar.chatgemini.ui.navigation.SetApi
import com.fajar.chatgemini.ui.navigation.TopBar
import com.fajar.chatgemini.ui.theme.ChatGeminiTheme
import com.fajar.chatgemini.ui.theme.DecentBlue

@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingScreen(
    navController: NavHostController
) {

    Scaffold(
        topBar = {
            TopBar(name = stringResource(
                id = R.string.settings),
                navController = navController)
        }
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Button(
                onClick = {
                    navController.navigate(SetApi.route)
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DecentBlue,
                    contentColor = Color.White
                )

            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Change API Key",
                    fontSize = 15.sp
                    )

            }

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewSetting(){
    val navController = rememberNavController()
    ChatGeminiTheme {
        SettingScreen(navController=navController)
    }
}