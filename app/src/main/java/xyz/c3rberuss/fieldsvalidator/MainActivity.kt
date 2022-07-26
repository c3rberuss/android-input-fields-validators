package xyz.c3rberuss.fieldsvalidator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.c3rberuss.fieldsvalidator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}