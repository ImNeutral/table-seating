package com.example.tableseatingtest1.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.tableseatingtest1.R
import com.example.tableseatingtest1.databinding.MainFragmentBinding


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle? ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.main_fragment, container, false
        )
        binding.buttonNext.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_customerListFragment)
        )

        createTableButtons(requireContext(), "Table 1")
        createTableButtons(requireContext(), "Table 2")
        createTableButtons(requireContext(), "Table 3")
        createTableButtons(requireContext(), "Table 4")
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun createTableButtons(context: Context, btnText: String) {
        val myButton = Button(context)
        myButton.setText(btnText)
        myButton.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        binding.tableListButtons.addView(myButton)
    }

}