package com.example.motivationapp.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.motivationapp.databinding.ImageFormDialogBinding
import com.example.motivationapp.extensions.tryLoadImage

class ImageFormDialog(private val context: Context) {

    fun show(
        defaultUrl: String? = null,
        whenImageIsLoaded: (url: String) -> Unit
    ) {

        ImageFormDialogBinding.inflate(LayoutInflater.from(context)).apply {
            defaultUrl.let {
                ivFormImageContent.tryLoadImage(it)
                edtUrlImageField.setText(it)
            }

            btFormLoadImage.setOnClickListener {
                val url = edtUrlImageField.text.toString()
                ivFormImageContent.tryLoadImage(url)
            }

            AlertDialog.Builder(context)
                .setView(root)
                .setNegativeButton("Cancel") { _, _ -> }
                .setPositiveButton("Confirm") { _, _ ->
                    val url = edtUrlImageField.text.toString()
                    whenImageIsLoaded(url)
                }
                .show()
        }
    }
}
