package com.example.equipocinco.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.equipocinco.R
import androidx.appcompat.widget.Toolbar
import android.widget.TextView
import com.example.equipocinco.view.dialog.AddChallengeDialog.Companion.showDialog
import com.example.equipocinco.databinding.FragmentChallengesListBinding
import com.example.equipocinco.viewmodel.ChallengesViewModel

class ChallengesListFragment : Fragment() {
    private lateinit var binding: FragmentChallengesListBinding

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
        binding.fbagregar.setOnClickListener{
            showDialog(binding.root.context)
        }

    }
}