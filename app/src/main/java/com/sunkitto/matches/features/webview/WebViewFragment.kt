package com.sunkitto.matches.features.webview

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sunkitto.matches.databinding.FragmentWebViewBinding

private const val PAGE_LINK = "https://fex.net/"
private const val MIME_TYPE_IMAGE = "image/*"

class WebViewFragment : Fragment() {

    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

    private var uploadMessages: ValueCallback<Array<Uri>?>? = null

    private val cameraGalleryResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            CameraGalleryContract()
        ) { imageUri: Uri? ->
            if(imageUri != null) {
                uploadMessages?.onReceiveValue(arrayOf(imageUri))
                uploadMessages = null
            }
        }

    private var isCameraPermissionGranted = false
    private val cameraPermissionResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            isCameraPermissionGranted = isGranted
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWebViewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.refreshButton.setOnClickListener {
            binding.webView.reload()
        }

        with(binding.webView.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowFileAccess = true
        }
        binding.webView.loadUrl(PAGE_LINK)

        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>?>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                val checkCameraPermission = ContextCompat
                    .checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)

                if(checkCameraPermission != PackageManager.PERMISSION_GRANTED) {
                    cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)
                } else {
                    isCameraPermissionGranted = true
                }

                if(isCameraPermissionGranted) {
                    uploadMessages?.onReceiveValue(null)
                    uploadMessages = filePathCallback
                    cameraGalleryResultLauncher.launch(MIME_TYPE_IMAGE)
                }
                return true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}