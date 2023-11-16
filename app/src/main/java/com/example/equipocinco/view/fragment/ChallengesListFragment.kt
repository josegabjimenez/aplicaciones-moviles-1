package com.example.equipocinco.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import android.widget.Button

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.equipocinco.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.equipocinco.databinding.FragmentChallengesListBinding
import com.example.equipocinco.view.adapter.ChallengeAdapter
import com.example.equipocinco.view.dialog.AddChallengeDialog
import com.example.equipocinco.view.viewholder.OnEditClickListener
import com.example.equipocinco.viewmodel.ChallengesViewModel

class ChallengesListFragment : Fragment(), OnEditClickListener {

    private lateinit var binding: FragmentChallengesListBinding
    private val challengesViewModel: ChallengesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengesListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = binding.contentToolbar.toolbar
        val toolbarTitle: TextView = toolbar.findViewById(R.id.toolbarTitle)
        toolbarTitle.text = "Retos"
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        setupRecyclerView()
        setupAddButton()
    }

    private fun setupRecyclerView() {
        val recycler = binding.recyclerview
        val layoutManager = LinearLayoutManager(context)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recycler.layoutManager = layoutManager
        val adapter = ChallengeAdapter(challengesViewModel.challengesList.value ?: mutableListOf(), this)
        recycler.adapter = adapter

        observerChallengesList()
    }

    private fun setupAddButton() {
        val dialog = AddChallengeDialog(challengesViewModel) {
            observerChallengesList()
        }
        binding.fbagregar.setOnClickListener {
            dialog.showDialog(binding.root.context)
        }
    }

    private fun observerChallengesList() {
        challengesViewModel.getChallengesList()
        challengesViewModel.challengesList.observe(viewLifecycleOwner) { challengesList ->
            val adapter = binding.recyclerview.adapter as ChallengeAdapter
            adapter.updateChallenges(challengesList)
        }
    }


    override fun onEditClick(challengeId: Int) {
        // Aquí deberías abrir un AlertDialog o hacer alguna otra acción cuando se haga clic en Editar.
        // Puedes utilizar el ID del reto (challengeId) para realizar operaciones adicionales.
        println("Edit Clicked for Challenge ID: $challengeId")
        showEditDialog(challengeId)
    }

    private fun showEditDialog(challengeId: Int) {
        val viewModel = challengesViewModel // Replace with your actual ViewModel reference

        // Inflate the custom layout
        val inflater = LayoutInflater.from(requireContext())
        val customLayout = inflater.inflate(R.layout.edit_dialog, null)

        // Find views in the custom layout
        val tvDialogTitle = customLayout.findViewById<TextView>(R.id.tvDialogTitle)
        val etDescription = customLayout.findViewById<EditText>(R.id.etDescription)
        val btnSave = customLayout.findViewById<Button>(R.id.btnSave)
        val btnCancel = customLayout.findViewById<Button>(R.id.btnCancel)

        // Set up the AlertDialog
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        alertDialog.setView(customLayout)

        // Set the title
        tvDialogTitle.text = "Editar Descripción"

        // Get the challenge by ID
        val currentChallenge = viewModel.getChallengeById(challengeId)

        // Set the current description in the EditText
        etDescription.setText(currentChallenge?.description)

        // Set click listeners for buttons
        btnSave.setOnClickListener {
            // Handle save button click
            val newDescription = etDescription.text.toString()
            currentChallenge?.let {
                viewModel.updateChallengeDescription(it.id, newDescription)
            }
            alertDialog.dismiss()
        }

        btnCancel.setOnClickListener {
            // Handle cancel button click
            alertDialog.dismiss()
        }

        // Show the AlertDialog
        alertDialog.show()
    }


    override fun onDeleteClick(challengeId: Int) {
        // Lógica para manejar el clic en el botón de eliminar
        // Puedes mostrar un diálogo de confirmación para la eliminación aquí.
        showDeleteConfirmationDialog(challengeId)
    }

    private fun showDeleteConfirmationDialog(challengeId: Int) {
        // Implementa aquí la lógica para mostrar un diálogo de confirmación para la eliminación.
        // Puedes utilizar AlertDialog.Builder para construir el diálogo y manejar los clics en los botones Aceptar/Cancelar.
        // Después, puedes llamar a challengesViewModel.deleteChallenge(challengeId) si el usuario confirma la eliminación.
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmar eliminación")
            .setMessage("¿Seguro que quieres eliminar este reto?")
            .setPositiveButton("Aceptar") { _, _ ->
                // Lógica para eliminar el desafío
                challengesViewModel.deleteChallenge(challengeId)
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}
