package com.example.inputcontrols

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    lateinit var phoneCategory: Spinner
    lateinit var addDate: ImageView
    lateinit var dateOfBirth: EditText
    var year = 0
    var month = 0
    var day = 0
    val categories = arrayListOf(
        "Home", "Work", "Business"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        phoneCategory = findViewById(R.id.phoneCategory)
        addDate = findViewById(R.id.addDate)
        dateOfBirth = findViewById(R.id.dob)

        val adapter = ArrayAdapter(
            this@MainActivity, android.R.layout.simple_spinner_item, categories
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        phoneCategory.adapter = adapter
        phoneCategory.onItemSelectedListener = this@MainActivity

        addDate.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.addDate -> {
                val calendar = Calendar.getInstance()
                year = calendar.get(Calendar.YEAR)
                month = calendar.get(Calendar.MONTH)
                day = calendar.get(Calendar.DAY_OF_MONTH)
                DatePickerDialog(
                    this@MainActivity,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        dateOfBirth.setText("${year} - ${month} - ${dayOfMonth}")
                    },
                    year,
                    month,
                    day
                ).show()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(this, "Phone category ${categories[position]}", Toast.LENGTH_SHORT).show()
    }
}
