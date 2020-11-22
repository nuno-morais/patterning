package dev.morais.patterning.records

import java.util.Date

data class Record(
    val id: String?,
    val type: String,
    val content: Map<String, String>,
    val date: Date,
    val userId: String,
    val createdAt: Date,
    val updatedAt: Date
) {
    companion object {
        fun generate() =
            Date().let {
                Record(
                    id = null,
                    type = "FOOD",
                    content = mapOf(
                        "ingredient1" to "10",
                        "ingredient2" to "20",
                        "calories" to "400"
                    ),
                    userId = "id1",
                    date = it,
                    createdAt = it,
                    updatedAt = it
                )
            }
    }

    override fun toString() = "" +
            "{" +
            " id: $id" +
            " type: $type" +
            " content: $content" +
            " user_id: $userId" +
            " date: $date" +
            " updated_at: $updatedAt" +
            " created_at: $createdAt" +
            "}"
}