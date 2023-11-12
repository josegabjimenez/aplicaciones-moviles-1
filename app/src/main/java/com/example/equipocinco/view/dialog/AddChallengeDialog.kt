package com.example.equipocinco.view.dialog

import android.app.Application
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.equipocinco.databinding.AddChallengeDialogBinding
import com.example.equipocinco.model.Challenge
import com.example.equipocinco.viewmodel.ChallengesViewModel

class AddChallengeDialog (private val challengesViewModel: ChallengesViewModel) {
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
            println("BUTTON CLICKEEEEEDDD!!!")
            saveChallenge(binding.etInsertChallenge.text.toString())
            Toast.makeText(context, "Reto adicionado", Toast.LENGTH_SHORT).show()
            alertDialog.dismiss()
        }

        alertDialog.show()

    }
    private fun saveChallenge(description: String){
        println("SAVING CHALLENGEEEEE!!!")
        val challenge = Challenge(description = description)
        challengesViewModel.saveChallenge(challenge)
    }
}