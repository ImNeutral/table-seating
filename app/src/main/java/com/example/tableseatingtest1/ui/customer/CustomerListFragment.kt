package com.example.tableseatingtest1.ui.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.trackmysleepquality.database.TableSeatingDatabase
import com.example.tableseatingtest1.R
import com.example.tableseatingtest1.database.Customer
import com.example.tableseatingtest1.databinding.FragmentCustomerListBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CustomerListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CustomerListFragment : Fragment() {

    private lateinit var binding: FragmentCustomerListBinding

    override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?  ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_customer_list, container, false
        )
        var args = CustomerListFragmentArgs.fromBundle(requireArguments())
        var customerListViewModel = generateCustomerListViewModel()
        requireActivity().setTitle(getString(R.string.table_no, args.tableNo))
        binding.customerListViewModel = customerListViewModel
        binding.lifecycleOwner = this
        customerListViewModel.tableId = args.tableNo
        customerListViewModel.initCustomersOnTable()
        emptyCustomerNameInput()
        customerListViewModel.customerName = ""
        binding.addCustomer.setOnClickListener {
            customerListViewModel.customerName = binding.customerNameInput.text.toString()
            customerListViewModel.createCustomer()
            customerAddedMessage()
            emptyCustomerNameInput()
        }
        binding.clearTable.setOnClickListener {
            customerListViewModel.clearCustomers()
        }
        customerListViewModel.customersOnTable.observe(viewLifecycleOwner, Observer {
            hideAddCustomer(it.size == customerListViewModel.noCustomersAllowed)
            hasCustomers(it.size > 0)
            var stringText = StringBuilder()
            var counter = 1
            it.forEach { customer: Customer ->
                stringText.append(counter++)
                stringText.append(". ")
                stringText.append(customer.customerName)
                stringText.append("\n")
            }
            binding.customerNameList.text = stringText.toString()
        })

        return binding.root
    }

    fun generateCustomerListViewModel(): CustomerListViewModel {
        val application = requireNotNull(this.activity).application

        val dataSource = TableSeatingDatabase.getInstance(application).customerDao

        val viewModelFactory = CustomerListViewModelFactory(dataSource, application)

        return ViewModelProvider(requireActivity(), viewModelFactory)
            .get(CustomerListViewModel::class.java)

    }

    fun hideAddCustomer(isFull: Boolean) {
        if(isFull) {
            binding.customerNameInput.visibility = View.GONE
            binding.addCustomer.visibility = View.GONE
            Toast.makeText(requireContext(), "Table is now full.", Toast.LENGTH_SHORT).show()
        } else {
            binding.customerNameInput.visibility = View.VISIBLE
            binding.addCustomer.visibility = View.VISIBLE
        }
    }

    fun hasCustomers(hasCustomers: Boolean) {
        if(hasCustomers) {
            binding.clearTable.visibility = View.VISIBLE
        } else {
            binding.clearTable.visibility = View.GONE
        }
    }

    fun emptyCustomerNameInput() {
        binding.customerNameInput.setText("")
    }

    fun customerAddedMessage() {
        Toast.makeText(requireContext(), "A new customer has been added!\nWelcome ${binding.customerNameInput.text.toString()}!", Toast.LENGTH_SHORT).show()
    }
}