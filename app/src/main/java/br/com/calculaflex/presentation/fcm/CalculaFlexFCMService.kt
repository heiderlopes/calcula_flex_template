package br.com.calculaflex.presentation.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import br.com.calculaflex.R
import br.com.calculaflex.presentation.main.MainActivity
import br.com.calculaflex.presentation.utils.notification.NotificationUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CalculaFlexFCMService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java)

        val title = p0.data["title"] ?: p0.notification?.title ?: this.getString(R.string.app_name)
        val message = p0.data["message"] ?: p0.notification?.body ?: ""

        val channelID = this.getString(R.string.default_notification_channel_id)
        val channelName = this.getString(R.string.default_notification_channel_name)
        val channelDescription = this.getString(R.string.default_notification_channel_description)

        val notifChannel =
            NotificationUtils.NotifChannel(channelID, channelName, channelDescription)

        NotificationUtils.notificationSimple(this, title, message, notifChannel)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.i("TOKEN_FIREBASE", p0)
    }

}
