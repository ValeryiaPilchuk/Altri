package com.example.altri

import android.app.Activity
import android.os.Bundle
import android.widget.ImageButton
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import android.content.Intent
import android.media.Image
import android.net.Uri
import com.example.altri.data.Message
import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.motion.utils.Oscillator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.altri.utils.BotResponse
import com.example.altri.utils.Constants.OPEN_GOOGLE
import com.example.altri.utils.Constants.OPEN_SEARCH
import com.example.altri.utils.Constants.RECEIVE_ID
import com.example.altri.utils.Constants.SEND_ID
import com.example.altri.utils.Time
import kotlinx.android.synthetic.main.activity_chat_bot.*
import kotlinx.coroutines.*
import java.sql.Timestamp

class ChatbotActivity: Activity() {

    private lateinit var adapter: MessagingAdapter
    private val botlist = listOf("Altri")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // requestWindowFeature(Window.FEATURE_NO_TITLE)
        //this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_chat_bot)
        //val background: ConstraintLayout = findViewById(R.id.)
        val btnBack = findViewById<ImageButton>(R.id.imageButton)

        recyclerView()

        clickEvents()

        //val random = (0..0).random() //choose to always speak to altri
        customMessage("Hello!, this is ${botlist[0]} speaking, how may I help you today? :)") //first msg in recyclerview
        btnBack.setOnClickListener{
            val intent = Intent(this,ChatbotMenu::class.java)
            startActivity(intent)
        }
    }


    private fun clickEvents() {
        btn_send.setOnClickListener(){
            sendMessage()
        }
        et_message.setOnClickListener{
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(adapter.itemCount - 1) //keep us at the bottom of the screen
                }
            }
        }
    }


    private fun recyclerView(){
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun sendMessage(){
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if(message.isNotEmpty()){
            et_message.setText("")
            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String){
        val timeStamp = Time.timeStamp()

        GlobalScope.launch{
            delay(1000) //1 second

            withContext(Dispatchers.Main){
                val response = BotResponse.basicResponses(message)

                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))
                rv_messages.scrollToPosition(adapter.itemCount - 1) //take us down all the way to the last message in order to stay up to date


                when(response){
                    OPEN_GOOGLE ->{
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH ->{
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfter("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                }
            }
        }
    }

    override fun onStart(){
        super.onStart()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount - 1) //keep us at the bottom of the screen
            }

        }
    }


    private fun customMessage(message: String){
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount - 1) //take us down all the way to the last message in order to stay up to date
            }
        }
    }
}



