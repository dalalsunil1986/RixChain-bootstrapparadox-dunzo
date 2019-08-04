package com.dunzo


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.fragment_view.*


class ViewD : Fragment(), AdapterView.OnItemSelectedListener {


//
//    var categories = arrayOf("Groceries", "Electronics", "Food")
//
//     var spinner: Spinner? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view, container, false)
    }

//    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
////
//        spinner = this.spinner_category
//        spinner!!.onItemSelectedListener
//
//        // Create an ArrayAdapter using a simple spinner layout and languages array
//        val aa = ArrayAdapter<String>(activity?.applicationContext, android.R.layout.simple_spinner_item, categories)
//        // Set layout to use when the list of choices appear
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        // Set Adapter to Spinner
//        spinner!!.adapter = aa
//
//
//    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
//        println("Selected : "+categories[position])
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

}
