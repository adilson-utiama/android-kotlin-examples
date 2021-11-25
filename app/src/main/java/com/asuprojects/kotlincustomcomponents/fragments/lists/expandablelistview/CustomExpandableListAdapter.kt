package com.asuprojects.kotlincustomcomponents.fragments.lists.expandablelistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.appcompat.widget.AppCompatTextView
import com.asuprojects.kotlincustomcomponents.R

class CustomExpandableListAdapter(
    val context: Context,
    var listGroupTitles: MutableList<String>,
    var listGroupItems: HashMap<String, List<String>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return listGroupTitles.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listGroupItems[listGroupTitles[groupPosition]]!!.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return listGroupTitles[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return listGroupItems[listGroupTitles[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertedView = convertView
        val listTitle = getGroup(groupPosition) as String
        if(convertedView == null){
            convertedView = LayoutInflater.from(context).inflate(R.layout.expandable_list_group, null)
        }
        val titleTextView = convertedView!!.findViewById<AppCompatTextView>(R.id.expandable_list_group_title)
        titleTextView.text = listTitle
        return convertedView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convView = convertView
        val title = getChild(groupPosition, childPosition) as String
        if(convView == null){
            convView = LayoutInflater.from(context).inflate(R.layout.expandable_list_item, null)
        }
        val content = convView!!.findViewById<AppCompatTextView>(R.id.expandable_list_item_content)
        content.text = title
        return convView!!
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}