package com.example.pruebatecnica.utils.extension_functions

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.pruebatecnica.R

fun Fragment.showDialog(type: DialogType = DialogType.INFORMATION, title: String, message: String) {
    val alertDialog = AlertDialog.Builder(requireContext())
    alertDialog.setTitle(title)
    alertDialog.setMessage(message)
    when(type) {
        DialogType.INFORMATION -> {
            alertDialog.setPositiveButton("OK") { _, _ -> }
        }
        DialogType.CONFIRMATION -> {
            alertDialog.setPositiveButton("CONFIRM") { _, _ -> }
            alertDialog.setNegativeButton("CANCEL") { _, _ -> }
        }
    }
    alertDialog.setCancelable(false)
    alertDialog.show()
}

enum class DialogType() {
    INFORMATION,
    CONFIRMATION
}