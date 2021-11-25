package com.asuprojects.kotlincustomcomponents.menuscreens


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.prototypes.collapsingtoolbar.CollapsingToolBarActivity
import com.asuprojects.kotlincustomcomponents.prototypes.AddItemListActivity
import com.asuprojects.kotlincustomcomponents.prototypes.api.PaletteAPIActivity
import com.asuprojects.kotlincustomcomponents.prototypes.blurimage.BlurBackgroundActivity
import com.asuprojects.kotlincustomcomponents.prototypes.customprogressbar.CustomProgressBarActivity
import com.asuprojects.kotlincustomcomponents.prototypes.expandableitem.ExpandableRecyclerItemActivity
import com.asuprojects.kotlincustomcomponents.prototypes.gridlayoutmanager.ProtoGridLayoutManagerActivity
import com.asuprojects.kotlincustomcomponents.prototypes.pdfprint.PdfPrinterActivity
import com.asuprojects.kotlincustomcomponents.prototypes.recyclercardscale.RecyclerCardScaleActivity
import com.asuprojects.kotlincustomcomponents.prototypes.recyclercarousel.RecyclerPagerSnapCarouselActivity
import com.asuprojects.kotlincustomcomponents.prototypes.recyclerviewpager.RecyclerViewPagerActivity
import com.asuprojects.kotlincustomcomponents.prototypes.recyclrsnapzoom.RecyclerSnapZoomActivity
import kotlinx.android.synthetic.main.fragment_prototypes.*

class PrototypesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prototypes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_add_item_list_screen.setOnClickListener {
            startActivity(Intent(requireActivity(), AddItemListActivity::class.java))
        }

        btn_print_pdf.setOnClickListener {
            startActivity(Intent(requireActivity(), PdfPrinterActivity::class.java))
        }

        btn_custom_progressbar.setOnClickListener {
            startActivity(Intent(requireActivity(), CustomProgressBarActivity::class.java))
        }

        btn_custom_expandable_recycler_item.setOnClickListener {
            startActivity(Intent(requireActivity(), ExpandableRecyclerItemActivity::class.java))
        }

        btn_proto_grid_layout_manager.setOnClickListener {
            startActivity(Intent(requireActivity(), ProtoGridLayoutManagerActivity::class.java))
        }

        btn_proto_collapsing_tool_bar.setOnClickListener {
            startActivity(Intent(requireActivity(), CollapsingToolBarActivity::class.java))
        }

        btn_proto_palette_api.setOnClickListener {
            startActivity(Intent(requireActivity(), PaletteAPIActivity::class.java))
        }

        btn_proto_recycler_carousel.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerPagerSnapCarouselActivity::class.java))
        }

        btn_proto_recycler_view_pager.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerViewPagerActivity::class.java))
        }

        btn_proto_recycler_snap_zoom.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerSnapZoomActivity::class.java))
        }

        btn_proto_recycler_card_scale.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerCardScaleActivity::class.java))
        }

        btn_proto_blur_image.setOnClickListener {
            startActivity(Intent(requireActivity(), BlurBackgroundActivity::class.java))
        }

    }

}
