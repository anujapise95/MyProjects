package com.justaccounts.mindbrowseandroidtest.ui.contact

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.justaccounts.mindbrowseandroidtest.R
import com.justaccounts.mindbrowseandroidtest.adapter.ContactListAdapter
import com.justaccounts.mindbrowseandroidtest.databinding.ContactsFragmentBinding
import com.justaccounts.mindbrowseandroidtest.databinding.IncludeLoadingBinding
import com.justaccounts.mindbrowseandroidtest.interfaces.ContactListener
import com.justaccounts.mindbrowseandroidtest.model.PhoneContact
import com.justaccounts.mindbrowseandroidtest.ui.MainActivity
import com.justaccounts.mindbrowseandroidtest.utils.Utils
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.khoiron.actionsheets.ActionSheet
import com.khoiron.actionsheets.callback.ActionSheetCallBack


class ContactsFragment : Fragment(), ContactListener {
    private val TAG = "ContactsFragment"
    lateinit var contactsFragmentBinding: ContactsFragmentBinding
    lateinit var adapter: ContactListAdapter;
    lateinit var includeLoadingBinding: IncludeLoadingBinding

    companion object {
        fun newInstance() = ContactsFragment()
    }

    private lateinit var viewModel: ContactsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactsFragmentBinding = ContactsFragmentBinding.inflate(inflater, container, false);
        includeLoadingBinding= IncludeLoadingBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
        intiUi()
        initRecycler()

        contactsFragmentBinding.progressBar.visibility = View.VISIBLE
        retriveContacts();

        return contactsFragmentBinding.root
    }

    private fun initRecycler() {
        adapter = ContactListAdapter(requireActivity(), this)
        val recyclerView = contactsFragmentBinding.contactRv
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    private fun intiUi() {
        (requireActivity() as MainActivity).setToolbarTitle(getString(R.string.contact))
        (requireActivity() as MainActivity).showBackButton(true)
    }

    private fun retriveContacts() {
        viewModel.noOfUser.observe(viewLifecycleOwner, { noOfUsers ->
            if (noOfUsers == 0) {
                viewModel.getContacts(requireActivity().contentResolver)
            }else{
              getContactsFromDb();
            }
        })
    }

    private fun getContactsFromDb() {
        viewModel.getAllContacts().observe(viewLifecycleOwner, { contacts ->
            contactsFragmentBinding.progressBar.visibility = View.INVISIBLE
            adapter.setData(contacts)
        })
    }

    override fun onFavClick(phoneContact: PhoneContact) {
        viewModel.markFavContact(phoneContact)
    }

    override fun onDeleteClick(phoneContact: PhoneContact, position: Int) {
        Utils.displayAlert(
            requireActivity(),
            getString(R.string.delete_contact_title),
            getString(R.string.delete_contact) + " " + phoneContact.mobileNo + " ?",
            { dialogInterface, which ->
                viewModel.deleteContact(phoneContact)
                refreshContacts(position,phoneContact);
            })
    }

    override fun onRowClick(phoneContact: PhoneContact) {
        val data by lazy { ArrayList<String>() }

        data.add("Call to "+phoneContact.mobileNo)
        data.add("SMS to "+phoneContact.mobileNo)

        ActionSheet(requireActivity(), data)
            .hideTitle()
            .setCancelTitle(getString(R.string.cancel))
            .setColorTitleCancel(R.color.black)
            .setColorData(R.color.colorPrimary)
            .create(object : ActionSheetCallBack {
                override fun data(data: String, position: Int) {
                    when (position) {
                        0 -> makeCall(phoneContact)
                        1 -> startSMSIntent(phoneContact.mobileNo,"Enter message here....")
                    }
                }
            })
    }

    fun makeCall(phoneContact: PhoneContact){
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.CALL_PHONE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    val callIntent = Intent(Intent.ACTION_CALL)
                    callIntent.data = Uri.parse("tel:" + phoneContact.mobileNo) //change the number
                    startActivity(callIntent)
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(
                        requireActivity(),
                        "Call Permission Denied",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest();
                }

            })
            .check()
    }

    fun startSMSIntent(phoneNumber: String, message: String?) {
        val intent = Intent(Intent.ACTION_SENDTO)
        // This ensures only SMS apps respond
        intent.data = Uri.parse("smsto:$phoneNumber")
        intent.putExtra("sms_body", message)
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent)
        }
    }

    private fun refreshContacts(position: Int, phoneContact: PhoneContact) {
        viewModel.getAllContacts().observe(viewLifecycleOwner, { contacts ->
            adapter.refreshRemovedData(contacts, position)
            Toast.makeText(
                requireActivity(),
                phoneContact.mobileNo + " deleted successfully",
                Toast.LENGTH_LONG
            ).show()
        })
    }
}