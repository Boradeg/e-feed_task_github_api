package com.example.e_feed_task_github_api.data_classes

data class MyDataItem(
    val actor: Actor,
    val commit_id: Any,
    val commit_url: Any,
    val created_at: String,
    val event: String,
    val id: Long,
    val issue: Issue,
    val node_id: String,
    val performed_via_github_app: Any,
    val state_reason: Any,
    val url: String
)