package com.example.barvolume

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

//menandakan kelas kotlin yg meruapakan sebuah activity
class MainActivity : AppCompatActivity(), View.OnClickListener {

    //komponen view yang di deklarasikan secara global
    private lateinit var edtLebar : EditText
    private lateinit var edtTinggi : EditText
    private lateinit var edtPanjang : EditText
    private lateinit var txtHasil : TextView

    //Metode onCreate() metode utama mengatur layout xml
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //kelas main activity yang terhubung dengan activity_main.xml
        setContentView(R.layout.activity_main)

        //memasukkan id komponen di xml ke dalam variabel
        edtLebar = findViewById(R.id.editTextLebar)
        edtTinggi = findViewById(R.id.editTextTinggi)
        edtPanjang = findViewById(R.id.editTextPanjang)
        txtHasil = findViewById(R.id.txtJumlah)

        //event click pada button untuk memunculkan aksi
        btnHitung.setOnClickListener(this)

        //memasukkan nilai seekbar ke dalam TextView
        seekBarBerat.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                txtBerat.text = progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    //berfumgsi menyimpan objek bundle bundles merupakan objek yang bisa disimpan di dalam
    // onSaveInstanceState. Bundles bisa di isi dengan beberapa objek Parcelable di dalamnya s
    // eperti String, int, float, dll.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, txtHasil.text.toString())
    }

    //fungsi orientasi layar otomatis
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onClick(v: View?) {
        if(v!!.id == R.id.btnHitung){

            //mengambil nilai EdiText menggunakan text.toString() ke dalam variabel
            val inputPanjang = edtPanjang.text.toString().trim()
            val inputLebar = edtLebar.text.toString().trim()
            val inputTinggi = edtTinggi.text.toString().trim()
            val inputBerat = seekBarBerat.progress

            var isEmptyFields = false
            var isInvalidDouble = false

            //kontrol flow mengecek isi inputan menggunakan isEmpety()
            if(inputLebar.isEmpty()){
                isEmptyFields = true
                edtLebar.error = "Field ini kosong"
            }
            if(inputTinggi.isEmpty()){
                isEmptyFields = true
                edtTinggi.error = "Field ini kosong"
            }
            if(inputPanjang.isEmpty()){
                isEmptyFields = true
                edtPanjang.error = "Field ini kosong"
            }

            //konversi nilai string EditText ke double
            val panjang = toDouble(inputPanjang)
            val lebar = toDouble(inputLebar)
            val tinggi = toDouble(inputLebar)

            //validasi inputan harus angka
            if(panjang == null){
                isInvalidDouble = true
                edtPanjang.error = "Field ini harus nomor yang falid"
            }
            if(lebar == null){
                isInvalidDouble = true
                edtLebar.error = "Field ini harus nomor yang falid"
            }
            if(tinggi == null){
                isInvalidDouble = true
                edtTinggi.error = "Field ini harus nomor yang falid"
            }

            //menampilkan data ke txtHasil
            if (!isEmptyFields && !isInvalidDouble){
                val volume = panjang as Double * lebar as Double * tinggi as Double * inputBerat
                txtHasil.text = volume.toString()
            }
    }
}
    //fungsi konversi string ke double
    private fun toDouble(str: String): Double? {
        return try {
            str.toDouble()
        }catch (e: NumberFormatException){
            null
        }
    }

    //fungsi menyimpan data hasil dan di teruskan lagi ke txtHasil
    companion object {
        private const val STATE_RESULT = "state_result"
    }
}

