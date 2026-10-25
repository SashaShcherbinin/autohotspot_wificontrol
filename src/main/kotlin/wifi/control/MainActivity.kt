package wifi.control

import android.app.Activity
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val enableWifiButton = findViewById<Button>(R.id.main_button_enable_wifi)
        val disableWifiButton = findViewById<Button>(R.id.main_button_disable_wifi)
        val appVersion = findViewById<TextView>(R.id.main_label_app_version)

        enableWifiButton.setOnClickListener {
            contentResolver.update(CONTENT_URI, null, null, null)
        }

        disableWifiButton.setOnClickListener {
            contentResolver.delete(CONTENT_URI, null, null)
        }

        val build = CONTENT_URI.buildUpon().appendPath(PATH_VERSION).build()
        val versionCursor: Cursor? = contentResolver.query(
            build,
            null,
            null,
            null,
            null
        )
        appVersion.text = versionCursor?.let {
            it.moveToFirst()
            val versionCode = it.getInt(it.getColumnIndex(COLUM_VERSION_CODE))
            val versionName = it.getString(it.getColumnIndex(COLUM_VERSION_NAME))
            "Version: $versionName ($versionCode)"
        } ?: "Version: unknown"
        versionCursor?.close()
    }
}
