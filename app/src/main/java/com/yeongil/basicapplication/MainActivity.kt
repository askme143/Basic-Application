package com.yeongil.basicapplication

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.yeongil.basicapplication.databinding.ActivityMainBinding
import com.yeongil.basicapplication.ui.phoneBook.PhoneBookFragment
import com.yeongil.basicapplication.viewModel.PhoneBookViewModel
import com.yeongil.basicapplication.viewModelFactory.PhoneBookViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val phoneBookViewModel by viewModels<PhoneBookViewModel> {
        PhoneBookViewModelFactory(
            application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.navigation.setOnNavigationItemSelectedListener { replaceFragment(it.itemId) }
        binding.navigation.selectedItemId = R.id.phone_book
    }

    private fun replaceFragment(itemId: Int): Boolean {
        val fragment = when (itemId) {
            R.id.phone_book -> PhoneBookFragment()
            else -> return false
        }

        val fragmentTx = supportFragmentManager.beginTransaction()
        fragmentTx.replace(R.id.frame_layout, fragment)
        fragmentTx.commit()

        phoneBookViewModel.permissions.observe(this, {
            val content = it.getContentIfNotHandled()
            if (content != null) { requestPermissions(content) }
        })

        return true
    }

    private fun requestPermissions(permissions: List<String>) {
        ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 1)
    }
}