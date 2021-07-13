package com.justaccounts.mindbrowseandroidtest.ui.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.justaccounts.mindbrowseandroidtest.R
import com.justaccounts.mindbrowseandroidtest.databinding.HomeFragmentBinding
import com.justaccounts.mindbrowseandroidtest.ui.MainActivity
import com.justaccounts.mindbrowseandroidtest.ui.contact.ContactsFragment
import com.justaccounts.mindbrowseandroidtest.ui.deleted.DeletedFragment
import com.justaccounts.mindbrowseandroidtest.ui.favorite.FavoriteFragment
import com.justaccounts.mindbrowseandroidtest.utils.Utils
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class HomeFragment : Fragment() {
    lateinit var homeFragmentBinding: HomeFragmentBinding;
    private val TAG = "HomeFragment"

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeFragmentBinding = HomeFragmentBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).setToolbarTitle(getString(R.string.home_label))
        (requireActivity() as MainActivity).showBackButton(false)
        homeFragmentBinding.contactBtn.setOnClickListener {
            onContactBtnClick()
        }
        homeFragmentBinding.favBtn.setOnClickListener {
            onFavoriteBtnClick()
        }
        homeFragmentBinding.deletedBtn.setOnClickListener {
            onDeleteBtnClick()
        }
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        return homeFragmentBinding.root
    }

    private fun onFavoriteBtnClick() {
        (requireActivity() as MainActivity).addFragment(FavoriteFragment.newInstance(), true);
    }

    private fun onContactBtnClick() {
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.READ_CONTACTS)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    openContactFragment();
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    if (response?.isPermanentlyDenied!!) {
                        Utils.displayAlert(
                            requireActivity(),
                            getString(R.string.permission_title),
                            getString(R.string.contact_permission_desc),
                            { dialogInterface, which ->
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts("package", activity!!.packageName, null)
                                intent.data = uri
                                startActivityForResult(
                                    intent,
                                    101
                                )
                            })
                    }
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

    private fun openContactFragment() {
        (requireActivity() as MainActivity).addFragment(
            ContactsFragment.newInstance(),
            true
        );
    }


    private fun onDeleteBtnClick() {
        (requireActivity() as MainActivity).addFragment(DeletedFragment.newInstance(), true);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 101) {
            openContactFragment()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}