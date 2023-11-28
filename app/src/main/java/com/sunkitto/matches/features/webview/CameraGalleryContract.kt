package com.sunkitto.matches.features.webview

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.FileProvider
import com.sunkitto.matches.BuildConfig
import com.sunkitto.matches.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CameraGalleryContract : ActivityResultContract<String, Uri?>() {

    private var photoUri: Uri? = null

    override fun createIntent(
        context: Context,
        input: String,
    ): Intent =
        openImageIntent(context, input)

    override fun parseResult(
        resultCode: Int,
        intent: Intent?
    ): Uri? =
        if (resultCode != Activity.RESULT_OK) {
            null
        } else {
            intent?.data ?: photoUri
        }

    private fun openImageIntent(
        context: Context,
        mimeType: String,
    ): Intent {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoUri = createPhotoUri(context)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = mimeType

        val resolveInfoList = ArrayList<ResolveInfo>()
        val intents = ArrayList<Intent>()

        context.packageManager.queryIntentActivities(
            cameraIntent,
            PackageManager.MATCH_ALL
        ).forEach { resolveInfo ->
            val foundedIntent = Intent(cameraIntent).also { intent ->
                intent.component = ComponentName(
                    resolveInfo.activityInfo.packageName,
                    resolveInfo.activityInfo.name,
                )
            }
            intents.add(foundedIntent)
            resolveInfoList.add(resolveInfo)
        }

        context.packageManager.queryIntentActivities(
            galleryIntent,
            PackageManager.MATCH_ALL
        ).forEach { resolveInfo ->
            val foundedIntent = Intent(galleryIntent).also { intent ->
                intent.component = ComponentName(
                    resolveInfo.activityInfo.packageName,
                    resolveInfo.activityInfo.name,
                )
            }
            intents.add(foundedIntent)
            resolveInfoList.add(resolveInfo)
        }

        return Intent.createChooser(
            galleryIntent,
            context.getString(R.string.choose_source)
        ).also { intent ->
            intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toTypedArray())
        }
    }

    private fun createPhotoUri(context: Context): Uri {

        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(Date())

        val imageFileName = "IMG_" + timestamp + "_"

        val storageDir = context.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES
        ) ?: throw IllegalStateException("Directory not found")

        val file = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir,
        )

        return FileProvider.getUriForFile(
            context,
            BuildConfig.APPLICATION_ID +
                    ".provider",
            file
        )
    }
}