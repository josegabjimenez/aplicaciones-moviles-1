package com.example.equipocinco.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.equipocinco.R
import com.example.equipocinco.databinding.FragmentChallengesListBinding
import com.example.equipocinco.view.dialog.AddChallengeDialog
import com.example.equipocinco.viewmodel.ChallengesViewModel

class ChallengesListFragment : Fragment() {
    private lateinit var binding: FragmentChallengesListBinding

    private val challengesViewModel: ChallengesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChallengesListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun setupToolbar(toolbar: Toolbar) {
        val toolbarTitle = toolbar.findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = "Retos"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller()
    }

    private fun controller(){
        val dialog = AddChallengeDialog(challengesViewModel)
        binding.fbagregar.setOnClickListener{
            dialog.showDialog(binding.root.context)
        }

    }
}