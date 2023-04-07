package com.example.tipcalculator

import android.animation.ArgbEvaluator
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.tipcalculator.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
private const val Initial_tip_percent = 15
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercentLabel:TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTipGrade:TextView

    private fun computeTipAndTotal() {
        if(etBaseAmount.text.isEmpty()){
            tvTipAmount.text = ""
            tvTotalAmount.text = ""
            return
        }
        var base = etBaseAmount.text.toString().toDouble()
        var total: Double
        var tip: Double = base * seekBarTip.progress / 100
        total = tip + base
        tvTipAmount.text = "%.2f".format(tip)
        tvTotalAmount.text = "%.2f".format(total)
    }
    private fun computeTipGrade(progress:Int){
        var grade = when(progress){
            in 0..5 -> "Poor"
            in 6 ..10 -> "Acceptable"
            in 11 .. 15 -> "Good"
            in 16 .. 20 -> "Very Good"

            else -> "Excellent"
        }
        tvTipGrade.text = grade
        var color = ArgbEvaluator().evaluate(
            progress.toFloat()/seekBarTip.max,
            ContextCompat.getColor(this,R.color.color_worst_tip),
            ContextCompat.getColor(this,R.color.color_best_tip)
        )as Int
        tvTipGrade.setTextColor(color)

    }
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipPercentLabel = findViewById(R.id.tvTipPercentLabel)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        seekBarTip.progress = Initial_tip_percent
        tvTipPercentLabel.text = "$Initial_tip_percent%"
        tvTipGrade = findViewById(R.id.tvTipGrade)
        computeTipGrade(Initial_tip_percent)

        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG,"on Progress Changed $p1")
                tvTipPercentLabel.text = "$p1%"
                computeTipAndTotal()
                computeTipGrade(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })


        etBaseAmount.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG,"after text changed $p0")
                computeTipAndTotal()

            }

        })

    }
}