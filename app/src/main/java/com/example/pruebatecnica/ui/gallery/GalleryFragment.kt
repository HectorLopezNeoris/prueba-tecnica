package com.example.pruebatecnica.ui.gallery

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.load
import com.example.pruebatecnica.databinding.FragmentGalleryBinding
import com.example.pruebatecnica.utils.extension_functions.showAlertNoPermissions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream


@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2

    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    private var bitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()

        firebaseStorage = FirebaseStorage.getInstance()

        storageRef = firebaseStorage.reference
    }

    private fun galleryCheckPermission() {

        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    openGallery()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                    showAlertNoPermissions()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    showAlertNoPermissions()
                }

            }).check()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun cameraCheckPermission() {

        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            openCamera()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    showAlertNoPermissions()
                }
            }).check()
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                CAMERA_REQUEST_CODE -> {

                    bitmap = data?.extras?.get("data") as Bitmap

                    binding.ivPhoto.load(bitmap) {

                    }
                }

                GALLERY_REQUEST_CODE -> {

                    binding.ivPhoto.load(data?.data) {

                    }
                }

            }

        }
    }

    private fun setupListeners() {
        binding.btnGallery.setOnClickListener {
            galleryCheckPermission()
        }

        binding.btnCamera.setOnClickListener {
            cameraCheckPermission()
        }

        binding.ivPhoto.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(requireContext())
            pictureDialog.setTitle("Selecionar acción:")
            val pictureDialogItem = arrayOf(
                "Seleccionar foto de galería",
                "Capturar foto con la cámara"
            )
            pictureDialog.setItems(pictureDialogItem) { dialog, which ->
                when (which) {
                    0 -> openGallery()
                    1 -> openCamera()
                }
            }

            pictureDialog.show()
        }

        binding.btnUpload.setOnClickListener {
            if (bitmap != null) {
                val imageRef = storageRef.child("images/prueba.jpg")
                // Get the data from an ImageView as bytes
                binding.ivPhoto.isDrawingCacheEnabled = true
                binding.ivPhoto.buildDrawingCache()
                val bitmap = (binding.ivPhoto.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        "Error al subir la imagen", Toast.LENGTH_SHORT
                    )
                        .show()
                }.addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(
                        requireContext(),
                        "Imagen cargada con éxito", Toast.LENGTH_SHORT
                    )
                        .show()
                    binding.ivPhoto.setImageDrawable(null)
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "No hay foto por subir", Toast.LENGTH_SHORT
                ).show()
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}