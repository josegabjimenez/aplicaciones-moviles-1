package com.example.equipocinco.view.dialog

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.equipocinco.databinding.AddChallengeDialogBinding

class AddChallengeDialog {

    companion object{
        fun showDialog(context: Context) {
            val inflater = LayoutInflater.from(context)
            val binding = AddChallengeDialogBinding.inflate(inflater)
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setCancelable(false)
            alertDialog.setView(binding.root)

            binding.btnSave.isEnabled = false

            binding.etInsertChallenge.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    binding.btnSave.isEnabled = s.toString().isNotEmpty()
                }

            })

            binding.btnCancel.setOnClickListener {
                Toast.makeText(context,"Se ha cancelado la accion", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
            }

            binding.btnSave.setOnClickListener{
                Toast.makeText(context, "Reto adicionado", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
            }

            alertDialog.show()
        }
    }
}