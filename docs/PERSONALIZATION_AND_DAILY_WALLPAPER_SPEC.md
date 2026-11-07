# Personalisation profile — Mi Cinematic Launcher

This document records the owner's requested visual identity, daily wallpaper preferences, and inspiration library. It is a product brief, not a claim that every feature is implemented in the APK.

## Launcher identity
- Product name: Mi Cinematic Launcher.
- Logo direction: use the uploaded black-and-silver reference as visual inspiration — a minimal, polished metallic orbit/star emblem on a deep black background. Do not copy another company's exact trademark or wordmark. Keep the mark legible at small TV sizes.
- Overall interface: premium, dark, cinematic, uncluttered, remote-friendly.

## Daily Wallpaper Picks
- On the first launcher opening after the TV starts in the morning, show three curated wallpaper cards.
- The user previews and selects one; that selection becomes the wallpaper for that day.
- Offer a manual refresh and a setting to disable daily prompts.
- Prepare/cache choices when possible; never block the home screen waiting for network or AI.
- Three editorial categories:
  1. Movie & series mood — based on the user's favourite titles and genres.
  2. Music mood — based on recent playback only when an app/system media session makes it available.
  3. Today's discovery — optional current cultural/news/trending inspiration, with source/date labels where relevant.
- Manual-first: select and crop artwork; optional AI-generated original atmosphere-inspired wallpaper when a provider is configured. AI is not required for everyday use.
- Fit TV landscape (16:9), keep important visual subject away from clock/date/navigation/app-icon zones, apply subtle dark overlay for readability.
- Keep a local cache and a fallback wallpaper for offline use.
- Startup behavior: prefer showing the in-launcher daily selection screen on startup. A notification is optional and depends on the TV firmware; do not promise it will appear on every television.

## Favourite movie / visual inspiration library
- Marvel / superhero: Avengers (including Avengers: Endgame), Spider-Man franchise, Thor, Thor: Love and Thunder.
- Indian epic / mythology: Ramayan / Ramayana and new adaptations.
- Kannada action / star films: KGF, Yash films.
- Romance: love stories, romantic films, Korean-style romantic dramas.
- Cars / automotive: BMW, Lamborghini, other cinematic car photography.
- Racing/action: Fast & Furious franchise.
- Games / game-inspired atmosphere: GTA (use original city/action-inspired imagery; do not reproduce protected game artwork unless sourced/licensed).
- Additional new releases can be added by the user later.

## Music taste / wallpaper inspiration
- English: The Weeknd, especially “Starboy”.
- Hindi: Arijit Singh, especially “Fitoor”.
- Strong preference: romantic / love songs.
- Also likes older/classic songs sometimes.
- “All I Want” — artist/title needs confirmation before using as a precise track reference.
- “Can We Be Friends Again?” — phrase/title needs confirmation; retain as a user-mentioned music reference, not a verified track.
- Music history is optional: use only playback metadata actually exposed by Android media sessions; do not claim access to full listening history.

## AI provider preference
- Preferred quality route: OpenAI image generation when separately configured and available.
- Fallback: Gemini when configured and available.
- Manual artwork selection/composition must always remain available.
- Do not assume a ChatGPT subscription includes OpenAI API access or credits. Keep provider keys out of the APK; use a secure backend and explicit configuration.
- If neither provider is configured, the wallpaper studio should still work with manually selected artwork and local cinematic composition.

## Implementation notes / honest status
- Existing branch already has a manual wallpaper picker and custom per-app icon picker.
- Existing branch does not yet implement Daily Wallpaper Picks, movie/music/news recommendation feeds, automatic artwork discovery/rotation, or AI provider integration.
- Do not label a feature complete until implemented and the resulting APK build is verified.
