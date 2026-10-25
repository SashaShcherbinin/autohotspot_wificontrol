# WiFiControl

An open-source helper that lets AutoHotspot enable and disable Wi-Fi. All source code is available in this repository. The helper requests Wi-Fi state permissions and does not request internet access.

## Automatic switching

AutoHotspot sends local requests to this helper when your configured Wi-Fi rules run. Keep WiFiControl installed; you do not need to keep its screen open. The buttons are for manual testing, and the screen displays the actual Wi-Fi state.

## Why it is not on Google Play

WiFiControl targets Android 9 (API 28) to retain Android's compatibility allowance for switching Wi-Fi. Apps targeting Android 10 or later cannot use this API as ordinary apps. Current Google Play target requirements prevent publishing this older-target helper as a normal app update.

## Why Android shows a warning

Android may warn that WiFiControl was built for an older version and does not include the latest privacy protections. This is caused by its older target SDK, which is needed for Wi-Fi control. You can review the complete source code before deciding whether to install it.

## How to install

1. Download [WiFiControl 1.1.3](https://github.com/SashaShcherbinin/autohotspot_wificontrol/releases/download/1.1.3/WiFiControl.1.1.3.apk).
2. Open the downloaded APK on your Android phone.
3. If Chrome shows an unknown-app installation message, select **Settings**, enable **Allow from this source**, and return to the installer.
4. If Play Protect shows the older-Android compatibility warning, open **More details** if available. **Install anyway** is a small text link above the large **Got it** button. Select it only if you choose to continue.
5. Finish installation and return to AutoHotspot to enable the Wi-Fi options.

Screens and available options vary by Android version and device manufacturer.
