package com.siddesh.micinema

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.Color
import android.graphics.Typeface

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(72, 36, 72, 36)
            setBackgroundColor(Color.rgb(12, 14, 20))
        }

        val time = TextView(this).apply {
            text = "10:08"
            textSize = 48f
            setTextColor(Color.WHITE)
            typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
        }
        val subtitle = TextView(this).apply {
            text = "WEDNESDAY  •  YOUR HOME"
            textSize = 13f
            letterSpacing = 0.18f
            setTextColor(Color.LTGRAY)
        }
        val heading = TextView(this).apply {
            text = "Continue your story"
            textSize = 25f
            setTextColor(Color.WHITE)
            setPadding(0, 48, 0, 18)
        }
        val apps = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        listOf("YouTube", "Netflix", "Prime Video", "Settings").forEachIndexed { index, label ->
            val tile = TextView(this).apply {
                text = label
                textSize = 17f
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)
                setBackgroundColor(if (index == 0) Color.rgb(66, 75, 95) else Color.rgb(32, 36, 46))
                setPadding(28, 22, 28, 22)
                isFocusable = true
                isFocusableInTouchMode = true
                setOnFocusChangeListener { view, focused ->
                    view.scaleX = if (focused) 1.06f else 1f
                    view.scaleY = if (focused) 1.06f else 1f
                    view.alpha = if (focused) 1f else 0.82f
                }
            }
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.marginEnd = 18
            apps.addView(tile, params)
        }

        root.addView(time)
        root.addView(subtitle)
        root.addView(heading)
        root.addView(apps)
        setContentView(root)
        apps.getChildAt(0)?.requestFocus()
    }
}
