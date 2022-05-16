package com.example.altri.utils

import android.content.Intent
import com.codepalace.chatbot.utils.SolveMath
import com.example.altri.AddTaskActivity
import com.example.altri.ChatbotActivity
import com.example.altri.utils.Constants.OPEN_GOOGLE
import com.example.altri.utils.Constants.OPEN_SEARCH
import com.example.altri.utils.Constants.ADD_TASK
import com.example.altri.utils.Constants.SETTINGS_NAV
import com.example.altri.utils.Constants.TASK_NAV


import java.lang.Exception

object BotResponse {

    fun basicResponses(_message: String): String{

        val random = (0..2).random()
        val message = _message.toLowerCase()

        return when {

            //Hello
            message.contains("hello") ||  message.contains("hi") || message.contains("hey")-> {
                when (random){
                    0 -> "Hello There"
                    1 -> "Hi!"
                    2 -> "Hola!"
                    else -> "error"
                }
            }

            //when message contains how are you
            message.contains("how are you") -> {
                when (random){
                    0 -> "I'm doing fine, thanks for asking!"
                    1 -> "I'm feeling kind of hungry..."
                    2 -> "I'm feeling fantastic! How about you?"
                    else -> "error"
                }
            }

            message.contains("flip") && message.contains("coin") ->{
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }

            //solve math
            message.contains("solve") -> {
                val equation: String? = message.substringAfter("solve") //search for this term in string and remove everything before it and get everything after

                return try{
                    val answer = SolveMath.solveMath(equation ?: "0")
                    answer.toString()

                } catch(e: Exception){
                    "Sorry I cannot solve that"
                }
            }

            message.contains("time") && message.contains("?") ->{ //retrieves the current time
                Time.timeStamp()
            }

            //open google
            message.contains("open") && message.contains("google") ->{
                OPEN_GOOGLE
            }
            // uses search feature
            message.contains("search") ->{
                OPEN_SEARCH
            }

            message.contains("add") && message.contains("task")->{
                ADD_TASK
            }

            (message.contains("show") || message.contains("what") ) && message.contains("task") ->{
                TASK_NAV
            }

            message.contains("settings") ->{
                SETTINGS_NAV
            }

            else -> {
                when (random){ // if the user gives a response it doesnt know
                    0 -> "I don't understand..."
                    1 -> "You're a bit confusing..."
                    2 -> "Try asking me something different."
                    else -> "error"
                }

            }
        }
    }
}