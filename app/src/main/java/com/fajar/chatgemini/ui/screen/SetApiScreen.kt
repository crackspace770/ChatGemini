package com.fajar.chatgemini.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.fajar.chatgemini.R
import com.fajar.chatgemini.ui.data.MainViewModel
import com.fajar.chatgemini.ui.navigation.MultiTurnMode
import com.fajar.chatgemini.ui.navigation.SetApi
import com.fajar.chatgemini.ui.navigation.TopBar
import com.fajar.chatgemini.ui.theme.DecentBlue
import com.fajar.chatgemini.ui.theme.DecentGreen
import com.fajar.chatgemini.ui.theme.DecentRed
import com.fajar.chatgemini.utils.datastore
import com.fajar.chatgemini.utils.getApiKey
import kotlinx.coroutines.runBlocking

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SetApiScreen(
    viewModel: MainViewModel,
    navController: NavHostController
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val validationState =
        viewModel.validationState.observeAsState().value
    val context = LocalContext.current

    var text by remember { mutableStateOf(TextFieldValue(runBlocking { context.datastore.getApiKey() })) }
    Scaffold(
        topBar = {
            TopBar(
                name = stringResource(id = R.string.set_api),
                navController = navController,
                showNavigationIcon = viewModel.isHomeVisit.value
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                singleLine = true,
                value = text,
                onValueChange = { newText ->
                    text = newText
                    viewModel.resetValidationState()
                },
                placeholder = {
                    Text(
                        color = MaterialTheme.colorScheme.inversePrimary,
                        text = "Enter your api key"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(10.dp),
                shape = RoundedCornerShape(28),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.W500,
                    fontSize = 18.sp
                )
            )

            Button(
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    if (validationState == MainViewModel.ValidationState.Valid) {
                        if (viewModel.isHomeVisit.value == true) {
                            navController.navigateUp()
                        } else {
                            navController.popBackStack(SetApi.route, true)
                            navController.navigate(MultiTurnMode.route)
                        }
                    } else if (validationState == MainViewModel.ValidationState.Idle) {
                        viewModel.validate(context, text.text)
                    }
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (validationState) {
                        MainViewModel.ValidationState.Checking -> Color.DarkGray
                        MainViewModel.ValidationState.Idle -> DecentBlue
                        MainViewModel.ValidationState.Invalid -> DecentRed
                        MainViewModel.ValidationState.Valid -> DecentGreen
                        null -> DecentBlue
                    },
                    contentColor = Color.White
                )
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = when (validationState) {
                        MainViewModel.ValidationState.Checking -> "Validating..."
                        MainViewModel.ValidationState.Idle -> "Validate"
                        MainViewModel.ValidationState.Invalid -> "Invalid Key"
                        MainViewModel.ValidationState.Valid -> "Continue"
                        else -> "NULL"
                    },
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.padding(30.dp))


        }
    }
}

