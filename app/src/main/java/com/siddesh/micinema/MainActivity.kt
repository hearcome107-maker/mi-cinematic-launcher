package com.siddesh.micinema

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : Activity() {

    private val apps = listOf(
        "YouTube" to "com.google.android.youtube.tv",
        "Netflix" to "com.netflix.ninja",
        "Prime Video" to "com.amazon.amazonvideo.livingroom",
        "Settings" to "com.android.tv.settings"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(65, 35, 65, 35)
            setBackgroundColor(Color.rgb(12, 14, 21))
        }

        val clock = TextView(this).apply {
            text = SimpleDateFormat("h:mm", Locale.getDefault()).format(Date())
            textSize =  sixty
            setTextColor(Color.WHITE)
            typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
        }

        val date = TextView(this).apply {
            text = SimpleDateFormat(
                "EEEE, d MMMM",
                Locale.getDefault()
            ).format(Date()).uppercase(Locale.getDefault())
            textSize = 14f
            setTextColor(Color.LTGRAY)
        }

        val title = TextView(this).apply {
            text = "YOUR CINEMA. YOUR HOME."
            textSize = 15f
            letterSpacing = 0.18f
            setTextColor(Color.rgb(175, 185, 205))
            setPadding(0, 48, 0, 18)
        }

        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        apps.forEachIndexed { index, item ->
            val tile = TextView(this).apply {
                text = item.first
                textSize = 18f
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)
                setBackgroundColor(
                    if (index == 0) Color.rgb(65, 77, 103)
                    else Color.rgb(31, 36, 48)
                )
                setPadding(25, 22, 25, 22)
                isFocusable = true
                isFocusableInTouchMode = true

                setOnFocusChangeListener { view, focused ->
                    view.scaleX = if (focused) 1.06f else 1f
                    view.scaleY = if (focused) 1.06f else 1f
                    view.alpha = if (focused) 1f else 0.78f
                }

                setOnClickListener {
                    val launchIntent =
                        packageManager.getLaunchIntentForPackage(item.second)

                    if (launchIntent != null) {
                        startActivity(launchIntent)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "${item.first} is not available",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.marginEnd = 16
            row.addView(tile, params)
        }

        root.addView(clock)
        root.addView(date)
        root.addView(title)
        root.addView(row)

        setContentView(root)
        row.getChildAt(0)?.requestFocus()
    }
}
