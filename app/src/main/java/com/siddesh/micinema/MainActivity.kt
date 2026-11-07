package com.siddesh.micinema

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.ComponentName
import android.media.MediaMetadata
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.Drawable
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : Activity() {
    private val bg = Color.rgb(9, 12, 20)
    private val panel = Color.rgb(25, 32, 47)
    private val muted = Color.rgb(164, 176, 198)
    private val prefs by lazy { getSharedPreferences("cinema", MODE_PRIVATE) }
    private var profile = "Siddesh"
    private lateinit var root: LinearLayout
    private lateinit var nav: LinearLayout
    private lateinit var content: LinearLayout
    private var page = "Home"
    private var iconTargetPackage: String? = null
    private val tabs = listOf("Home", "YouTube", "Apps", "Settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profile = prefs.getString("profile", "Siddesh") ?: "Siddesh"
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        shell()
        show("Home")
    }

    private fun shape(c: Int) = GradientDrawable().apply { setColor(c); cornerRadius = 22f }
    private fun text(s: String, size: Float, color: Int = Color.WHITE, bold: Boolean = false) =
        TextView(this).apply { text=s; textSize=size; setTextColor(color); if (bold) typeface=Typeface.DEFAULT_BOLD }

    private fun action(s: String, run: () -> Unit) = TextView(this).apply {
        text=s; textSize=17f; gravity=Gravity.CENTER; setTextColor(Color.WHITE)
        setPadding(18,13,18,13); background=shape(panel); isFocusable=true; isClickable=true
        setOnFocusChangeListener { v, f -> v.background=shape(if(f) Color.rgb(65,82,115) else panel); v.scaleX=if(f)1.05f else 1f; v.scaleY=if(f)1.05f else 1f }
        setOnClickListener { run() }
    }

    private fun shell() {
        root=LinearLayout(this).apply { orientation=1; setPadding(42,22,42,24); background=wallpaperBackground() }
        val top=LinearLayout(this).apply { gravity=Gravity.CENTER_VERTICAL }
        top.addView(text("MI  CINEMATIC",19f,Color.WHITE,true),LinearLayout.LayoutParams(0,-2,1f))
        top.addView(action("◉  $profile") { profiles() })
        top.addView(action("⚙") { show("Settings") })
        root.addView(top)
        nav=LinearLayout(this).apply { orientation=0; setPadding(0,20,0,18) }; root.addView(nav)
        content=LinearLayout(this).apply { orientation=1 }; root.addView(content,LinearLayout.LayoutParams(-1,0,1f))
        setContentView(root); drawNav()
    }

    private fun drawNav() {
        nav.removeAllViews()
        tabs.forEach { t ->
            val b=action(t) { if(t=="YouTube") launch("com.google.android.youtube.tv",t) else show(t) }
            if(t==page) b.background=shape(Color.rgb(65,82,115))
            val lp=LinearLayout.LayoutParams(-2,-2); lp.marginEnd=9; nav.addView(b,lp)
        }
    }

    private fun show(p:String) {
        page=p; drawNav(); content.removeAllViews()
        when(p) {
            "Home" -> home()
            "Apps" -> apps()
            "Settings" -> settings()
        }
    }

    private fun heading(s:String, sub:String) {
        content.addView(text(s,34f,Color.WHITE,true)); content.addView(text(sub,16f,muted))
        content.addView(View(this),LinearLayout.LayoutParams(1,22))
    }

    private fun home() {
        content.addView(text(SimpleDateFormat("h:mm",Locale.getDefault()).format(Date()),72f))
        content.addView(text(SimpleDateFormat("EEEE, d MMMM",Locale.getDefault()).format(Date()).uppercase(Locale.getDefault()),16f,muted))
        content.addView(View(this),LinearLayout.LayoutParams(1,25))
        content.addView(text("YOUR CINEMA. YOUR HOME.",18f,Color.WHITE,true))
        content.addView(text("Welcome back, $profile",15f,muted))
        content.addView(View(this),LinearLayout.LayoutParams(1,25))
        content.addView(text("QUICK ACCESS",14f,muted,true))
        val row=LinearLayout(this)
        listOf(Triple("YouTube","com.google.android.youtube.tv","▶"),Triple("Netflix","com.netflix.ninja","N"),Triple("Prime Video","com.amazon.amazonvideo.livingroom","P"),Triple("Settings","com.android.tv.settings","⚙")).forEach { item ->
            val tile=appTile(item.first,item.second,item.third) { if(item.first=="Settings") systemSettings() else launch(item.second,item.first) }
            val lp=LinearLayout.LayoutParams(175,112); lp.marginEnd=14; row.addView(tile,lp)
        }
        content.addView(row)
        content.addView(View(this),LinearLayout.LayoutParams(1,24))
        val music=LinearLayout(this).apply { orientation=0; gravity=Gravity.CENTER_VERTICAL; setPadding(18,16,18,16); background=shape(panel); isFocusable=true; isClickable=true; setOnClickListener { musicPlayer() } }
        music.addView(text("♫   MUSIC",18f,Color.WHITE,true),LinearLayout.LayoutParams(0,-2,1f))
        music.addView(text("Open full-screen player  ›",14f,muted)); content.addView(music)
    }

    private fun tile(name:String, glyph:String, run:()->Unit)=LinearLayout(this).apply {
        orientation=1; gravity=Gravity.CENTER; background=shape(panel); isFocusable=true; isClickable=true
        addView(text(glyph,30f,Color.WHITE,true)); addView(text(name,16f,Color.WHITE,true))
        setOnFocusChangeListener { v,f -> v.background=shape(if(f)Color.rgb(65,82,115) else panel); v.scaleX=if(f)1.05f else 1f; v.scaleY=if(f)1.05f else 1f }
        setOnClickListener { run() }
    }

    private fun apps() {
        heading("All applications","Installed apps available to launch")
        val scroll=ScrollView(this); val list=LinearLayout(this).apply { orientation=1 }
        val found=packageManager.queryIntentActivities(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER),0)
            .ifEmpty { packageManager.queryIntentActivities(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER),0) }
            .distinctBy { it.activityInfo.packageName }.sortedBy { it.loadLabel(packageManager).toString() }
        found.chunked(4).forEach { group ->
            val row=LinearLayout(this)
            group.forEach { info ->
                val name=info.loadLabel(packageManager).toString()
                val v=appTile(name,info.activityInfo.packageName,"▣") { launch(info.activityInfo.packageName,name) }
                val lp=LinearLayout.LayoutParams(0,112,1f); lp.marginEnd=12; lp.bottomMargin=12; row.addView(v,lp)
            }; list.addView(row)
        }
        if(found.isEmpty()) list.addView(text("No launchable apps found.",18f,muted))
        scroll.addView(list); content.addView(scroll,LinearLayout.LayoutParams(-1,0,1f))
    }

    private fun appTile(name:String, pkg:String, fallback:String, run:()->Unit)=LinearLayout(this).apply {
        orientation=1; gravity=Gravity.CENTER; background=shape(panel); isFocusable=true; isClickable=true
        val iconView=ImageView(this@MainActivity).apply {
            val saved=prefs.getString("icon_$pkg",null)
            var loaded=false
            if(saved!=null) try { setImageURI(Uri.parse(saved)); loaded=drawable!=null } catch (_:Exception) {}
            if(!loaded) try { setImageDrawable(packageManager.getApplicationIcon(pkg)); loaded=true } catch (_:Exception) {}
            scaleType=ImageView.ScaleType.FIT_CENTER
            if(!loaded) contentDescription=fallback
        }
        addView(iconView,LinearLayout.LayoutParams(54,54))
        addView(text(name,15f,Color.WHITE,true))
        setOnFocusChangeListener { v,f -> v.background=shape(if(f)Color.rgb(65,82,115) else panel); v.scaleX=if(f)1.05f else 1f; v.scaleY=if(f)1.05f else 1f }
        setOnClickListener { run() }
    }


    private fun musicPlayer() {
        content.removeAllViews()
        heading("Now Playing", "Your music • live queue")
        val manager = getSystemService(MEDIA_SESSION_SERVICE) as MediaSessionManager
        val controller = try { manager.getActiveSessions(ComponentName(this, MusicAccessService::class.java)).firstOrNull() } catch (e: Exception) { null }
        if (controller == null) {
            content.addView(text("Allow Mi Cinematic Launcher to read music notifications to show the song, album art and queue.", 17f, muted))
            content.addView(action("Enable music access") { startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")) })
            content.addView(action("Back to Home") { show("Home") })
            return
        }

        val meta = controller.metadata
        val title = meta?.getString(MediaMetadata.METADATA_KEY_TITLE) ?: "Unknown track"
        val artist = meta?.getString(MediaMetadata.METADATA_KEY_ARTIST) ?: "Unknown artist"
        val album = meta?.getString(MediaMetadata.METADATA_KEY_ALBUM) ?: ""
        val currentArt = meta?.getBitmap(MediaMetadata.METADATA_KEY_ART)
            ?: meta?.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART)
        val queue = controller.queue ?: emptyList()
        val currentId = controller.playbackState?.activeQueueItemId ?: -1L
        val ordered = queue.toMutableList()
        val currentIndex = ordered.indexOfFirst { it.queueId == currentId }
        if (currentIndex < 0) ordered.add(0, android.media.session.MediaSession.QueueItem(
            android.media.MediaDescription.Builder().setTitle(title).setSubtitle(artist).setIconBitmap(currentArt).build(), -999L
        ))

        val carouselScroll = HorizontalScrollView(this).apply { isHorizontalScrollBarEnabled = false }
        val carousel = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL; setPadding(8, 8, 8, 16) }
        ordered.take(12).forEachIndexed { index, item ->
            val desc = item.description
            val isCurrent = item.queueId == currentId || (currentIndex < 0 && index == 0)
            val card = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(10, 10, 10, 12)
                background = GradientDrawable().apply {
                    setColor(if (isCurrent) Color.argb(220, 57, 48, 43) else Color.argb(150, 35, 39, 48))
                    cornerRadius = 22f
                    setStroke(if (isCurrent) 2 else 1, Color.argb(if (isCurrent) 210 else 90, 255, 255, 255))
                }
                isFocusable = true
                isClickable = true
                setOnClickListener {
                    if (item.queueId != -999L) controller.transportControls.skipToQueueItem(item.queueId)
                }
            }
            val art = if (isCurrent) currentArt else desc.iconBitmap
            val cover = ImageView(this).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                if (art != null) setImageBitmap(art)
                else setImageDrawable(GradientDrawable().apply { setColor(Color.rgb(65, 53, 47)); cornerRadius = 18f })
            }
            card.addView(cover, LinearLayout.LayoutParams(if (isCurrent) 218 else 176, if (isCurrent) 190 else 154))
            card.addView(text(desc.title?.toString() ?: "Track", if (isCurrent) 19f else 16f, Color.WHITE, true))
            card.addView(text(desc.subtitle?.toString() ?: if (isCurrent) artist else "", 13f, muted))
            val lp = LinearLayout.LayoutParams(if (isCurrent) 242 else 198, -2)
            lp.marginEnd = 12
            carousel.addView(card, lp)
        }
        if (ordered.isEmpty()) {
            val card = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER; setPadding(12,12,12,12); background = shape(panel) }
            val cover = ImageView(this).apply { scaleType = ImageView.ScaleType.CENTER_CROP; if (currentArt != null) setImageBitmap(currentArt) }
            card.addView(cover, LinearLayout.LayoutParams(250, 185))
            card.addView(text(title, 20f, Color.WHITE, true))
            card.addView(text(artist, 15f, muted))
            carousel.addView(card)
        }
        carouselScroll.addView(carousel)
        content.addView(carouselScroll, LinearLayout.LayoutParams(-1, 0, 1.0f))

        val controlBar = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(12, 12, 12, 12)
            background = GradientDrawable().apply {
                setColor(Color.argb(185, 37, 32, 34))
                cornerRadius = 34f
                setStroke(1, Color.argb(105, 255, 255, 255))
            }
        }
        fun control(label: String, weight: Float = 0.8f, callback: () -> Unit) {
            val button = action(label, callback)
            val lp = LinearLayout.LayoutParams(0, 58, weight)
            lp.marginEnd = 6
            controlBar.addView(button, lp)
        }
        control("◀◀") { controller.transportControls.skipToPrevious(); content.postDelayed({ musicPlayer() }, 350) }
        control(if (controller.playbackState?.state == PlaybackState.STATE_PLAYING) "Ⅱ" else "▶") {
            if (controller.playbackState?.state == PlaybackState.STATE_PLAYING) controller.transportControls.pause()
            else controller.transportControls.play()
            content.postDelayed({ musicPlayer() }, 250)
        }
        control("▶▶") { controller.transportControls.skipToNext(); content.postDelayed({ musicPlayer() }, 350) }
        val now = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL; setPadding(8, 6, 8, 6); background = shape(Color.argb(175, 15, 17, 23)) }
        val thumb = ImageView(this).apply { scaleType = ImageView.ScaleType.CENTER_CROP; if (currentArt != null) setImageBitmap(currentArt) }
        now.addView(thumb, LinearLayout.LayoutParams(52, 52))
        val labels = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(8, 0, 4, 0) }
        labels.addView(text(title, 14f, Color.WHITE, true))
        labels.addView(text(artist, 12f, muted))
        now.addView(labels, LinearLayout.LayoutParams(0, -2, 1f))
        val nowLp = LinearLayout.LayoutParams(0, -2, 2.2f)
        nowLp.marginEnd = 6
        controlBar.addView(now, nowLp)
        control("☷") { showMusicQueue(controller) }
        control("⌂") { show("Home") }
        content.addView(controlBar, LinearLayout.LayoutParams(-1, -2))
        if (album.isNotBlank()) content.addView(text(album, 12f, muted))
    }

    private fun showMusicQueue(controller: android.media.session.MediaController) {
        content.removeAllViews()
        heading("Playing queue", "Songs shared by your current music app")
        val queue = controller.queue ?: emptyList()
        if (queue.isEmpty()) content.addView(text("This music app does not share its queue.", 16f, muted))
        queue.forEach { item ->
            val row = action(item.description.title?.toString() ?: "Track") {
                controller.transportControls.skipToQueueItem(item.queueId)
                content.postDelayed({ musicPlayer() }, 300)
            }
            val lp = LinearLayout.LayoutParams(-1, -2)
            lp.bottomMargin = 8
            content.addView(row, lp)
        }
        content.addView(action("Back to player") { musicPlayer() })
    }

    private fun customIconSettings() {
        content.removeAllViews()
        heading("Custom app icons","Choose an image saved on your TV for any installed app")
        content.addView(text("Select an app, then choose an image from Downloads, USB storage or your TV file manager.",15f,muted))
        val scroll=ScrollView(this)
        val list=LinearLayout(this).apply { orientation=1 }
        val found=packageManager.queryIntentActivities(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER),0)
            .ifEmpty { packageManager.queryIntentActivities(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER),0) }
            .distinctBy { it.activityInfo.packageName }.sortedBy { it.loadLabel(packageManager).toString() }
        found.forEach { info ->
            val pkg=info.activityInfo.packageName
            val name=info.loadLabel(packageManager).toString()
            val row=LinearLayout(this).apply { gravity=Gravity.CENTER_VERTICAL; setPadding(8,7,8,7) }
            row.addView(text(name,17f,Color.WHITE,true),LinearLayout.LayoutParams(0,-2,1f))
            row.addView(action("Choose image") { chooseAppIcon(pkg) })
            if(prefs.contains("icon_$pkg")) row.addView(action("Reset") { prefs.edit().remove("icon_$pkg").apply(); customIconSettings() })
            list.addView(row)
        }
        scroll.addView(list)
        content.addView(scroll,LinearLayout.LayoutParams(-1,0,1f))
        content.addView(action("Back to Settings") { show("Settings") })
    }

    private fun chooseAppIcon(pkg:String) {
        iconTargetPackage=pkg
        val intent=Intent(Intent.ACTION_OPEN_DOCUMENT).apply { addCategory(Intent.CATEGORY_OPENABLE); type="image/*"; addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION) }
        try { startActivityForResult(intent,411) } catch (_:Exception) { Toast.makeText(this,"No image picker is available on this TV.",Toast.LENGTH_LONG).show() }
    }
    private fun media(type:String) {
        heading(type,"Your $type library")
        content.addView(action("⌕  Search $type") {
            val input=EditText(this).apply { hint="Enter title" }
            AlertDialog.Builder(this).setTitle("Search $type").setView(input)
                .setPositiveButton("Search") { _,_ -> Toast.makeText(this,"No streaming catalogue connected yet.",Toast.LENGTH_LONG).show() }
                .setNegativeButton("Cancel",null).show()
        })
        content.addView(text("Provider availability needs a permitted catalogue source. No titles, prices or streaming availability are fabricated.",15f,muted))
    }

    private fun settings() {
        heading("Settings","Personalise your cinema")
        listOf("Profiles" to { profiles() },"Customize app icons" to { customIconSettings() },"Wi-Fi settings" to { open(Settings.ACTION_WIFI_SETTINGS) },"Bluetooth settings" to { open(Settings.ACTION_BLUETOOTH_SETTINGS) },"System settings" to { systemSettings() },"Choose wallpaper" to { chooseWallpaper() },"Preview current wallpaper" to { previewWallpaper() },"Remove custom wallpaper" to { prefs.edit().remove("wallpaper_uri").apply(); shell(); show("Settings") },"Reset preferences" to { prefs.edit().clear().apply(); profile="Siddesh"; shell(); show("Home") }).forEach { item ->
            val b=action(item.first,item.second); val lp=LinearLayout.LayoutParams(-1,-2); lp.bottomMargin=9; content.addView(b,lp)
        }
    }


    private fun wallpaperBackground(): Drawable {
        val saved = prefs.getString("wallpaper_uri", null)
        if (saved != null) {
            try {
                val bitmap = contentResolver.openInputStream(Uri.parse(saved))?.use { BitmapFactory.decodeStream(it) }
                if (bitmap != null) {
                    val image = BitmapDrawable(resources, bitmap).apply { gravity = Gravity.FILL }
                    return LayerDrawable(arrayOf(image, ColorDrawable(Color.argb(145, 5, 8, 14))))
                }
            } catch (_: Exception) { prefs.edit().remove("wallpaper_uri").apply() }
        }
        return GradientDrawable(GradientDrawable.Orientation.TL_BR, intArrayOf(bg,Color.rgb(20,27,43),Color.rgb(8,11,18)))
    }

    private fun chooseWallpaper() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }
        try { startActivityForResult(intent, 410) }
        catch (_: Exception) { Toast.makeText(this, "Image picker is not available on this TV.", Toast.LENGTH_LONG).show() }
    }

    private fun previewWallpaper() {
        val saved = prefs.getString("wallpaper_uri", null)
        if (saved == null) {
            Toast.makeText(this, "Choose a wallpaper first.", Toast.LENGTH_SHORT).show()
            return
        }
        val image = ImageView(this).apply {
            setImageURI(Uri.parse(saved))
            scaleType = ImageView.ScaleType.CENTER_CROP
            adjustViewBounds = true
            minimumHeight = 260
        }
        AlertDialog.Builder(this).setTitle("Current wallpaper").setView(image)
            .setPositiveButton("Done", null).show()
    }

    @Deprecated("Deprecated by Android, retained for Android 9 TV compatibility")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 410 && resultCode == RESULT_OK) {
            val uri = data?.data ?: return
            try {
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                prefs.edit().putString("wallpaper_uri", uri.toString()).apply()
                shell()
                show("Home")
                Toast.makeText(this, "Wallpaper applied.", Toast.LENGTH_SHORT).show()
            } catch (_: Exception) {
                Toast.makeText(this, "Could not save this image. Try another image.", Toast.LENGTH_LONG).show()
            }
        }
        if (requestCode == 411 && resultCode == RESULT_OK) {
            val uri=data?.data ?: return
            val pkg=iconTargetPackage ?: return
            try {
                try { contentResolver.takePersistableUriPermission(uri,Intent.FLAG_GRANT_READ_URI_PERMISSION) } catch (_:Exception) {}
                prefs.edit().putString("icon_$pkg",uri.toString()).apply()
                iconTargetPackage=null
                customIconSettings()
                Toast.makeText(this,"Custom app icon applied.",Toast.LENGTH_SHORT).show()
            } catch (_:Exception) { Toast.makeText(this,"Could not apply that image. Try another file.",Toast.LENGTH_LONG).show() }
        }
    }

    private fun profiles() {
        val names=arrayOf("Siddesh","Family","Guest")
        AlertDialog.Builder(this).setTitle("Choose profile").setItems(names) { _,i ->
            profile=names[i]; prefs.edit().putString("profile",profile).apply(); shell(); show("Home")
        }.show()
    }

    private fun launch(pkg:String,name:String) {
        val i=packageManager.getLaunchIntentForPackage(pkg)
        if(i!=null) startActivity(i) else Toast.makeText(this,"$name is not installed",Toast.LENGTH_SHORT).show()
    }
    private fun systemSettings()=open(Settings.ACTION_SETTINGS)
    private fun open(action:String) { try { startActivity(Intent(action)) } catch (_:Exception) { systemSettings() } }
    override fun onKeyDown(keyCode:Int,event:KeyEvent?):Boolean {
        if(keyCode==KeyEvent.KEYCODE_MENU) { show("Settings"); return true }
        return super.onKeyDown(keyCode,event)
    }
}