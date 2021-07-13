package com.justaccounts.mindbrowseandroidtest.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.justaccounts.mindbrowseandroidtest.R
import com.justaccounts.mindbrowseandroidtest.adapter.FavoriteContactListAdapter
import com.justaccounts.mindbrowseandroidtest.databinding.FavoriteFragmentBinding
import com.justaccounts.mindbrowseandroidtest.ui.MainActivity

class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var favoriteFragmentBinding: FavoriteFragmentBinding
    lateinit var adapter: FavoriteContactListAdapter;
    private val TAG = "FavoriteFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteFragmentBinding = FavoriteFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        initUI()
        initRecycler()
        viewModel.favContacts.observe(viewLifecycleOwner, { favContacts ->
            adapter.setData(favContacts)
        })
        return favoriteFragmentBinding.root
    }

    private fun initRecycler() {
        adapter = FavoriteContactListAdapter(requireActivity())
        val recyclerView = favoriteFragmentBinding.favContactRv
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    private fun initUI() {
        (requireActivity() as MainActivity).setToolbarTitle(getString(R.string.fav_label))
        (requireActivity() as MainActivity).showBackButton(true)
    }

}