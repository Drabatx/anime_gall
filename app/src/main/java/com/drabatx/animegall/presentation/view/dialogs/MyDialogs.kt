package com.drabatx.animegall.presentation.view.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.drabatx.animegall.R


@Composable
fun MessageDialog(
    title: String,
    text: String,
    icon: ImageVector,
    showDialog: Boolean
) {
    var showDialogR by remember { mutableStateOf(showDialog) }
    if (showDialogR){
        AlertDialog(
            icon = {
                Icon(icon, contentDescription = stringResource(R.string.example_icon))
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = text)
            },
            onDismissRequest = {
                showDialogR = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialogR = false
                    }
                ) {
                    Text(stringResource(R.string.text_accept))
                }
            },
            dismissButton = {

            },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        )
    }
}

@Composable
fun LoadingDialog(isLoading: Boolean) {
    var showDialog by remember { mutableStateOf(isLoading) }
    Dialog(
        onDismissRequest = { showDialog = false },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment= Center,
            modifier = Modifier
                .size(100.dp)
                .background(White, shape = RoundedCornerShape(8.dp))
        ) {
            CircularProgressIndicator()
        }
    }
}

