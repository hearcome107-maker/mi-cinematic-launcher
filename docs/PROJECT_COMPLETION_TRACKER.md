# Mi Cinematic Launcher — master completion tracker

Status is based on committed source code, not promises or visual mockups. A successful APK build means compilation passed; it does not mean device testing passed.

## Implemented in source
- [x] Android TV Leanback launcher entry and Android 9+ minimum API target.
- [x] Basic dark cinematic home screen and clock/date display (12-hour clock).
- [x] Installed-app discovery and app launching.
- [x] Basic Home / Movies / Series / YouTube / Apps / Settings navigation.
- [x] Manual wallpaper picker, preview and removal.
- [x] Custom per-app image icons with reset.
- [x] Basic profile name selector (Siddesh / Family / Guest; profiles do not yet have separate layouts).
- [x] First-launch / new-day three-category Daily Picks screen (Movie Mood, Music Mood, Daily Discovery).
- [x] Basic daily choice persistence.
- [x] Custom silver orbit/star vector app icon registered in manifest.
- [x] Android TV home-screen banner registered in manifest.

## Partially implemented; needs completion
- [ ] Daily Picks use real image thumbnails and curated recommendations; current three cards are gradient placeholders.
- [ ] Selecting a Daily Pick applies a real chosen photo; current selection stores a category and gradient-style wallpaper only.
- [ ] Actual favourite titles/artists/genres editable in TV settings.
- [ ] Family profiles with separate preferences, favourites and wallpaper histories.
- [ ] Final polished logo artwork matching the user's supplied reference; current icon is a simple vector interpretation.
- [ ] TV banner visual review and actual device verification.
- [ ] Full remote-control focus order, Back/Home key behaviour and accessibility testing.
- [ ] Actual media-session music metadata and previous/play/pause/next controls.
- [ ] Clock glass/depth/glow/position settings.
- [ ] Quick-control panel with capability-aware Wi-Fi/Bluetooth actions.
- [ ] TV-friendly app folders, favourites, categories and search.
- [ ] Landscape/TV resolution and scaling review on the target Mi TV.

## Not implemented
- [ ] Automatic movie/TV artwork discovery (requires an artwork catalogue/API and configuration).
- [ ] Trending news/current stories feed (requires a source and content handling).
- [ ] Music listening-history recommendations beyond exposed Android media-session metadata.
- [ ] OpenAI image generation provider integration.
- [ ] Gemini image generation provider integration and fallback logic.
- [ ] Secure backend for AI provider credentials and billing.
- [ ] Automatic background refresh/rotation and scheduled preparation.
- [ ] Movie/series catalogue search and real streaming provider availability comparison.
- [ ] Universal search, recent search history and TV keyboard.
- [ ] Full profile-specific personalisation.
- [ ] Long-press Home to open Recent Apps (device/firmware-dependent; cannot promise without testing).
- [ ] Full-screen visual music player.
- [ ] Daily notification behaviour (TV firmware-dependent; in-launcher startup prompt is preferred).
- [ ] Phone companion app (explicitly postponed).
- [ ] Final installation and end-to-end testing on the user's Mi TV 4i.

## Build / test record
- Source branch: chatgpt-launcher-build.
- Latest code should be built from this branch's latest commit.
- Do not mark device testing complete until the user tests it on the actual TV and reports results.
