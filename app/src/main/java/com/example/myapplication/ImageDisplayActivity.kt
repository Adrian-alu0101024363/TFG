package com.example.myapplication


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import com.example.myapplication.databinding.ActivityArScreenBinding
import com.example.myapplication.databinding.ActivityImageDisplayBinding
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException


@ExperimentalGetImage class ImageDisplayActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityImageDisplayBinding
    private lateinit var editText1: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityImageDisplayBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        //setContentView(R.layout.activity_image_display)

        val imageUriString = intent.getStringExtra("imageUri")
        val imageUri = Uri.parse(imageUriString)
        editText1 = findViewById(R.id.edittext1)
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageURI(imageUri)
        extractTextFormUri(applicationContext,imageUri)
        viewBinding.saveCard.setOnClickListener { onSaveCard() }
    }
    private fun extractTextFormUri(context: Context, uri: Uri) {
        val recognizer = TextRecognition.getClient(JapaneseTextRecognizerOptions.Builder().build())
        try {
            val image = InputImage.fromFilePath(context, uri)
            //Log.d("entro", "entro")
            val result: Task<Text> = recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    // Task completed successfully
                    editText1.setText(visionText.text)
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    override fun onBackPressed() {
        val intent = Intent(this, ar_screen::class.java)
        startActivity(intent)
        startActivity(intent)
        finish()
    }
    private fun onSaveCard() {
        Log.d("texto", editText1.text.toString())
        Toast.makeText(baseContext, editText1.text.toString(), Toast.LENGTH_SHORT).show()
    }
}