# Mi Cinematic Launcher — starter project

An early Android TV launcher scaffold for the Mi TV 4 I (Android 9 / API 28).

## Build in GitHub Actions (phone-friendly)
1. Upload the contents of this folder into the root of your GitHub repository, keeping the `.github/workflows/build-apk.yml` path intact.
2. Open the repository's **Actions** tab. Enable Actions if GitHub asks.
3. The `Build Mi Cinematic Launcher APK` workflow runs after a push to `main`; you can also open it and choose **Run workflow**.
4. When the run finishes successfully, open the run and download the `mi-cinematic-launcher-debug` artifact. It contains the debug APK.

## Current state
This is a starter scaffold, not the finished cinematic launcher. It has a basic TV entry point and placeholder home screen. The clock text and app tiles are placeholders; installed-app discovery/launching, profiles, wallpaper controls, music playback, quick controls, universal search and voice features are not implemented yet.

## Compatibility notes
- Minimum SDK: 28 (Android 9).
- The app declares a Leanback launcher entry point.
- Building successfully does not guarantee full compatibility with every Mi TV firmware. Test the APK on the TV before setting it as the default launcher.
- This app does not replace Android system settings or modify the TV operating system.
- Do not commit private family photos or personal files to this public repository. Wallpaper selection should be added as a local TV feature.
