# WiFiControl

Open-source helper that lets [AutoHotspot](https://play.google.com/store/apps/details?id=com.wifi.auto.share) turn Wi-Fi on and off automatically. It has no internet permission.

## How it works

1. AutoHotspot runs your rule, for example "turn off Wi-Fi when the hotspot starts".
2. It calls WiFiControl's exported content provider on the device. No intents or broadcasts are used; the only intent filter is the launcher entry.
3. WiFiControl calls Android's `WifiManager.setWifiEnabled()`.

Android 10+ blocks that call for apps targeting Android 10 or later, so WiFiControl targets Android 9 (API 28). That is also why it is not on Google Play and why Android may show an "older version" warning when installing.

Keep it installed; its screen does not need to be open. The screen shows the current Wi-Fi state and has buttons for manual testing.

### Provider interface

Authority: `com.wifi.auto.hotspot.wifi.control.provider` (package `wifi.auto.hotspot.wifi.control.provider.content`)

- `update` on `content://com.wifi.auto.hotspot.wifi.control.provider` turns Wi-Fi on. It returns `1` if Android accepted the request and `0` if not.
- `delete` on the same URI turns Wi-Fi off, with the same return values.
- `query` on `content://com.wifi.auto.hotspot.wifi.control.provider/version` returns one row with `version_code` and `version_name`.

A returned `1` means Android accepted the request, not that Wi-Fi has finished switching; read the Wi-Fi state to confirm. On Android 11+ the calling app needs `<queries><package android:name="wifi.auto.hotspot.wifi.control.provider.content" /></queries>` in its manifest.

Test from a computer with `adb`:

```sh
adb shell content delete --uri content://com.wifi.auto.hotspot.wifi.control.provider
adb shell content update --uri content://com.wifi.auto.hotspot.wifi.control.provider --bind x:s:on
adb shell content query --uri content://com.wifi.auto.hotspot.wifi.control.provider/version
adb shell cmd wifi status
```

## Install

1. Download [WiFiControl.apk](https://github.com/SashaShcherbinin/autohotspot_wificontrol/releases/latest/download/WiFiControl.apk) on your phone and open it.
2. If Chrome asks, open **Settings** and enable **Allow from this source**.
3. If Play Protect warns about an older Android version, tap **More details**, then **Install anyway**.
4. Return to AutoHotspot and enable the Wi-Fi options.

## Releases

GitHub Actions builds the signed APK on every push to `main` and attaches `WiFiControl.apk` to the release when a version tag is pushed.
