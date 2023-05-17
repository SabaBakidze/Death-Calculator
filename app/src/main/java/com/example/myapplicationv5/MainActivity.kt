package com.example.myapplicationv5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.util.*

const val FEMALE_LIFE_EXPECTANCY = 78.5
const val MALE_LIFE_EXPECTANCY = 69.9

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generate()
    }

    private fun generate(){
        val daysLivedTxt: TextView = findViewById(R.id.daysLived_txt)
        val daysToLiveTxt: TextView = findViewById(R.id.daysToLive_txt)
        val yearsToLiveTxt: TextView = findViewById(R.id.yearsToLive_txt)
        val percentageTxt: TextView = findViewById(R.id.percentage_txt)
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        var radioButton: RadioButton

        val datePicker: DatePicker = findViewById(R.id.datePicker1)
        datePicker.init(2000, 9, 27, null)
        val button: Button = findViewById(R.id.generate_btn)
        var daysLived = 0
        var daysToLive = 0.0
        var yearsToLive = 0
        var strFormat: String

        button.setOnClickListener(){
            daysLived = calculatePast(datePicker.dayOfMonth, datePicker.month, datePicker.year)
            daysLivedTxt.text = "You have lived $daysLived days"

            radioButton = findViewById(radioGroup.checkedRadioButtonId)
            daysToLive = calculateFuture(datePicker.year, radioButton.text.toString().lowercase())
            daysToLiveTxt.text = daysLeftToLive(daysToLive, daysLived)
            //daysToLiveTxt.text = "You have ${daysLeftToLive(daysToLive, daysLived)} days left to live"

            yearsToLive = ( (daysToLive - daysLived) / 365).toInt()
            yearsToLiveTxt.text = yearsLeftToLive(yearsToLive, daysToLive, daysLived)
            //yearsToLiveTxt.text = "~$yearsToLive years and ${((daysToLive - daysLived) % 365).toInt()} days"

            strFormat = String.format("%.2f", calculatePercentage(daysLived, daysToLive))
            percentageTxt.text = "That is $strFormat % of your life"
        }

    }

    private fun calculatePercentage(daysLived: Int, daysToLive: Double): Double{
        return ((daysLived * 100) / daysToLive)
    }

    private fun calculatePast(day: Int, month: Int, year: Int): Int{
        val textView: TextView = findViewById(R.id.daysLived_txt)
        var daysLived = 0
        val currentYear = Calendar.getInstance().get(Calendar.YEAR);
        val currentDayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)

        when (month){
            0 -> daysLived += (31 - day) + 334 //January
            1 -> daysLived += (28 - day) + 306 //February
            2 -> daysLived += (31 - day) + 275 //March
            3 -> daysLived += (30 - day) + 245 //April
            4 -> daysLived += (31 - day) + 214 //May
            5 -> daysLived += (30 - day) + 184 //June
            6 -> daysLived += (31 - day) + 153 //July
            7 -> daysLived += (31 - day) + 122 //August
            8 -> daysLived += (30 - day) + 92 //September
            9 -> daysLived += (31 - day) + 61 //October
            10 -> daysLived += (30 - day) + 31 //November
            11 -> daysLived += (31 - day) + 0 //December
        }

        daysLived += ( (currentYear - year - 1) * 365) + currentDayOfYear

        return daysLived
    }

    private fun calculateFuture(year: Int, gender: String): Double{
        val currentDayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        var daysToLive = 0.0

        if (gender == "male"){
            daysToLive = (365 - currentDayOfYear) + (365 * MALE_LIFE_EXPECTANCY)
        }else if (gender == "female"){
            daysToLive = (365 - currentDayOfYear) + (365 * FEMALE_LIFE_EXPECTANCY)
        }

        return daysToLive
    }

    private fun daysLeftToLive(daysToLive: Double, daysLived: Int):String{
        var daysLeftToLive = daysToLive - daysLived

        return if (daysLeftToLive.toInt() == 0)
            "Today is the day..."
        else if(daysLeftToLive < 0)
            "You have lived ${daysLeftToLive.toInt()*(-1)} more days than an average human !"
        else
            "You have ${daysLeftToLive.toInt()} days left to live"
    }

    private fun yearsLeftToLive(yearsToLive: Int, daysToLive: Double, daysLived: Int): String{
        var daysLeftToLive = daysToLive - daysLived

        return if(yearsToLive < 0 || daysLeftToLive < 0)
            "~${yearsToLive*(-1)} years and ${(((daysToLive - daysLived) % 365)*(-1)).toInt()} days"
        else
            "~$yearsToLive years and ${((daysToLive - daysLived) % 365).toInt()} days"

    }

}