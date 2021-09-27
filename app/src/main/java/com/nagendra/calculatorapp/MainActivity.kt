package com.nagendra.calculatorapp

import android.app.ActionBar
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.*
import com.nagendra.calculatorapp.adapters.calcMainAdapter

class MainActivity : AppCompatActivity() {

    lateinit var gridLayout : GridView
    lateinit var textview : TextView
    lateinit var history : TextView
    var number1 = "";
    var number2 = "";
    var operation = "";
    var lastPos = 0;
    var setFirstNumber = false;
    private var operationList = arrayOf("AC","a²","%","/","7","8","9","*","4","5","6","-","1","2","3","+","0",".","√","=")
    private var colorList = arrayOf(R.color.grey_button,R.color.grey_button,R.color.grey_button,R.color.ios_orange,R.color.darkgrey_button,R.color.darkgrey_button,R.color.darkgrey_button
        ,R.color.ios_orange,R.color.darkgrey_button,R.color.darkgrey_button,R.color.darkgrey_button,R.color.ios_orange,R.color.darkgrey_button,R.color.darkgrey_button,R.color.darkgrey_button,R.color.ios_orange,
        R.color.darkgrey_button,R.color.grey_button,R.color.grey_button,R.color.ios_orange)

    private var constants = arrayOf(R.color.grey_button,R.color.grey_button,R.color.grey_button,R.color.ios_orange,R.color.darkgrey_button,R.color.darkgrey_button,R.color.darkgrey_button
        ,R.color.ios_orange,R.color.darkgrey_button,R.color.darkgrey_button,R.color.darkgrey_button,R.color.ios_orange,R.color.darkgrey_button,R.color.darkgrey_button,R.color.darkgrey_button,R.color.ios_orange,
        R.color.darkgrey_button,R.color.grey_button,R.color.grey_button,R.color.ios_orange)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.calculator_main)
        gridLayout = findViewById(R.id.gridid)
        textview = findViewById(R.id.texty)
        history = findViewById(R.id.text1)

        gridLayout.measure(0,0)
        val mainAdapter = calcMainAdapter(this ,operationList,gridLayout.height,colorList)
        gridLayout.adapter = mainAdapter

        gridLayout.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->

            colorList[lastPos] = constants[lastPos]
            colorList[position] = R.color.selected
            mainAdapter.notifyDataSetChanged()
            lastPos = position

            //CHECKER FOR 1 NUMBER INPUT
            if (!setFirstNumber){
                if (position in intArrayOf(0,1,2,3,7,11,15,18,19)){
                    if (position == 0){
                        reset()
                    }else if(position == 19){
                        textview.setText(number1)
                    }
                    else if(position in intArrayOf(1,2,18)){
                        if(position == 1){
                            number1 = (number1.toDouble()*number1.toDouble()).toString()
                        }
                        else if(position==2){
                            number1 = (number1.toDouble()/100).toString()
                        }else if (position==18){
                            number1 = Math.sqrt(number1.toDouble()).toString()
                        }
                        textview.setText(number1)
                    }
                    else{
                        setFirstNumber = true;
                        operation = operationList[position]
                        historyAdder("reset")
                        historyAdder(number1+" "+ operation+" ")
                    }
                }else{
                        number1 += operationList[position]
                        textview.setText(number1)

                }
                //CHECKER FOR 2 NUMBER INPUT
            }else{
                if (position in intArrayOf(0,1,2,3,7,11,15,18,19)){
                    if(position == 0){
                        reset()
                    }else if (position == 19){
                        solveCalculation()
                        setFirstNumber =false
                        number2 = ""
                    }else if(position in intArrayOf(1,2,18)){
                        if(number2 != ""){
                            solveCalculation()
                            if(position == 1){
                                number2 = (textview.text.toString().toDouble()*textview.text.toString().toDouble()).toString()
                            }
                            else if(position==2){
                                number2 = (textview.text.toString().toDouble()/100).toString()
                            }else if (position==18){
                                number2 = Math.sqrt(textview.text.toString().toDouble()).toString()
                            }
                            number1 = number2
                            number2 = ""
                            setFirstNumber = false
                            textview.setText(number1)
                        }

                    }
                    else {
                        if(number2 != ""){
                            //CHECKING FOR OPERATIONS
                            solveCalculation()
                            operation = operationList[position]
                            number2=""
                            setFirstNumber = true

                        }
                    }
                }else{
                    // ADDING STRING OF NUMBER
                    number2 += operationList[position]
                    textview.setText(number2)

                }
            }
        }

    }

    override fun onResume() {
        super.onResume()

    }
    fun solveCalculation(){
        val store1 = number1.toDouble()
        if (operation == "+"){
            number1 = ((store1 + number2.toDouble()).toString())
            textview.setText(number1)
        }else if(operation == "-"){
            number1 = ((store1 - number2.toDouble()).toString())
            textview.setText(number1)
        }else if(operation == "*"){
            number1 = ((store1 * number2.toDouble()).toString())
            textview.setText(number1)
        }else if(operation == "/"){
            number1 = ((store1 / number2.toDouble()).toString())
            textview.setText(number1)
        }
        historyAdder(number2 + " = ")
    }

    fun reset(){
        number2 = ""
        number1 = ""
        operation = ""
        setFirstNumber = false
        textview.setText("0")
        historyAdder("reset")
    }

    fun historyAdder(Char : String) {
        if(Char != "reset")
        history.setText(history.text.toString() + Char)
        else
            history.setText("")

    }
}