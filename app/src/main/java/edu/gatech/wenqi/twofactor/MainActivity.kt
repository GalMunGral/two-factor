package edu.gatech.wenqi.twofactor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonTest.setOnClickListener {
            Log.i("test", "Hello")
            val input: String = inputTest.text.toString()
            val intent = Intent(this, TestActivity::class.java).apply {
                putExtra("ip_address", input)
            }
            startActivity(intent)
        }
    }

}
