package com.example.hangman

import android.R.attr.button
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    var word = String()
    var charsPublic = String()
    var numberOfTries = 0
    var guestsLeft = 8

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val array = resources.getStringArray(R.array.library)
        var textV = findViewById<TextView>(R.id.textView)
        var index = Random.nextInt(array.size)
        word = array[index]
        textV.text = word
        var chars = CharArray(word.length) { '_' }
        printCurrentState(chars)
        charsPublic = String(chars)
    }

    fun getText(view: View) {
        var butt: Button = findViewById((view as Button).id)
        butt.isEnabled = false
        butt.isClickable = false

        butt.setBackgroundResource(R.color.dark_purple_gray)
        var textV = findViewById<TextView>(R.id.currentState)
        findLetterInWord(butt.text.toString())
    }

    fun printCurrentState(chars: CharArray) {
        var currentStateTextView = findViewById<TextView>(R.id.currentState)
        var show = CharArray(2 * chars.size)
        for ( i in chars.indices){
            show[ 2 * i ] = chars [i]
            show[ 2 * i + 1 ] = ' '
        }
        currentStateTextView.text = String(show)
    }

    fun victory() {
        var indexCheck = findViewById<TextView>(R.id.textView3)
        indexCheck.text = getString(R.string.YouWon)
        var base = findViewById<ConstraintLayout>(R.id.baseview)
        base.setBackgroundResource(R.color.teal_700)
        blockButtons()
    }


    fun defeat(){
        charsPublic = word
        printCurrentState(charsPublic.toCharArray())
        var indexCheck = findViewById<TextView>(R.id.textView3)
        indexCheck.text = getString(R.string.Looser)
        var base = findViewById<ConstraintLayout>(R.id.baseview)
        base.setBackgroundResource(R.color.pink)
        blockButtons()
    }

    fun blockButtons() {
        val allButtons: ArrayList<View> = (findViewById<View>(R.id.baseview) as ConstraintLayout).touchables
        for (butt in allButtons) {
            butt.isEnabled = false
            butt.isClickable = false
        }
        var buttReplay: Button = findViewById(R.id.button9)
        buttReplay.isEnabled = true
        buttReplay.isClickable = true
    }

    fun findLetterInWord(letter: String) {
        var textV = findViewById<TextView>(R.id.currentState)
        var index = -1
        var openedLettersNow = 0
        do {
            index = word.lowercase(Locale.ROOT).indexOf(letter.lowercase(Locale.ROOT), startIndex = index + 1)
            val tmp = charsPublic.toCharArray()
            if (index != -1) {
                tmp[index] = letter[0]
                charsPublic = String(tmp)
                openedLettersNow++
            }
        } while (index != -1)

        if (openedLettersNow == 0) {
            numberOfTries++
            guestsLeft--
            switchImage()
        } else if (!charsPublic.contains('_')) {
            victory()
        }

        if (guestsLeft == 0){
            defeat()
        }

        textV.text = charsPublic
        printCurrentState(charsPublic.toCharArray())
    }

    fun switchImage(){
        var image = findViewById<ImageView>(R.id.imageView5)
        when (numberOfTries){
            1 -> image.setBackgroundResource(R.drawable.hangman1)
            2 -> image.setBackgroundResource(R.drawable.hangman2)
            3 -> image.setBackgroundResource(R.drawable.hangman3)
            4 -> image.setBackgroundResource(R.drawable.hangman4)
            5 -> image.setBackgroundResource(R.drawable.hangman5)
            6 -> image.setBackgroundResource(R.drawable.hangman6)
            7 -> image.setBackgroundResource(R.drawable.hangman7)
            8 -> image.setBackgroundResource(R.drawable.hangman8)
        }
    }

    fun restart() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        finishAffinity()
    }

    fun replay(view: View) {
        restart()
    }
}