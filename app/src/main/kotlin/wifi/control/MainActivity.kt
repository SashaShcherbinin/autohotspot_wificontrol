package wifi.control

import android.app.Activity
import android.os.Bundle
import android.widget.Button

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val enableWifiButton = findViewById<Button>(R.id.main_button_enable_wifi)
        val disableWifiButton = findViewById<Button>(R.id.main_button_disable_wifi)

        enableWifiButton.setOnClickListener {
            contentResolver.update(CONTENT_URI, null, null, null)
        }

        disableWifiButton.setOnClickListener {
            contentResolver.delete(CONTENT_URI, null, null)
        }
    }
}
