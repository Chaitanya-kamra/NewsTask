package com.chaitanya.newstask

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter: NewsListAdapter
    private val API_KEY = "2666cdb58dc944c7baa690328d4d610c"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter
        fetchData()
    }

    private fun fetchData() {
        val mainProgress = findViewById<ProgressBar>(R.id.mainProgress)
        mainProgress.visibility= View.VISIBLE
        val tvError = findViewById<TextView>(R.id.tvError)
        val service = ApiClient.newsApiService
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.getTopHeadlines(
                    "in","technology",API_KEY
                )
                Log.e("check",response.articles.toString())
                withContext(Dispatchers.Main) {
                    mAdapter.updateNews(response.articles)
                    mainProgress.visibility= View.GONE
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    tvError.visibility= View.VISIBLE
                    mainProgress.visibility= View.GONE
                    Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
    override fun onItemClicked(item: Article) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        val params = CustomTabColorSchemeParams.Builder()
        params.setToolbarColor(
            ContextCompat.getColor(
                this@MainActivity,
                androidx.appcompat.R.color.material_blue_grey_950
            )
        )
        builder.setDefaultColorSchemeParams(params.build())
        builder.setShowTitle(true)
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}