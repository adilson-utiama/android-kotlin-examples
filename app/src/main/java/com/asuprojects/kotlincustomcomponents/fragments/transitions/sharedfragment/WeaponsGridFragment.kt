package com.asuprojects.kotlincustomcomponents.fragments.transitions.sharedfragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Explode
import androidx.transition.Fade
import androidx.transition.Slide
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.transitions.sharedfragment.WeaponDetailFragment.Companion.WEAPON_ITEM_ARG
import kotlinx.android.synthetic.main.fragment_weapons_grid.*

class WeaponsGridFragment : Fragment() {

    private val OPTION_FADE = 1
    private val OPTION_SLIDE = 2
    private val OPTION_EXPLODE = 3

    private var weapons = mutableListOf<Weapon>()
    private lateinit var adapter: WeaponItemAdapter

    private var transitionType = OPTION_FADE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        weapons = getWeapons()
        adapter = WeaponItemAdapter(weapons)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weapons_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radio_group_transitions.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radio_button_fade -> {
                    transitionType = OPTION_FADE
                }
                R.id.radio_button_slide -> {
                    transitionType = OPTION_SLIDE
                }
                R.id.radio_button_explode -> {
                    transitionType = OPTION_EXPLODE
                }
            }
        }

        recycler_frag_transition.layoutManager = GridLayoutManager(requireActivity(), 2)
        recycler_frag_transition.addItemDecoration(GridItemDecoration(0,2))
        recycler_frag_transition.adapter = adapter
        adapter.setOnClickWeapon(object: WeaponItemAdapter.OnClickWeapon{
            override fun onSelectWeapon(view: View, position: Int) {
                val weapon = weapons[position]
                val detailScreen = WeaponDetailFragment()
                val bundle = Bundle()
                bundle.putParcelable(WEAPON_ITEM_ARG, weapon)
                detailScreen.arguments = bundle

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    detailScreen.sharedElementEnterTransition = DetailsTransition()
                    when(transitionType){
                        1 -> {
                            detailScreen.enterTransition = Fade()
                            exitTransition = Fade()
                        }
                        2 -> {
                            detailScreen.enterTransition = Slide()
                            exitTransition = Slide()
                        }
                        3 -> {
                            detailScreen.enterTransition = Explode()
                            exitTransition = Explode()
                        }
                    }
                    detailScreen.sharedElementReturnTransition = DetailsTransition()
                }

                val tx = requireActivity().supportFragmentManager.beginTransaction()
                tx.addSharedElement(view, "weapon_image")
                tx.replace(R.id.framelayout_weapons, detailScreen)
                tx.addToBackStack(null)
                tx.commit()
            }
        })
    }

    private fun getWeapons(): MutableList<Weapon> {
        return mutableListOf(
            Weapon(R.drawable.b1_sa_p92, "SA PS2"),
            Weapon(R.drawable.b2_shotgun_g_xi, "Shotgun G-XI"),
            Weapon(R.drawable.b3_ak47, "Assault Rifle AK-47"),
            Weapon(R.drawable.b4_famas_f1, "Assault Rifle FAMAS F1"),
            Weapon(R.drawable.b5_rifle_m4_carbine_cm16, "Rifle M4"),
            Weapon(R.drawable.b6_python_357, "Revolver Python 357"),
            Weapon(R.drawable.b7_asg_bersa_thunder_9_pro, "Bersa BP9CC"),
            Weapon(R.drawable.b8_colt_m1911_gnb_preta, "Colt M1911")
        )
    }

}