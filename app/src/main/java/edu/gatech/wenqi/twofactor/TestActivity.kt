package edu.gatech.wenqi.twofactor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val ipFromInput: String = intent.extras.getString("ip_address")
        textTestInput.text = ipFromInput

        buttonSend.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, ipFromInput)
            }
            startActivity(intent)
        }
    }
}