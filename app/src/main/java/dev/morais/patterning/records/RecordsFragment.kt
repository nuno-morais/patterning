package dev.morais.patterning.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import dev.morais.patterning.R

@AndroidEntryPoint
class RecordsFragment : Fragment() {
    private val recordsViewModel by viewModels<RecordsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.records, container, false)

        view.findViewById<RecyclerView>(R.id.records).also { recyclerView ->
            recordsViewModel.records.observe(viewLifecycleOwner) { records ->
                recyclerView.layoutManager = LinearLayoutManager(context)
                val adapter = RecordsRecyclerViewAdapter(context, records)
                recyclerView.adapter = adapter
                adapter.onDeleteClickListener { 
                    recordsViewModel.deleteRecord(it)
                }
            }
        }

        view.findViewById<FloatingActionButton>(R.id.add_record).also { button ->
            button.setOnClickListener {
                recordsViewModel.addRecord(Record.generate())
            }
        }

        return view
    }
}