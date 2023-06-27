package com.example.myapplication


import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import com.example.myapplication.databinding.ActivityImageDisplayBinding
import com.example.myapplication.db.DbHelper
import com.example.myapplication.db.DbHelper.TABLE_WORDS
import com.example.myapplication.db.DbUsers
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import java.io.IOException


@ExperimentalGetImage class ImageDisplayActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityImageDisplayBinding
    private lateinit var editText1: EditText
    private lateinit var translation: String
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
        finish()
    }
    private fun onSaveCard() {
        //Log.d("texto", editText1.text.toString())
        //Toast.makeText(baseContext, editText1.text.toString(), Toast.LENGTH_SHORT).show()
        val dbHelper = DbHelper(this@ImageDisplayActivity)
        val db = dbHelper.writableDatabase
        if (db != null) {
            val dbUsers = DbUsers(this@ImageDisplayActivity)
            val currentUser = dbUsers.lastUser
            if (currentUser.name !== "") {
                currentUser.experiencia += editText1.text.length
                val condicion = 100 * currentUser.nivel
                val amariExperience = currentUser.experiencia - condicion
                if(currentUser.experiencia >= condicion) {
                    currentUser.nivel++
                    if (amariExperience > 0) {
                        currentUser.experiencia = amariExperience
                    } else {
                        currentUser.experiencia = 0.0
                    }
                    if (currentUser.nivel in 4..40) {
                        currentUser.rango = "忍者くノ一"
                    } else if (currentUser.nivel in 41 .. 65) {
                        currentUser.rango = "侍"
                    } else if (currentUser.nivel > 65) {
                        currentUser.rango = "将軍"
                    } else {
                        currentUser.rango = "一般人"
                    }
                }
                dbUsers.updateUser(currentUser)
                dbUsers.close()
                val values = ContentValues()
                val imageUriString = intent.getStringExtra("imageUri")
                values.put("imageurl", imageUriString)
                values.put("texto", editText1.text.toString())
                values.put("UsuarioId", currentUser.id)
                translateText(editText1.text.toString(), values, currentUser);

            } else {
                Toast.makeText(baseContext, "No usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para realizar la traducción
    private fun translateText(textToTranslate: String, values: ContentValues, current:Userinfo) {
        //Create an English-German translator:
        val dbHelper = DbHelper(this@ImageDisplayActivity)
        val db = dbHelper.writableDatabase
        var nativeLanguage = TranslateLanguage.SPANISH
        var targetLanguage = TranslateLanguage.ENGLISH
        if (current.mother == "Japanese") {
            nativeLanguage = TranslateLanguage.JAPANESE
        }
        if (current.mother == "English") {
            nativeLanguage = TranslateLanguage.ENGLISH
        }
        if (current.target == "Japanese") {
            targetLanguage = TranslateLanguage.JAPANESE
        }
        if (current.target == "Spanish") {
            targetLanguage = TranslateLanguage.SPANISH
        }

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(targetLanguage)
            .setTargetLanguage(nativeLanguage)
            .build()
        val translator = Translation.getClient(options)
        var conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully. Okay to start translating.
                // (Set a flag, unhide the translation UI, etc.)
                Toast.makeText(baseContext,"exito",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                // Model couldn’t be downloaded or other internal error.
                Toast.makeText(baseContext,"error",Toast.LENGTH_SHORT).show()
            }
        translator.translate(textToTranslate)
            .addOnSuccessListener { translatedText ->
                // Translation successful.
                translation = translatedText.toString()
                Log.d("Translation", translation)
                values.put("translation", translation)

                db.insert(TABLE_WORDS, null, values)
                /*val newRowId = db.insert(TABLE_WORDS, null, values)

                if (newRowId != -1L) {
                    // La inserción fue exitosa
                    // Puedes realizar las acciones necesarias aquí
                } else {
                    // La inserción falló
                    // Puedes manejar el caso de error aquí
                }*/
                db.close()
                val intent = Intent(this, ar_screen::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { exception ->
                // Error.
                Toast.makeText(baseContext,"Transalation error",Toast.LENGTH_SHORT).show()
            }

    }

}