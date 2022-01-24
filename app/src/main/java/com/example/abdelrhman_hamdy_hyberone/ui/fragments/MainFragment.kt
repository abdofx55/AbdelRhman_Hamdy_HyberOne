package com.example.abdelrhman_hamdy_hyberone.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abdelrhman_hamdy_hyberone.R
import com.example.abdelrhman_hamdy_hyberone.data.models.Item
import com.example.abdelrhman_hamdy_hyberone.databinding.MainFragmentBinding
import com.example.abdelrhman_hamdy_hyberone.ui.adapter.ItemsAdapter
import com.example.abdelrhman_hamdy_hyberone.ui.viewModel.MainViewModel
import com.example.abdelrhman_hamdy_hyberone.utils.DownloadStatus
import com.example.abdelrhman_hamdy_hyberone.utils.NetworkUtil
import com.example.abdelrhman_hamdy_hyberone.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        const val LOG_TAG = "MAIN_FRAGMENT_LOG_TAG"
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var adapter: ItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        setupUI()
        loadData()

        return binding.root
    }

    private fun loadData() {
        // check internet connection
        if (NetworkUtil.isConnected(requireContext())) {
            Log.d(LOG_TAG, getString(R.string.connected))
            setupObservers()

        } else {
            Log.d(LOG_TAG, getString(R.string.no_internet))
            viewModel.setErrorMessage(getString(R.string.no_internet))
        }
    }


    private fun setupUI() {

        binding.apply {
            // Setting viewModel in xml to update UI
            mainViewModel = viewModel
            lifecycleOwner = requireActivity()

            // create layout Manager and setting adapter to recycler view
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

            adapter = ItemsAdapter(ItemsAdapter.OnClickListener { position, item ->
                Log.d(LOG_TAG, "${item.name} is clicked and status is : ${item.status}")
                fakeDownload(position, item)
            })

            recyclerView.adapter = adapter

            // Swipe to Refresh Layout
            swipeRefreshLayout.setOnRefreshListener {
                loadData()                // load again
                swipeRefreshLayout.isRefreshing = false     // stop refreshing

            }
        }
    }

    private fun setupObservers() {

        viewModel.resource.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d(LOG_TAG, Status.SUCCESS.toString())
                    binding.spinKit.visibility = View.GONE
                    viewModel.setErrorMessage("")
                    it.data?.let { items -> adapter.submitList(items) }
                }

                Status.LOADING -> {
                    Log.d(LOG_TAG, Status.LOADING.toString())
                    binding.spinKit.visibility = View.VISIBLE
                    viewModel.setErrorMessage("Loading...")
                }

                Status.ERROR -> {
                    Log.d(LOG_TAG, Status.ERROR.toString())
                    binding.spinKit.visibility = View.GONE
                    viewModel.setErrorMessage(it.message ?: "Error Loading Items")
                }
            }
        })
    }

    private fun fakeDownload(position: Int, item: Item) {
        if (item.status == null && item.downloadPercentage < 100) {
            Log.d(LOG_TAG, "Not Downloaded")
            // immediate start download
            item.status = DownloadStatus.DOWNLOADING
            adapter.notifyItemChanged(position)
        }
    }
}


