package com.example.pexeso

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import com.example.pexeso.R.drawable.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    fun start(){
        val images: MutableList<Int> =
            mutableListOf(o1, o2, o3, o4, o5, o6, o1, o2, o3, o4, o5, o6)

        val buttons = arrayOf(button1, button2, button3, button4, button5, button6, button7, button8,
            button9, button10, button11, button12)

        var click = 0
        var turned = false
        var lastClicked = -1
        var counter = 0
        var correct = 0
        var score = 0
        buttonreset.setOnClickListener{
            timer.base = SystemClock.elapsedRealtime()
            start()
        }

        timer.start()
        images.shuffle()
        textView.text = "Attempts: 0"
        for(i in 0..11){
            buttons[i].setBackgroundResource(o0)
            buttons[i].text = "cardBack"
            buttons[i].textSize = 0.0F
            buttons[i].setOnClickListener {


                if (buttons[i].text == "cardBack" && !turned) {
                    buttons[i].setBackgroundResource(images[i])
                    buttons[i].setText(images[i])
                    if (click == 0) {
                        lastClicked = i
                    }
                    click++
                } else if (buttons[i].text !in "cardBack") {
                    buttons[i].setBackgroundResource(o0)
                    buttons[i].text = "cardBack"
                    click--
                }

                if (click == 2 ) {
                    turned = true
                    if (buttons[i].text == buttons[lastClicked].text) {
                        buttons[i].isClickable = false
                        buttons[lastClicked].isClickable = false
                        turned = false
                        click = 0
                        counter++
                        correct++
                        textView.text = "Attempts: " + counter.toString()
                    }
                } else if (click == 0) {
                    turned = false
                    counter++
                    textView.text = "Attempts: " + counter.toString()
                }

                if(correct == 6){
                    timer.stop()

                    var times = timer.text.split(":")
                    var time = times[0].toInt() * 60 + times[1].toInt()

                    score = 1000 - time - counter

                    if(score < 0){

                        score = 0
                    }
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Congratulations!")
                    builder.setMessage("Attempts: " + counter.toString() + "\nTime: " + timer.text + "\nScore: " + score.toString())
                    builder.setIcon(result)
                    builder.setNeutralButton("Restart") { dialog, which ->
                        timer.base = SystemClock.elapsedRealtime()
                        start()
                    }
                    builder.setPositiveButton("Cancel") { dialog, which ->
                    }
                    builder.show()

                }

            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       start()

    }
}