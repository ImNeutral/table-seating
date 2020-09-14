package com.example.tableseatingtest1.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.android.trackmysleepquality.database.TableSeatingDatabase
import com.example.tableseatingtest1.R
import com.example.tableseatingtest1.databinding.MainFragmentBinding
import com.example.tableseatingtest1.ui.customer.CustomerListViewModel
import com.example.tableseatingtest1.ui.customer.CustomerListViewModelFactory


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var customerListViewModel: CustomerListViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.main_fragment, container, false
        )
        requireActivity().setTitle(R.string.app_name)
        var buttonsOfTables = mapOf(
            binding.table1 to 1L,
            binding.table2 to 2L,
            binding.table3 to 3L,
            binding.table4 to 4L
        )
        var tableCustomerCount = arrayListOf<Int>(0, 0, 0, 0)
        customerListViewModel = generateCustomerListViewModel()
        customerListViewModel.initAllCustomers()
        customerListViewModel.allCustomers.observe(viewLifecycleOwner, Observer {
            it.forEach {
                tableCustomerCount[it.tableId.toInt() - 1] += 1
            }
            buttonsOfTables.map { (btn: Button, tableId: Long) ->
                btn.text = String.format(
                    getResources().getString(R.string.table_btn_text),
                    tableId.toInt(),
                    tableCustomerCount[tableId.toInt() - 1]
                );
            }
        })
        buttonsOfTables.map { (btn: Button, tableId: Long) ->
            btn.setOnClickListener {
                it.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToCustomerListFragment(
                        tableId
                    )
                )
            }
        }
        setHasOptionsMenu(true)

        return binding.root
    }

    fun generateCustomerListViewModel(): CustomerListViewModel {
        val application = requireNotNull(this.activity).application

        val dataSource = TableSeatingDatabase.getInstance(application).customerDao

        val viewModelFactory = CustomerListViewModelFactory(dataSource, application)

        return ViewModelProvider(requireActivity(), viewModelFactory)
            .get(CustomerListViewModel::class.java)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clearTables -> {
                customerListViewModel.clearAllCustomers()
                Toast.makeText(requireContext(), "All tables has been cleared!", Toast.LENGTH_SHORT)
                    .show()
                refreshMainFragment()
            }
            else -> Log.i("MainFragment", "Clicked Other things.")
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun refreshMainFragment() {
        val ft: FragmentTransaction = fragmentManager!!.beginTransaction()
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false)
        }
        ft.detach(this).attach(this).commit()
    }
}