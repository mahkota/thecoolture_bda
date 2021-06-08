package com.thecoolture.batikdetectionapp.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thecoolture.batikdetectionapp.data.StoreEntity
import com.thecoolture.batikdetectionapp.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {

    private lateinit var storeViewModel: StoreViewModel
    private var _binding: FragmentStoreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        storeViewModel =
            ViewModelProvider(this).get(StoreViewModel::class.java)

        _binding = FragmentStoreBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            binding.progressBar.visibility = View.VISIBLE

            storeViewModel.store.observe(viewLifecycleOwner, Observer {
                binding.progressBar.visibility = View.GONE

                val stores = it

                val storeAdapter = StoreAdapter()
                storeAdapter.setStores(stores)

                with(binding.rvStore) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = storeAdapter
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}