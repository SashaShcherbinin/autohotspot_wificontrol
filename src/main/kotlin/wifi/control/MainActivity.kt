package wifi.control

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var wifiStatus: TextView
    private lateinit var enableButton: Button
    private lateinit var disableButton: Button
    private val wifiManager: WifiManager
        get() = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    private val wifiStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateWifiStatus()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wifiStatus = findViewById(R.id.main_wifi_status)
        enableButton = findViewById(R.id.main_button_enable_wifi)
        disableButton = findViewById(R.id.main_button_disable_wifi)
        enableButton.setOnClickListener { requestWifi(true) }
        disableButton.setOnClickListener { requestWifi(false) }
        findViewById<Button>(R.id.main_button_source).setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(SOURCE_URL)))
            } catch (_: ActivityNotFoundException) {
                Toast.makeText(this, R.string.main_error_open_source, Toast.LENGTH_LONG).show()
            }
        }
        val versionUri = CONTENT_URI.buildUpon().appendPath(PATH_VERSION).build()
        val version = contentResolver.query(versionUri, null, null, null, null)?.use {
            it.moveToFirst()
            getString(
                R.string.main_version,
                it.getString(it.getColumnIndexOrThrow(COLUM_VERSION_NAME)),
                it.getInt(it.getColumnIndexOrThrow(COLUM_VERSION_CODE)),
            )
        }
        findViewById<TextView>(R.id.main_label_app_version).text = version
        val content = findViewById<View>(R.id.main_content)
        content.post {
            val maxWidth = (MAX_CONTENT_WIDTH_DP * resources.displayMetrics.density).toInt()
            content.layoutParams = (content.layoutParams as android.widget.FrameLayout.LayoutParams).apply {
                width = minOf((content.parent as View).width, maxWidth)
                gravity = android.view.Gravity.CENTER_HORIZONTAL
            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(wifiStateReceiver, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))
        updateWifiStatus()
    }

    override fun onStop() {
        unregisterReceiver(wifiStateReceiver)
        super.onStop()
    }

    private fun requestWifi(enabled: Boolean) {
        val accepted = if (enabled) {
            contentResolver.update(CONTENT_URI, null, null, null)
        } else {
            contentResolver.delete(CONTENT_URI, null, null)
        }
        if (accepted <= 0) {
            Toast.makeText(this, R.string.main_error_change_rejected, Toast.LENGTH_LONG).show()
        }
        updateWifiStatus()
    }

    private fun updateWifiStatus() {
        val state = wifiManager.wifiState
        val changing = state == WifiManager.WIFI_STATE_ENABLING || state == WifiManager.WIFI_STATE_DISABLING
        wifiStatus.setText(
            when {
                changing -> R.string.main_status_changing
                wifiManager.isWifiEnabled -> R.string.main_status_enabled
                else -> R.string.main_status_disabled
            }
        )
        enableButton.isEnabled = !changing && !wifiManager.isWifiEnabled
        disableButton.isEnabled = !changing && wifiManager.isWifiEnabled
    }

    private companion object {
        const val SOURCE_URL = "https://github.com/SashaShcherbinin/autohotspot_wificontrol"
        const val MAX_CONTENT_WIDTH_DP = 600
    }
}
