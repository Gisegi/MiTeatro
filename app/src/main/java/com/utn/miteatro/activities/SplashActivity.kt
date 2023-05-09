package com.utn.miteatro.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.utn.miteatro.R
import com.utn.miteatro.entities.User

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 3000 // 3 sec
    lateinit var imgSplash : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        imgSplash = findViewById(R.id.imgSplash)

        Handler().postDelayed(

            {
               startActivity(Intent(this, LoginActivity::class.java))


               /**  // BORRAR
                startActivity(Intent(this, MainActivity::class.java))
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("UserData", User(0,"gisela","farace","gisela@email.com", "1234", "avatar_dracula"))
                startActivity(intent)
                 // BORRAR
                */
                finish()
            }
            , SPLASH_TIME_OUT)
    }

    override fun onResume() {
        super.onResume()

        Glide.with(this)
            .asGif()
            .load(R.drawable.iniciosplash)
            .centerCrop()
            .into(imgSplash);

    }
}