package com.example.wishlistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlistapp.data.Wish
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
) {
    val snackMessage = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    if (id != 0L) {
        val wish = viewModel.getAWishById(id).collectAsState(initial = Wish())
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    } else {
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }

    // Function to handle save/update operation
    fun handleSaveOrUpdate() {
        keyboardController?.hide() // Hide keyboard first

        scope.launch {
            delay(300) // Wait for keyboard to close

            if (viewModel.wishTitleState.isNotEmpty() &&
                viewModel.wishDescriptionState.isNotEmpty()
            ) {
                if (id != 0L) {
                    // Update existing wish
                    viewModel.updateWish(
                        Wish(
                            id = id,
                            title = viewModel.wishTitleState.trim(),
                            description = viewModel.wishDescriptionState.trim()
                        )
                    )
                    snackMessage.value = "Wish has been updated"
                } else {
                    // Add new wish
                    viewModel.addWish(
                        Wish(
                            title = viewModel.wishTitleState.trim(),
                            description = viewModel.wishDescriptionState.trim()
                        )
                    )
                    snackMessage.value = "Wish has been created"
                }
            } else {
                snackMessage.value = "Please fill all fields"
            }

            // Show snackbar briefly then navigate back
            snackbarHostState.showSnackbar(snackMessage.value)
            delay(1000) // Show snackbar for 1 second
            navController.navigateUp() // Navigate back to previous page
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            AppBarView(
                title = if (id != 0L) stringResource(R.string.update_wish)
                else stringResource(R.string.add_wish)
            ) { navController.navigateUp() }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(
                label = "Title",
                value = viewModel.wishTitleState,
                onValueChange = {
                    viewModel.onWishTitleChange(it)
                },
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(
                label = "Description",
                value = viewModel.wishDescriptionState,
                onValueChange = {
                    viewModel.onWishDescriptionChange(it)
                },
                onDone = {
                    handleSaveOrUpdate()
                },
                imeAction = ImeAction.Done
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { handleSaveOrUpdate() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary // ✅ instead of purple_500
                )
            ) {
                Text(
                    text = if (id != 0L) "Update Wish" else "Add Wish",
                    color = MaterialTheme.colorScheme.onPrimary // ✅ instead of Color.White
                )
            }

        }
        }
    }


@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    onNext: (() -> Unit)? = null,
    onDone: (() -> Unit)? = null,
    imeAction: ImeAction = ImeAction.Next
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = MaterialTheme.colorScheme.onSurface) }, // ✅ adapt text color
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = { onNext?.invoke() },
            onDone = {
                keyboardController?.hide()
                onDone?.invoke()
            }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary, // ✅ adapt border
            unfocusedBorderColor = MaterialTheme.colorScheme.outline, // ✅ theme outline
            cursorColor = MaterialTheme.colorScheme.primary, // ✅ adapt cursor
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface
        ),
        singleLine = true
    )
}

@Preview
@Composable
fun WishTextFieldPreview() {
    WishTextField(label = "Title", value = "", onValueChange = {})
}