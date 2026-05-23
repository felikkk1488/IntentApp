package com.example.intentapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactBookScreen()
        }
    }
}

// Звонок
fun callPhone(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Нет приложения для звонков", Toast.LENGTH_SHORT).show()
    }
}

// Email
fun sendEmail(context: Context, email: String, subject: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Нет почтового приложения", Toast.LENGTH_SHORT).show()
    }
}

// Карта
fun showOnMap(context: Context, latitude: Double, longitude: Double, label: String) {
    val geoUri = Uri.parse("geo:0,0?q=$latitude,$longitude($label)")
    val intent = Intent(Intent.ACTION_VIEW, geoUri)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Нет приложения для карт", Toast.LENGTH_SHORT).show()
    }
}

// Поделиться
fun shareContact(context: Context, text: String) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    val chooser = Intent.createChooser(sendIntent, "Поделиться через...")
    if (sendIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(chooser)
    } else {
        Toast.makeText(context, "Нет приложения для отправки", Toast.LENGTH_SHORT).show()
    }
}

// Экран
@Composable
fun ContactBookScreen() {
    val context = LocalContext.current

    val phoneNumber = stringResource(R.string.contact_phone)
    val emailAddress = stringResource(R.string.contact_email)
    val emailSubject = stringResource(R.string.email_subject)
    val officeLat = stringResource(R.string.office_lat).toDouble()
    val officeLon = stringResource(R.string.office_lon).toDouble()
    val officeLabel = stringResource(R.string.office_label)
    val shareText = stringResource(R.string.share_text, phoneNumber, emailAddress)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Заголовок
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Кнопка 1: Позвонить
        Button(
            onClick = { callPhone(context, phoneNumber) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_call))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка 2: Email
        Button(
            onClick = { sendEmail(context, emailAddress, emailSubject) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_email))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка 3: Карта
        Button(
            onClick = { showOnMap(context, officeLat, officeLon, officeLabel) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_map))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка 4: Поделиться
        Button(
            onClick = { shareContact(context, shareText) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_share))
        }
    }
}