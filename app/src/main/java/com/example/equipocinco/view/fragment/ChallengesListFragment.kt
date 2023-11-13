package com.example.equipocinco.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.equipocinco.R
import com.example.equipocinco.databinding.FragmentChallengesListBinding
import com.example.equipocinco.view.adapter.ChallengeAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = binding.contentToolbar.toolbar
        val toolbarTitle:TextView = toolbar.findViewById(R.id.toolbarTitle)
        toolbarTitle.text = "Retos"
        toolbar.setNavigationOnClickListener{
            findNavController().popBackStack()
        }
        controller()
        observerViewModel()

    }

    private fun observerViewModel(){
        observerChallengesList()
    }

    private fun observerChallengesList(){
        challengesViewModel.getChallengesList()
        challengesViewModel.challengesList.observe(viewLifecycleOwner){challengesList ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            layoutManager.reverseLayout = true
            layoutManager.stackFromEnd = true
            recycler.layoutManager = layoutManager
            val adapter = ChallengeAdapter(challengesList)
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun controller(){
        val dialog = AddChallengeDialog(challengesViewModel) {
            observerChallengesList()
        }
        binding.fbagregar.setOnClickListener{
            dialog.showDialog(binding.root.context)
        }

    }

}