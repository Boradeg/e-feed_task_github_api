package com.example.e_feed_task_github_api.activities
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.e_feed_task_github_api.R
import com.example.e_feed_task_github_api.data_classes.Issue
import com.example.e_feed_task_github_api.server_connection.GitHubService
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class MainActivity : AppCompatActivity() {
    //create variables
    private lateinit var userName:TextView
    private lateinit var createdDate:TextView
    private lateinit var closedDate:TextView
    private lateinit var title:TextView
    private lateinit var issueStatus:TextView
    private lateinit var issueDesc:TextView
    //define base url
    private  val baseUrl = "https://api.github.com/"
    //define owner and repo name
    private val owner = "Boradeg"
    private val repo = "openAIAPI"
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initialize var
        userName=findViewById(R.id.myUserName)
        title=findViewById(R.id.issueTitle)
        closedDate=findViewById(R.id.closedDate)
        createdDate=findViewById(R.id.createdDate)
        issueDesc=findViewById(R.id.issueDesc)
        issueStatus=findViewById(R.id.issueStatus)
       //create retrofit instance
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val call =  retrofit.create(GitHubService::class.java).getIssues(owner, repo)
        call.enqueue(object : Callback<List<Issue>> {
            override fun onResponse(call: Call<List<Issue>>, response: Response<List<Issue>>) {
                if (response.isSuccessful) {
                    val issues: List<Issue>? = response.body()
                    // Process the list of issues
                    issues?.forEach { issue ->
                        title.text=issue.title
                        userName.text=issue.user.login
                        val imageUrl=issue.user.avatar_url
                        loadImageIntoImageView(imageUrl)
                        createdDate.text=issue.created_at
                        issueDesc.text=issue.body
                        closedDate.text=issue.closed_at
                        issueStatus.text=issue.state
                        FancyToast.makeText(this@MainActivity,"Issue Title : ${issue.title}", FancyToast.LENGTH_LONG, FancyToast.INFO,true).show()
                        FancyToast.makeText(this@MainActivity,"Issue Created Data : ${issue.created_at}", FancyToast.LENGTH_LONG, FancyToast.INFO,true).show()
                        FancyToast.makeText(this@MainActivity,"Issue Closed Date : ${issue.closed_at}", FancyToast.LENGTH_LONG, FancyToast.INFO,true).show()
                        FancyToast.makeText(this@MainActivity,"User Name : ${issue.user.login}", FancyToast.LENGTH_LONG, FancyToast.INFO,true).show()


                    }
                } else {
                    // Handle unsuccessful response
                    // For example, handle error codes (response.code())
                }
            }

            override fun onFailure(call: Call<List<Issue>>, t: Throwable) {
                // Handle failure to connect to the API or other network issues
            }
        })

    }
    fun loadImageIntoImageView(imageUrl: String) {
        val circularImageView = findViewById<ImageView>(R.id.userImage)

        Glide.with(this)
            .load(imageUrl)
            .transform(CircleCrop()) // Apply circular transformation
            .into(circularImageView)
    }
}