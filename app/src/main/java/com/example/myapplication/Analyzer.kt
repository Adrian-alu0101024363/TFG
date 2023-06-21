package com.example.myapplication

import android.graphics.Rect
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.example.myapplication.ImageUtils
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import java.lang.reflect.Proxy
import java.nio.ByteBuffer

@ExperimentalGetImage class Analyzer(private val result: MutableLiveData<String>) : ImageAnalysis.Analyzer {
    val recognizer = TextRecognition.getClient(JapaneseTextRecognizerOptions.Builder().build())
    //Para cambiar texto a traves del model (clase creada necesaria)
    private lateinit var viewModel: ArActivityViewModel
    private lateinit var viewModelFactory: ArActivityViewModelFactory
    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    fun recognizeText(image: InputImage) : Task<Text> {
        return recognizer.process(image)
            .addOnSuccessListener { visionText ->
                result.value = visionText.text
                Log.d("rec", result.value.toString())
                /*val resultText = visionText.text
                for (block in visionText.textBlocks) {
                    val blockText = block.text
                    val blockCornerPoints = block.cornerPoints
                    val blockFrame = block.boundingBox
                    for (line in block.lines) {
                        val lineText = line.text
                        val lineCornerPoints = line.cornerPoints
                        val lineFrame = line.boundingBox
                        var tempText = ""
                        for (element in line.elements) {
                            val elementText = element.text
                            val elementCornerPoints = element.cornerPoints
                            val elementFrame = element.boundingBox
                            tempText += elementText
                        }
                        Log.d("bunsho" ,tempText)*

                    }
                }*/
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
                Log.d("failed", "no reconoce")
            }
    }
    override fun analyze(imageProxy: ImageProxy) {

        val mediaImage = imageProxy.image
        val rotationDegrees = imageProxy.imageInfo.rotationDegrees

        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            // We requested a setTargetAspectRatio, but it's not guaranteed that's what the camera
            // stack is able to support, so we calculate the actual ratio from the first frame to
            // know how to appropriately crop the image we want to analyze.
            val imageHeight = mediaImage.height
            val imageWidth = mediaImage.width

            val actualAspectRatio = imageWidth / imageHeight

            val convertImageToBitmap = ImageUtils.convertYuv420888ImageToBitmap(mediaImage)
            val cropRect = Rect(0, 0, imageWidth, imageHeight)

            val croppedBitmap =
                ImageUtils.rotateAndCrop(convertImageToBitmap, rotationDegrees, cropRect)
            recognizeText(InputImage.fromBitmap(croppedBitmap,0)).addOnCompleteListener {
                imageProxy.close()
            }
        }
    }
}