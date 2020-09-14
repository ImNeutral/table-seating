package com.example.tableseatingtest1.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle? ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.main_fragment, container, false
        )
        requireActivity().setTitle(R.string.app_name)
        var buttonsOfTables = mapOf(
                binding.table1 to 1L,
                binding.table2 to 2L,
                binding.table3 to 3L,
                binding.table4 to 4L)
        var tableCustomerCount = arrayListOf<Int>(0, 0, 0, 0)
        var customerListViewModel = generateCustomerListViewModel()
        customerListViewModel.initAllCustomers()
        customerListViewModel.allCustomers.observe(viewLifecycleOwner, Observer {
            it.forEach {
                tableCustomerCount[it.tableId.toInt() - 1] += 1
            }
            buttonsOfTables.map { (btn: Button, tableId: Long) ->
                btn.text = String.format(getResources().getString(R.string.table_btn_text), tableId.toInt(), tableCustomerCount[tableId.toInt() - 1]);
            }
        })
        //customerListViewModel.initTableSizes()
        /*var counter = 0
        customerListViewModel.tableSizes.forEach {
            counter++
            it.observe(viewLifecycleOwner, Observer {
                buttonsOfTables.map { (btn: Button, tableId: Long) ->
                    if (counter.toLong() == tableId) {
                        btn.text = String.format(
                            getResources().getString(R.string.table_btn_text),
                            tableId.toInt(),
                            it
                        );
                    }
                }
            })
        }*/
        /*customerListViewModel.tableSizes.map {
            buttonsOfTables.map { (btn: Button, tableId: Long) ->
                if(it.key == tableId) {
                    btn.text = String.format(getResources().getString(R.string.table_btn_text), tableId.toInt(), it.value);
                }
            }
        }*/
        /*customerListViewModel.tableSizes.observe(viewLifecycleOwner, Observer {
            it.map{
                buttonsOfTables.map { (btn: Button, tableId: Long) ->
                    if(it.key == tableId) {
                        btn.text = String.format(getResources().getString(R.string.table_btn_text), tableId.toInt(), it.value);
                    }
                }
            }
        })*/
        /*customerListViewModel.allCustomers.observe(viewLifecycleOwner, Observer {
            countPerTable.
        })*/
        buttonsOfTables.map { (btn: Button, tableId: Long) ->
            //var customerCount = customerListViewModel.getCustomerCountPerTable(tableId)
            btn.text = String.format(getResources().getString(R.string.table_btn_text), tableId.toInt(), tableCustomerCount[tableId.toInt() - 1]);
            //btn.text = setText(R.string.table_btn_text, tableId.toInt(), tableCustomerCount[tableId.toInt()])
            btn.setOnClickListener {
                it.findNavController().navigate(MainFragmentDirections.actionMainFragmentToCustomerListFragment(tableId))
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
            R.id.clearTables -> Log.i("MainFragment", "Clicked Clear Tables")
            else -> Log.i("MainFragment", "Clicked Other things.")
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }
}

data class TableContent(var tableId: Long, var count: Long)