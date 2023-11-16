package com.example.equipocinco.view.fragment

import android.annotation.SuppressLint
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

    @SuppressLint("MissingInflatedId")
    private fun showEditDialog(challengeId: Int) {
        val viewModel = challengesViewModel // Reemplaza con tu referencia real al ViewModel

        // Inflate del layout personalizado
        val inflater = LayoutInflater.from(requireContext())
        val customLayout = inflater.inflate(R.layout.edit_dialog, null)

        // Encuentra vistas en el layout personalizado
        val tvDialogTitle = customLayout.findViewById<TextView>(R.id.tvDialogTitle)
        val etDescription = customLayout.findViewById<EditText>(R.id.etDescription)
        val btnSave = customLayout.findViewById<Button>(R.id.btnSave)
        val btnCancel = customLayout.findViewById<Button>(R.id.btnCancel)

        // Configuración del AlertDialog
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        alertDialog.setView(customLayout)

        // Configuración del título

        // Obtener el reto por ID
        val currentChallenge = viewModel.getChallengeById(challengeId)

        // Establecer la descripción actual en el EditText
        etDescription.setText(currentChallenge?.description)

        // Configurar clics en botones
        btnSave.setOnClickListener {
            // Manejar clic en botón Guardar
            val newDescription = etDescription.text.toString()
            currentChallenge?.let {
                viewModel.updateChallengeDescription(it.id, newDescription)
            }
            alertDialog.dismiss()
        }

        btnCancel.setOnClickListener {
            // Manejar clic en botón Cancelar
            alertDialog.dismiss()
        }

        // Evitar que el diálogo se cierre al tocar fuera de él
        alertDialog.setCanceledOnTouchOutside(false)

        // Mostrar el AlertDialog
        alertDialog.show()
    }



    override fun onDeleteClick(challengeId: Int) {
        // Lógica para manejar el clic en el botón de eliminar
        // Puedes mostrar un diálogo de confirmación para la eliminación aquí.
        showDeleteConfirmationDialog(challengeId)
    }

    private fun showDeleteConfirmationDialog(challengeId: Int) {
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_confirmation, null)

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()

        val btnConfirmDelete = dialogView.findViewById<Button>(R.id.btnConfirmDelete)
        val btnCancelDelete = dialogView.findViewById<Button>(R.id.btnCancelDelete)
        val tvChallengeDescription = dialogView.findViewById<TextView>(R.id.tvChallengeDescription)

        // Obtener el reto por ID
        val currentChallenge = challengesViewModel.getChallengeById(challengeId)

        // Mostrar la descripción del reto en el TextView del diálogo
        tvChallengeDescription.text = currentChallenge?.description ?: "No description available"

        btnConfirmDelete.setOnClickListener {
            // Lógica para eliminar el desafío
            challengesViewModel.deleteChallenge(challengeId)
            alertDialog.dismiss()
        }

        btnCancelDelete.setOnClickListener {
            alertDialog.dismiss()
        }

        // Evitar que el diálogo se cierre al tocar fuera de él
        alertDialog.setCanceledOnTouchOutside(false)

        alertDialog.show()
    }




}
