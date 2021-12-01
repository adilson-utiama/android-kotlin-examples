package com.asuprojects.kotlincustomcomponents.prototypes.expandmenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.databinding.ActivityExpandMenuButtonBinding

class ExpandMenuButtonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpandMenuButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpandMenuButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnToggleHorizontal.setOnClickListener {

            if(binding.expandableMenuHorizontal.isExpanded){
                binding.btnToggleHorizontal.animate().rotation(0F).duration = 200
            }else{
                binding.btnToggleHorizontal.animate().rotation(45F).duration = 200
            }

            binding.expandableMenuHorizontal.toggle()

        }


        binding.btnToggleHorizontal2.setOnClickListener {
            if(binding.expandableMenuHorizontal2.isExpanded){
                binding.btnToggleHorizontal2.animate().rotation(0F).duration = 200
            }else{
                binding.btnToggleHorizontal2.animate().rotation(45F).duration = 200
            }

            binding.expandableMenuHorizontal2.toggle()
        }

        binding.btnToggleHorizontal3.setOnClickListener {
            if(binding.expandableMenuHorizontal3.isExpanded){
                binding.btnToggleHorizontal3.animate().rotation(0F).duration = 200
            }else{
                binding.btnToggleHorizontal3.animate().rotation(45F).duration = 200
            }

            binding.expandableMenuHorizontal3.toggle()
        }


        binding.btnToggleVertical.setOnClickListener {

            if(binding.expandableMenuVertical.isExpanded){
                binding.btnToggleVertical.animate().rotation(0F).duration = 200
            }else{
                binding.btnToggleVertical.animate().rotation(45F).duration = 200
            }

            binding.expandableMenuVertical.toggle()

        }

        binding.btnToggleVertical2.setOnClickListener {
            if(binding.expandableMenuVertical2.isExpanded){
                binding.btnToggleVertical2.animate().rotation(0F).duration = 200
            }else{
                binding.btnToggleVertical2.animate().rotation(45F).duration = 200
            }

            binding.expandableMenuVertical2.toggle()
        }

        binding.btnToggleVertical3.setOnClickListener {
            if(binding.expandableMenuVertical3.isExpanded){
                binding.btnToggleVertical3.animate().rotation(0F).duration = 200
            }else{
                binding.btnToggleVertical3.animate().rotation(45F).duration = 200
            }

            binding.expandableMenuVertical3.toggle()
        }

    }
}