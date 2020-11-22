package dev.morais.patterning.records

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecordsViewModel @ViewModelInject constructor(
    private val recordsRepository: RecordsRepository
) : ViewModel() {

    private val _records = mutableListOf<Record>()
    private val userId = Firebase.auth.currentUser?.uid ?: "" // TODO:

    init {
        recordsRepository.get(userId) {
            _records.addAll(it)
            records.value = _records
        }
    }

    val records = MutableLiveData<List<Record>>().apply {
        value = mutableListOf()
    }

    fun addRecord(record: Record) {
        recordsRepository.add(record.copy(userId = userId)) {
            it?.also {
                _records.add(0, it)
                records.value = _records
            }
        }
    }

    fun deleteRecord(record: Record) {
        recordsRepository.delete(record) {
            if (it) {
                _records.remove(record)
                records.value = _records
            }
        }
    }
}