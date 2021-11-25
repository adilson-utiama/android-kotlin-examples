package com.asuprojects.kotlincustomcomponents.prototypes.api

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.activity_palette_a_p_i.*

class PaletteAPIActivity : AppCompatActivity() {

    companion object {
        const val PICK_IMAGE = 1
    }

    private lateinit var adapter: PaletteSwatchesdAdapter

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palette_a_p_i)

        supportActionBar?.apply {
            title = "Palette API"
            setDisplayHomeAsUpEnabled(true)
        }

        palette_screen_pick_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }

        val bitmap = palette_screen_image_view.drawable.toBitmap()
        //val bitmap = BitmapFactory.decodeResource(resources, R.drawable.paisagem_praia)
        val swatches = generateColorsFromBitmap(bitmap)
        adapter = PaletteSwatchesdAdapter(swatches)
        recycler_palette_swatches.adapter = adapter

    }

    private fun generateColorsFromBitmap(bitmap: Bitmap) : MutableList<MySwatch> {
        val swatches = mutableListOf<MySwatch>()
        val builder = Palette.Builder(bitmap)
        //val generated = builder.generate()
        val palette = builder.generate()
            palette.dominantSwatch?.apply {
                swatches.add(MySwatch(
                    "Dominant Swatch",
                    rgb, titleTextColor, bodyTextColor
                ))
            }
            palette.darkVibrantSwatch?.apply {
                swatches.add(MySwatch(
                    "Dark Vibrant Swatch",
                    rgb, titleTextColor, bodyTextColor
                ))
            }
            palette.lightVibrantSwatch?.apply {
                swatches.add(MySwatch(
                    "Light Vibrant Swatch",
                    rgb, titleTextColor, bodyTextColor
                ))
            }
            palette.vibrantSwatch?.apply {
                swatches.add(MySwatch(
                    "Vibrant Swatch",
                    rgb, titleTextColor, bodyTextColor
                ))
            }

            //Muted Colors
            palette.mutedSwatch?.apply {
                swatches.add(MySwatch(
                    "Muted Swatch",
                    rgb, titleTextColor, bodyTextColor
                ))
            }
            palette.darkMutedSwatch?.apply {
                swatches.add(MySwatch(
                    "Dark Muted Swatch",
                    rgb, titleTextColor, bodyTextColor
                ))
            }
            palette.lightMutedSwatch?.apply {
                swatches.add(MySwatch(
                    "Light Muted Swatch",
                    rgb, titleTextColor, bodyTextColor
                ))
            }

        Log.i("TESTE", ">>> Swatches : ${swatches.size}")
        return swatches
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == RESULT_OK){
            if(requestCode == PICK_IMAGE){
                data?.apply {
                    val imageUri = this.data
                    Log.i("TESTE", ">>> Data Result -> $imageUri")

                    palette_screen_image_view.setImageURI(imageUri)
                    val toBitmap = palette_screen_image_view.drawable.toBitmap()
                    val newSwatches = generateColorsFromBitmap(toBitmap)
                    adapter.setNewList(newSwatches)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    data class MySwatch(
        val label: String,
        val swatchColor: Int,
        val titleTextColor: Int,
        val bodyTextColor: Int
    )

    class PaletteSwatchesdAdapter(var swatches: MutableList<MySwatch>)
        : RecyclerView.Adapter<SwatchesViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwatchesViewHolder {
            val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_palette_swatches, parent , false)
            return SwatchesViewHolder(inflatedView)
        }

        override fun onBindViewHolder(holder: SwatchesViewHolder, position: Int) {
            val swatch = swatches[position]
            holder.label.text = swatch.label
            holder.label.setTextColor(swatch.titleTextColor)
            holder.container.setBackgroundColor(swatch.swatchColor)
            holder.titleText.setTextColor(swatch.titleTextColor)
            holder.bodyText.setTextColor(swatch.bodyTextColor)
        }

        override fun getItemCount(): Int {
            return swatches.size
        }

        fun setNewList(newList: MutableList<MySwatch>){
            swatches = newList
            notifyDataSetChanged()
        }
    }

    class SwatchesViewHolder(view: View): RecyclerView.ViewHolder(view){
        val container: LinearLayoutCompat = view.findViewById(R.id.vh_swatch_item_container)
        val label: AppCompatTextView = view.findViewById(R.id.vh_swatch_label)
        val titleText: AppCompatTextView = view.findViewById(R.id.vh_swatch_title_text)
        val bodyText: AppCompatTextView = view.findViewById(R.id.vh_swatch_body_text)
    }
}