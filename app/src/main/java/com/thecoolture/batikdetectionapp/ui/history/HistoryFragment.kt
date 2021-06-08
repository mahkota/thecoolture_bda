package com.thecoolture.batikdetectionapp.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thecoolture.batikdetectionapp.databinding.FragmentHistoryBinding
import com.thecoolture.batikdetectionapp.viewmodel.ViewModelFactory

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = activity?.let { ViewModelFactory.getInstance(it.application) }
        historyViewModel =
            factory?.let { ViewModelProvider(this, it).get(HistoryViewModel::class.java) }!!

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (activity != null) {
            binding.progressBar.visibility = View.VISIBLE

            historyViewModel.history.observe(viewLifecycleOwner, {
                binding.progressBar.visibility = View.GONE

                val history = it

                val historyAdapter = HistoryAdapter()
                historyAdapter.setHistory(history)

                with(binding.rvHistory) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = historyAdapter
                }
            })

        }
    }
}
