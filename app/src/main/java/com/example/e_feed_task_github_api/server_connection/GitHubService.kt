package com.example.e_feed_task_github_api.server_connection

import com.example.e_feed_task_github_api.data_classes.Issue
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("repos/{owner}/{repo}/issues")
    fun getIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<List<Issue>> // Issue model class will represent the structure of the response
}
