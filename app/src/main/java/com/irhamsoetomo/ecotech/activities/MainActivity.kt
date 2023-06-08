package com.irhamsoetomo.ecotech.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.irhamsoetomo.ecotech.R
import com.irhamsoetomo.ecotech.databinding.ActivityMainBinding
import com.irhamsoetomo.ecotech.fragment.FragmentHome
import com.irhamsoetomo.ecotech.fragment.FragmentStruk

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        switchFragment(FragmentHome())

        binding.fab.setOnClickListener {
            showDialogFab();
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> switchFragment(FragmentHome())
                R.id.snippet -> switchFragment(FragmentStruk())
                else ->{

                }
            }
            true
        }
    }

    private fun showDialogFab() {
        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_fab)
        customDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        customDialog.window?.setBackgroundDrawableResource(R.color.transparent)
        customDialog.setCancelable(true)
        val kamera = customDialog.findViewById<LinearLayout>(R.id.kamera)
        val galeri = customDialog.findViewById<LinearLayout>(R.id.galeri)
        kamera.setOnClickListener {
            // Buka Kamera
        }
        galeri.setOnClickListener {
            // Buka Galeri
            startActivity(Intent(Intent(this, ResultActivity::class.java)))
            customDialog.hide()
        }
        customDialog.show()
    }

    private fun switchFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_contaimer, fragment)
        fragmentTransaction.commit()
    }
}