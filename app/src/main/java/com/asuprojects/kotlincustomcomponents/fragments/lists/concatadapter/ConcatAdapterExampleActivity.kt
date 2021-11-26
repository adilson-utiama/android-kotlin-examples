package com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.databinding.ActivityConcatAdapterExampleBinding
import com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.fragments.RecipeHorizontalFragment
import com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.fragments.RecipeVerticalFragment

class ConcatAdapterExampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConcatAdapterExampleBinding

    //orientation: true = Horizontal, false = Vertical
    private var orientationH = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcatAdapterExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val horizontal = RecipeHorizontalFragment()
        val vertical = RecipeVerticalFragment()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.frameLayoutConcatAdapter2, horizontal)
            commit()
        }

        binding.btnOrientation.setOnClickListener {
            orientationH = !orientationH
            if(orientationH){
                supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(R.anim.fast_fade_in, R.anim.fast_fade_out)
                    replace(R.id.frameLayoutConcatAdapter2, horizontal)
                    commit()
                }
                binding.btnOrientation.setImageResource(R.drawable.ic_swap_vert)
            }else{
                supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(R.anim.fast_fade_in, R.anim.fast_fade_out)
                    replace(R.id.frameLayoutConcatAdapter2, vertical)
                    commit()
                }
                binding.btnOrientation.setImageResource(R.drawable.ic_swap_horiz)
            }
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}