package com.asuprojects.kotlincustomcomponents.fragments.transitions.sharedfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.viewholder_weapon.view.*

class WeaponItemAdapter(val weapons: MutableList<Weapon>)
    : RecyclerView.Adapter<WeaponItemAdapter.WeaponViewHolder>(){

    private lateinit var onClickWeapon: OnClickWeapon

    fun setOnClickWeapon(listener: OnClickWeapon){
        this.onClickWeapon = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeaponViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_weapon, parent, false)
        return WeaponViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: WeaponViewHolder, position: Int) {
        val weapon = weapons[position]
        holder.image.setImageResource(weapon.resourdId)
        holder.image.setOnClickListener {
            onClickWeapon.onSelectWeapon(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return weapons.size
    }

    class WeaponViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image: AppCompatImageView = view.findViewById(R.id.vh_weapon_image)
    }

    interface OnClickWeapon {
        fun onSelectWeapon(view: View, position: Int)
    }
}
