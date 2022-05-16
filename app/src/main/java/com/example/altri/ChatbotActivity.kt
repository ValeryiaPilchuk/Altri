package com.example.altri

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
//import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.altri.adapter.MessagingAdapter
import com.example.altri.data.Message
import com.example.altri.utils.BotResponse
import com.example.altri.utils.Constants
import com.example.altri.utils.Constants.ADD_TASK
import com.example.altri.utils.Time
import kotlinx.android.synthetic.main.activity_chat_bot.*
import kotlinx.coroutines.*
import java.util.*
import java.util.logging.Handler


class ChatbotActivity : Activity(), TextToSpeech.OnInitListener{
    private var tts: TextToSpeech? = null
    private lateinit var adapter: MessagingAdapter
    private val botlist = listOf("Altri")

    val ADD_TASK_INTENT = Intent(this, AddTaskActivity::class.java)
    val SETTINGS_INTENT = Intent(this, SettingsActivity::class.java) // need for functionality for settings page
    val CURRENT_TASK_INTENT = Intent(this, AddTaskActivity::class.java) //maybe needs to be adjusted testing...
    //var timer: Timer? = null
    //timer = new Timer()
    //val background: ConstraintLayout = findViewById(R.id.mainScreen)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot)
        val btnBack = findViewById<ImageButton>(R.id.imageButton)




        tts = TextToSpeech(this, this)
        recyclerView()
        clickEvents()

        customMessage("Hello!, this is ${botlist[0]} speaking, how may I help you today? :)")

        btn_send.setOnClickListener(){
            sendMessage()


        }





        btnBack.setOnClickListener{
            val intent = Intent(this,ChatbotMenu::class.java)
            startActivity(intent)
        }


    }

    private fun speak(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")


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
            adapter.insertMessage(Message(message, Constants.SEND_ID, timeStamp))
            speak(message)

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
                speak(response)
                adapter.insertMessage(Message(response, Constants.RECEIVE_ID, timeStamp))
                rv_messages.scrollToPosition(adapter.itemCount - 1) //take us down all the way to the last message in order to stay up to date

                when(response){
                    Constants.OPEN_GOOGLE ->{
                        delay(2000)
                        setContentView(R.layout.warning_page) //grab warning page to display
                        delay(2000)
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    Constants.OPEN_SEARCH ->{
                        delay(2000)
                        setContentView(R.layout.warning_page) //grab warning page to display
                        delay(2000)
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfter("Search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                    Constants.ADD_TASK ->{ //only takes u to the page without the functionalities same with settings wip
                        delay(2000)
                        setContentView(R.layout.activity_add_task)

                    }

                    Constants.SETTINGS_NAV ->{
                        delay(2000)
                        setContentView(R.layout.activity_settings)
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
                adapter.insertMessage(Message(message, Constants.RECEIVE_ID, timeStamp))
                speak(message)
                rv_messages.scrollToPosition(adapter.itemCount - 1) //take us down all the way to the last message in order to stay up to date
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.getDefault())

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            }
        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }


}




//protected override fun onDestroy() {
// if (mTTS != null) {
//   mTTS.stop()
//  mTTS.shutdown()
//  }
//  super.onDestroy()
//  }
//}