package com.dicoding.asclepius.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.asclepius.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        binding.resultText.text = intent.getStringExtra(EXTRA_RESULT)
        binding.resultImage.setImageURI(intent.getParcelableExtra(EXTRA_IMAGE_URI))
    }

    companion object {
        const val EXTRA_IMAGE_URI = "uri"
        const val EXTRA_RESULT = "result"
    }

}