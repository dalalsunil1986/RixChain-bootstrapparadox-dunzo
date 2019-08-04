package com.dunzo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.content.Intent



class Tab : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_call -> {
//                val fragment = MainActivity()
//                supportFragmentManager.beginTransaction().replace(R.id.container, Fragment(), fragment.javaClass.simpleName)
//                        .commit()
                println("home page")
                openFragment(Main())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
//                val fragment = ViewData()
//                supportFragmentManager.beginTransaction().replace(R.id.container, Fragment(), fragment.javaClass.simpleName)
//                        .commit()
                println("data page")
                openFragment(ViewD())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)


            val fragment = Main()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                    .commit()

//        val fragment = MainActivity()
//        addFragment(fragment)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
        finish()
        System.exit(0)
    }

}



