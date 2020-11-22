package dev.morais.patterning.records

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class RecordsRepository @Inject constructor() {
    companion object {
        private const val TAG = "RECORDS"
    }

    fun get(userId: String, block: (records: List<Record>) -> Unit) {
        Log.d(TAG, "Getting records...")
        collection()
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                Log.d(TAG, "Records fetched successfully!")
                val records = result.map {
                    mapDocumentToRecord(it)
                }
                block(records)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error fetching records", e)
                block(emptyList())
            }
    }

    private fun mapDocumentToRecord(it: QueryDocumentSnapshot) =
        Record(
            id = it.id,
            type = it["type"] as String,
            content = it["content"] as Map<String, String>,
            date = (it["date"] as Timestamp).toDate(),
            updatedAt = (it["updatedAt"] as Timestamp).toDate(),
            createdAt = (it["createdAt"] as Timestamp).toDate(),
            userId = "id1"
        )

    fun add(record: Record, block: (record: Record?) -> Unit) =
        collection()
            .add(record)
            .addOnSuccessListener { documentReference: DocumentReference ->
                Log.d(TAG, "Record added with ID: ${documentReference.id}")
                block(record.copy(id = documentReference.id))

            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding Record", e)
                block(null)
            }

    fun delete(record: Record, block: (deleted: Boolean) -> Unit) =
        collection()
            .document(record.id!!)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Record successfully deleted!")
                block(true)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting record", e)
                block(false)
            }


    private fun collection() = Firebase.firestore.collection("records")

}