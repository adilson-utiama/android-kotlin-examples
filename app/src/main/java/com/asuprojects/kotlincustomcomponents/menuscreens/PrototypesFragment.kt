package com.asuprojects.kotlincustomcomponents.menuscreens


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.databinding.FragmentPrototypesBinding
import com.asuprojects.kotlincustomcomponents.prototypes.collapsingtoolbar.CollapsingToolBarActivity
import com.asuprojects.kotlincustomcomponents.prototypes.AddItemListActivity
import com.asuprojects.kotlincustomcomponents.prototypes.api.PaletteAPIActivity
import com.asuprojects.kotlincustomcomponents.prototypes.blurimage.BlurBackgroundActivity
import com.asuprojects.kotlincustomcomponents.prototypes.customprogressbar.CustomProgressBarActivity
import com.asuprojects.kotlincustomcomponents.prototypes.expandableitem.ExpandableRecyclerItemActivity
import com.asuprojects.kotlincustomcomponents.prototypes.expandmenu.ExpandMenuButtonActivity
import com.asuprojects.kotlincustomcomponents.prototypes.gridlayoutmanager.ProtoGridLayoutManagerActivity
import com.asuprojects.kotlincustomcomponents.prototypes.pdfprint.PdfPrinterActivity
import com.asuprojects.kotlincustomcomponents.prototypes.recyclercardscale.RecyclerCardScaleActivity
import com.asuprojects.kotlincustomcomponents.prototypes.recyclercarousel.RecyclerPagerSnapCarouselActivity
import com.asuprojects.kotlincustomcomponents.prototypes.recyclerviewpager.RecyclerViewPagerActivity
import com.asuprojects.kotlincustomcomponents.prototypes.recyclrsnapzoom.RecyclerSnapZoomActivity
import com.asuprojects.kotlincustomcomponents.screens.menupopup.MenuPopupActivity
import kotlinx.android.synthetic.main.fragment_prototypes.*

class PrototypesFragment : Fragment() {

    private var _bind: FragmentPrototypesBinding? = null
    private val bind get() = _bind!!

    override fun onDestroy() {
        _bind = null
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentPrototypesBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bind.btnPopupMenuCustom.setOnClickListener {
            startActivity(Intent(requireActivity(), MenuPopupActivity::class.java))
        }

        bind.btnExpandMenu.setOnClickListener {
            startActivity(Intent(requireActivity(), ExpandMenuButtonActivity::class.java))
        }

        bind.btnAddItemListScreen.setOnClickListener {
            startActivity(Intent(requireActivity(), AddItemListActivity::class.java))
        }

        bind.btnPrintPdf.setOnClickListener {
            startActivity(Intent(requireActivity(), PdfPrinterActivity::class.java))
        }

        bind.btnCustomProgressbar.setOnClickListener {
            startActivity(Intent(requireActivity(), CustomProgressBarActivity::class.java))
        }

        bind.btnCustomExpandableRecyclerItem.setOnClickListener {
            startActivity(Intent(requireActivity(), ExpandableRecyclerItemActivity::class.java))
        }

        bind.btnProtoGridLayoutManager.setOnClickListener {
            startActivity(Intent(requireActivity(), ProtoGridLayoutManagerActivity::class.java))
        }

        bind.btnProtoCollapsingToolBar.setOnClickListener {
            startActivity(Intent(requireActivity(), CollapsingToolBarActivity::class.java))
        }

        bind.btnProtoPaletteApi.setOnClickListener {
            startActivity(Intent(requireActivity(), PaletteAPIActivity::class.java))
        }

        bind.btnProtoRecyclerCarousel.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerPagerSnapCarouselActivity::class.java))
        }

        bind.btnProtoRecyclerViewPager.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerViewPagerActivity::class.java))
        }

        bind.btnProtoRecyclerSnapZoom.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerSnapZoomActivity::class.java))
        }

        bind.btnProtoRecyclerCardScale.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerCardScaleActivity::class.java))
        }

        bind.btnProtoBlurImage.setOnClickListener {
            startActivity(Intent(requireActivity(), BlurBackgroundActivity::class.java))
        }

    }

}
