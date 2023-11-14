package com.example.equipocinco.view.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.equipocinco.R
import com.example.equipocinco.databinding.RandomChallengeDialogBinding
import com.example.equipocinco.model.Pokemon
import com.example.equipocinco.viewmodel.ChallengesViewModel
import kotlin.random.Random
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import android.graphics.drawable.Drawable
import android.util.Log

class RandomChallengeDialog (private val challengesViewModel: ChallengesViewModel) {
    fun showDialog(
        context: Context
    ): AlertDialog {
        val inflater = LayoutInflater.from(context)
        val binding = RandomChallengeDialogBinding.inflate(inflater)

        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.setCancelable(false) //para cuando se cliquee por los lados no se pueda
        alertDialog.setView(binding.root) //establecer la vista de un cuadro de dialogo

        // Elijo un pokemon al azar de la lista de pokemones
        val pokemonSize = challengesViewModel.pokemonList.value?.size
        val randomPosition = Random.nextInt(pokemonSize ?: 151)
        val pokemon: Pokemon? = challengesViewModel.pokemonList.value?.get(randomPosition)

        val originalUrl = pokemon?.img
        val secureUrl = originalUrl?.replace("http://", "https://")

        /*println("POKEMONESS")
        println(challengesViewModel.pokemonList.value?.get(2)?.name)
        println("TAMAÑO DE POKEMONES:")
        println(challengesViewModel.pokemonList.value?.size.toString())
        println("POKEMON")
        println(pokemon?.name)
        println("POKEMON IMAGE")
        println(secureUrl)*/
        Glide.with(binding.root.context).load(secureUrl).into(binding.imPokemon)

/*        Glide.with(binding.root.context)
            .load(secureUrl)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    // Log the GlideException or handle the error as needed
                    Log.e("Glide", "Image load failed: $e")

                    // Return false to allow Glide to continue trying to load the image
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    // Log that the image load was successful
                    Log.d("Glide", "Image load successful")

                    // Return false to allow Glide to set the resource on the target
                    return false
                }
            })
            .into(binding.imPokemon)*/


        // Establezco el challenge en el texto del dialog
        binding.tvChallengeText.text = challengesViewModel.randomChallenge.value?.description.toString()

        binding.btnClose.setOnClickListener {
            //Toast.makeText(context,"Aceptar", Toast.LENGTH_SHORT).show()
            alertDialog.dismiss()
        }

        alertDialog.show()

        return alertDialog

    }
}