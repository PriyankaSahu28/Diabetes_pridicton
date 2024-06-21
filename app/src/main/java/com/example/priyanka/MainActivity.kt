package com.example.priyanka

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            val age = findViewById<EditText>(R.id.editage).text.toString()
            val diabetesPedigreeFunction =
                findViewById<EditText>(R.id.editTextDiabetesPedigreeFunction).text.toString()
            val bmi = findViewById<EditText>(R.id.editbmi).text.toString()
            val insulin = findViewById<EditText>(R.id.editInsulin).text.toString()
            val skinThickness = findViewById<EditText>(R.id.editSkinThickness).text.toString()
            val bloodPressure = findViewById<EditText>(R.id.editBloodPressure).text.toString()
            val glucose = findViewById<EditText>(R.id.editGlucose).text.toString()
            val pregnancies = findViewById<EditText>(R.id.editPregnancies).text.toString()
            val result = findViewById<TextView>(R.id.result)
            val progres=findViewById<ProgressBar>(R.id.progress)

            val json = JSONObject()
            json.put("Age", age.toInt())
            json.put("DiabetesPedigreeFunction", diabetesPedigreeFunction.toInt())
            json.put("BMI", bmi.toInt())
            json.put("Insulin", insulin.toInt())
            json.put("SkinThickness", skinThickness.toInt())
            json.put("BloodPressure", bloodPressure.toInt())
            json.put("Glucose", glucose.toInt())
            json.put("Pregnancies", pregnancies.toInt())

            print(
                json
            )
//            Toast.makeText(this@MainActivity, json.toString(), Toast.LENGTH_LONG).show()

progres.visibility= View.VISIBLE
            val url = "https://rphrp198outlook-daibetes.hf.space/predict"
            val jsonObjectRequest =
                JsonObjectRequest(Request.Method.POST, url, json,
                    {
                        progres.visibility= View.GONE
                        var res= it.get("res")
                        if(res==0){
                            result.text="You are Not Diabetic"

                        }else{
                            result.text="You are Diabetic"
                        }
//                        Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_LONG).show()
                    }) { error ->
                    progres.visibility= View.GONE
                    result.text=""
                    Toast.makeText(this@MainActivity,"Some Error Occured", Toast.LENGTH_LONG).show()
                    Log.e("Error", error.toString())
                }

            val queue = Volley.newRequestQueue(this)
            queue.add(jsonObjectRequest)
        }



    }
}