package com.masjoel.myrecyclerviewkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.masjoel.myrecyclerviewkotlin.model.Hero

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_PHOTO = "extra_photo"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tvDataTerima: TextView = findViewById(R.id.tv_data_received)
        val imgItemPhoto: ImageView =  findViewById(R.id.img_item_photo)

        val dtHero = intent.getParcelableExtra<Hero>(EXTRA_USER) as Hero
        val name = dtHero.name
        val detail = dtHero.detail
        val photo = dtHero.photo
        val teks = "Nama : $name\nDetail: $detail"
        /*val name = intent.getStringExtra(EXTRA_NAME)
        val detail = intent.getStringExtra(EXTRA_DETAIL)
        val photo = intent.getIntExtra(EXTRA_PHOTO,0)
        val teks = "Nama : $name\nDetail: $detail"*/

        Glide.with(this)
            .load(photo)
            .apply(RequestOptions().override(250, 250))
            .into(imgItemPhoto)
        tvDataTerima.text = teks
    }
}