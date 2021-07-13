package com.justaccounts.mindbrowseandroidtest.ui.deleted

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.justaccounts.mindbrowseandroidtest.R
import com.justaccounts.mindbrowseandroidtest.adapter.DeletedContactListAdapter
import com.justaccounts.mindbrowseandroidtest.databinding.DeletedFragmentBinding
import com.justaccounts.mindbrowseandroidtest.interfaces.DeleteContactListener
import com.justaccounts.mindbrowseandroidtest.model.DeletedPhoneContact
import com.justaccounts.mindbrowseandroidtest.ui.MainActivity
import com.justaccounts.mindbrowseandroidtest.utils.Utils

class DeletedFragment : Fragment(), DeleteContactListener {

    companion object {
        fun newInstance() = DeletedFragment()
    }

    private lateinit var deletedFragmentBinding: DeletedFragmentBinding;
    private lateinit var viewModel: DeletedViewModel
    private lateinit var adapter: DeletedContactListAdapter
    private val TAG = "DeletedFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        deletedFragmentBinding = DeletedFragmentBinding.inflate(inflater, container, false)
        initUI()
        initRecycler()
        viewModel = ViewModelProvider(this).get(DeletedViewModel::class.java)
        viewModel.deletedContacts.observe(viewLifecycleOwner, { deletedContacts ->
            Log.i(TAG, "onCreateView: " + deletedContacts)
            adapter.setData(deletedContacts)
        })
        return deletedFragmentBinding.root
    }

    private fun initRecycler() {
        adapter = DeletedContactListAdapter(requireActivity(), this)
        val recyclerView = deletedFragmentBinding.deletedContactRv
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    private fun initUI() {
        (requireActivity() as MainActivity).setToolbarTitle(getString(R.string.delete_label))
        (requireActivity() as MainActivity).showBackButton(true)
    }

    override fun onDeleteContactClick(deletedPhoneContact: DeletedPhoneContact, position: Int) {
        Utils.displayAlert(requireActivity(),getString(R.string.restore_contact_title),getString(R.string.restore_contact) + " "+ deletedPhoneContact.mobileNo +" ?",{ dialogInterface, which ->
            viewModel.restoreDeletedContact(deletedPhoneContact);
            refreshContacts(position);
            Toast.makeText(requireActivity(),deletedPhoneContact.mobileNo+getString(R.string.restore_success), Toast.LENGTH_LONG).show()
        })
    }

    private fun refreshContacts(position: Int) {
        viewModel.deletedContacts.observe(viewLifecycleOwner, { contacts ->
            Log.i(TAG, "retriveContacts: " + contacts)
            adapter.refreshRemovedData(contacts,position)
        })
    }
}