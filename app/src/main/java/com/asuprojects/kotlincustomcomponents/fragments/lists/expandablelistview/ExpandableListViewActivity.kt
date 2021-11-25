package com.asuprojects.kotlincustomcomponents.fragments.lists.expandablelistview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.lists.expandablelistview.ExpandableListViewActivity.ExpandableListData.data
import kotlinx.android.synthetic.main.activity_expandable_list_view.*

class ExpandableListViewActivity : AppCompatActivity() {

    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandable_list_view)

        expandableListView = expandable_list_view
        if (expandableListView != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = CustomExpandableListAdapter(this, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)
            expandableListView!!.setOnGroupExpandListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            expandableListView!!.setOnGroupCollapseListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            expandableListView!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                Toast.makeText(
                    applicationContext,
                    "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(
                            titleList as
                                    ArrayList<String>
                            )
                            [groupPosition]]!!.get(
                        childPosition
                    ),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
        }

    }

    internal object ExpandableListData {
        val data: HashMap<String, List<String>>
            get() {
                val expandableListDetail =
                    HashMap<String, List<String>>()
                val myFavCricketPlayers: MutableList<String> =
                    ArrayList()
                myFavCricketPlayers.add("MS.Dhoni")
                myFavCricketPlayers.add("Sehwag")
                myFavCricketPlayers.add("Shane Watson")
                myFavCricketPlayers.add("Ricky Ponting")
                myFavCricketPlayers.add("Shahid Afridi")

                val myFavFootballPlayers: MutableList<String> = ArrayList()
                myFavFootballPlayers.add("Cristiano Ronaldo")
                myFavFootballPlayers.add("Lionel Messi")
                myFavFootballPlayers.add("Gareth Bale")
                myFavFootballPlayers.add("Neymar JR")
                myFavFootballPlayers.add("David de Gea")

                val myFavTennisPlayers: MutableList<String> = ArrayList()
                myFavTennisPlayers.add("Roger Federer")
                myFavTennisPlayers.add("Rafael Nadal")
                myFavTennisPlayers.add("Andy Murray")
                myFavTennisPlayers.add("Novak Jokovic")
                myFavTennisPlayers.add("Sania Mirza")

                expandableListDetail["CRICKET PLAYERS"] = myFavCricketPlayers
                expandableListDetail["FOOTBALL PLAYERS"] = myFavFootballPlayers
                expandableListDetail["TENNIS PLAYERS"] = myFavTennisPlayers

                return expandableListDetail
            }
    }
}