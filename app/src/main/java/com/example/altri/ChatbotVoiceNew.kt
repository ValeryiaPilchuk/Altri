package com.example.altri

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.text.Editable
import android.widget.Toast
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import kotlinx.android.synthetic.main.activity_chat_voice_2.*
import java.util.*
import android.net.Uri
import com.example.altri.data.Message
import android.util.Log
import androidx.constraintlayout.motion.utils.Oscillator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.altri.utils.BotResponse
import com.example.altri.utils.Constants.OPEN_GOOGLE
import com.example.altri.utils.Constants.OPEN_SEARCH
import com.example.altri.utils.Constants.RECEIVE_ID
import com.example.altri.utils.Constants.SEND_ID
import com.example.altri.utils.Time
import kotlinx.android.synthetic.main.activity_chat_bot.*
import kotlinx.android.synthetic.main.activity_chat_voice_2.et_message
import kotlinx.android.synthetic.main.activity_chat_voice_2.rv_messages
import kotlinx.coroutines.*



class ChatbotVoiceNew : Activity() {
    private lateinit var adapter: MessagingAdapter
    private val botlist = listOf("Altri")
    private val RQ_SPEECH_REC = 102 //request code for later

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_voice_2) // i want the screen for speech 2 txt

        recyclerView()
        clickEvents()

        customMessage("Hello!, this is ${botlist[0]} speaking, how may I help you today? :)")

        btn_speak.setOnClickListener{

            askSpeechInput()
            sendMessage()


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK){
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            et_message.text = result?.get(0).toString()
        }

    }



    private fun askSpeechInput(){
        if(!SpeechRecognizer.isRecognitionAvailable((this))){
            Toast.makeText(this,"Speech recognition is not available", Toast.LENGTH_SHORT).show() //checks if phone can use speech
        } else{
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()) //get system language allow user to speak in lang
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!")
            startActivityForResult(i,RQ_SPEECH_REC)
        }
    }

    private fun clickEvents() {
       // btn_speak.setOnClickListener(){
          //  sendMessage()
      //  }
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