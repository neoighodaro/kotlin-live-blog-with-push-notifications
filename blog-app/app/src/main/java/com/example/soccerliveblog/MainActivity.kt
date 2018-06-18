package com.example.soccerliveblog


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.pushnotifications.PushNotifications
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var pusher: Pusher
    private val blogListAdapter = BlogListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PushNotifications.start(applicationContext,
                PUSHER_BEAMS_INSTANCEID)
        PushNotifications.subscribe("world-cup")
        with(recyclerViewBlogPosts){
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = blogListAdapter
        }
        setupPusher()
    }



    private fun setupPusher() {
        val options = PusherOptions()
        options.setCluster(PUSHER_CLUSTER)
        pusher = Pusher(PUSHER_API_KEY, options)

        val channel = pusher.subscribe("soccer")

        channel.bind("world-cup") { channelName, eventName, data ->
            val jsonObject = JSONObject(data)
            val time = jsonObject.getString("currentTime")
            val currentActivity = jsonObject.getString("currentPost")
            val model = BlogPostModel(time,currentActivity)
            runOnUiThread {
                blogListAdapter.addItem(model)
            }
        }
        pusher.connect()

    }

}
