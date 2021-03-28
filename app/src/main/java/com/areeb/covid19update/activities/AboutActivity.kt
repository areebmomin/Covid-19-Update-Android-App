package com.areeb.covid19update.activities

import android.content.Context
import android.content.res.Resources.Theme
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.areeb.covid19update.R
import java.util.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.about)
        setContentView(R.layout.activity_about)

        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(
            getColoredArrow(
                this,
                theme
            )
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getColoredArrow(context: Context, theme: Theme?): Drawable? {
        val arrowDrawable =
            ResourcesCompat.getDrawable(context.resources, R.drawable.ic_back_button, theme)
        var wrapped: Drawable? = null

        // This should avoid tinting all the arrows
        if (arrowDrawable != null) {
            wrapped = DrawableCompat.wrap(arrowDrawable)
            arrowDrawable.mutate()
            DrawableCompat.setTint(wrapped, Color.WHITE)
        }
        return wrapped
    }
}