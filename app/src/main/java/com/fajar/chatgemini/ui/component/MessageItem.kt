package com.fajar.chatgemini.ui.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fajar.chatgemini.Mode
import com.fajar.chatgemini.R
import com.fajar.chatgemini.ui.theme.ChatGeminiTheme
import com.fajar.chatgemini.ui.theme.DecentBlue
import com.fajar.chatgemini.ui.theme.DecentGreen


@Composable
fun MessageItem(
    text:String,
    mode:Mode
) {

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val toast = Toast.makeText(context, stringResource(R.string.text_copied), Toast.LENGTH_LONG)

    val cardBackgroundColor = when (mode) {
        Mode.GEMINI -> Color.Blue // Define color for GEMINI mode
        Mode.USER -> Color.DarkGray // Define color for USER mode
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(5.dp)
    ) {

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically){

                Image(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(100)),
                    painter = when(mode){
                        Mode.GEMINI -> painterResource(id = R.drawable.gemini)
                        Mode.USER -> painterResource(id = R.drawable.user) },
                    contentDescription ="logo"
                )

                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W600, text = when (mode) {
                        Mode.GEMINI -> "GEMINI"
                        Mode.USER -> "YOU"
                    }
                    )

            }

        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {

            Card(
                modifier = Modifier
                    .width(350.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cardBackgroundColor,
                ),
            ){
                SelectionContainer(

                ) {
                    Text(
                        modifier = Modifier
                            .padding(15.dp),
                        fontWeight = FontWeight.W500,
                        fontSize = 15.sp,
                        text = text,
                        color = Color.White
                    )
                }

            }

        }


    }

}

@Preview(showBackground = true)
@Composable
fun MessagePreview() {
    ChatGeminiTheme {
        MessageItem(text = "message", mode = Mode.USER)
    }
}