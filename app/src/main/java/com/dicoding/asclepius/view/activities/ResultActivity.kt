package com.dicoding.asclepius.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dicoding.asclepius.data.local.entity.CancerEntity
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.ImageConverter
import com.dicoding.asclepius.view.viewmodel.MainViewModel
import com.dicoding.asclepius.view.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(application)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the data
        val label = intent.getStringExtra(EXTRA_LABEL)
        val confidence = intent.getFloatExtra(EXTRA_CONFIDENCE, 0.0f)
        val confidencePercent = NumberFormat.getPercentInstance().format(confidence).trim()
        val imageUri = intent.getParcelableExtra<Uri>(EXTRA_IMAGE_URI)

        // Set the data
        binding.resultText.text = "$label: $confidencePercent"
        binding.resultImage.setImageURI(imageUri)

        binding.closeButton.setOnClickListener {
            startActivity(Intent(this@ResultActivity, MainActivity::class.java))
            finish()
        }

        binding.saveButton.setOnClickListener {
            val cancer = CancerEntity(
                label = label,
                confidence = confidence,
                image = ImageConverter.uriToByteArray(contentResolver, imageUri)
            )
            lifecycleScope.launch {
                viewModel.setCancer(cancer)
                startActivity(Intent(this@ResultActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_LABEL = "label"
        const val EXTRA_CONFIDENCE = "confidence"
        const val EXTRA_IMAGE_URI = "uri"
    }

}