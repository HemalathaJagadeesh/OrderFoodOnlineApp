package com.android.onlinefoodorderingapp.presentation.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.ImageRequest.*
import coil.request.SuccessResult
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


fun shareFoodWithImage(context: Context, item: FoodItem) {

    val imageLoader = ImageLoader(context)

    val request = Builder(context)
        .data(item.image) // your image URL
        .allowHardware(false) //converting to bitmap
        .build()

    CoroutineScope(Dispatchers.IO).launch {

        val result = imageLoader.execute(request)

        val bitmap = (result as? SuccessResult)?.drawable
            ?.toBitmap()

        bitmap?.let {

            val file = File(context.cacheDir, "food_image.png")
            val stream = FileOutputStream(file)
            it.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Check this out 🍔\n\n${item.name}\n₹${item.price}"
                )
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            withContext(Dispatchers.Main) {
                context.startActivity(
                    Intent.createChooser(shareIntent, "Share via")
                )
            }
        }
    }
}