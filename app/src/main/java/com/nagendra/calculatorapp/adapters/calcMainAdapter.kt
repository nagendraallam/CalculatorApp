package com.nagendra.calculatorapp.adapters

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.nagendra.calculatorapp.R


class calcMainAdapter (private val context: Context,private val items : Array<String>,private val metrics: Int, private val colorList: Array<Int>) :
    BaseAdapter() {

    private var layout : LayoutInflater? = null
    private lateinit var text : TextView;
    private lateinit var backgroundButton : ImageView;

    override fun getCount(): Int {

        return items.size;
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertview = convertView
        if (layout == null){
            layout = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null){
            convertview = layout!!.inflate(R.layout.calculator_button,null)

            convertview.minimumHeight = metrics/5
        }
        text = convertview!!.findViewById(R.id.tagID)
        backgroundButton = convertview!!.findViewById(R.id.background)
        text.setText(items[position])
        backgroundButton.setBackgroundTintList(ContextCompat.getColorStateList(context, colorList[position]));


        return convertview
    }
}
