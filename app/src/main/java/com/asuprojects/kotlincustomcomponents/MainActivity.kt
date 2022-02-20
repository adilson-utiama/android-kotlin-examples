package com.asuprojects.kotlincustomcomponents

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.asuprojects.kotlincustomcomponents.fragments.async.AsyncExamplesFragment
import com.asuprojects.kotlincustomcomponents.fragments.httprequests.HttpRequestsExamplesFragment
import com.asuprojects.kotlincustomcomponents.fragments.inputs.InputsExamplesFragment
import com.asuprojects.kotlincustomcomponents.fragments.lists.ListsExamplesFragment
import com.asuprojects.kotlincustomcomponents.fragments.media.MediaExamplesFragment
import com.asuprojects.kotlincustomcomponents.fragments.spinners.SpinnersFragment
import com.asuprojects.kotlincustomcomponents.fragments.widgets.WidgetsExamplesFragment
import com.asuprojects.kotlincustomcomponents.menuscreens.*
import com.asuprojects.kotlincustomcomponents.screens.camerax.CameraXExamplesActivity
import com.asuprojects.kotlincustomcomponents.screens.qrcode.QRCodeActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var actualTitle = "Interface and layouts"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Orientação da Tela Apenas VERTICAL
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark, null)
        }

        /**
         * ToolBar Configuration
         */
        setSupportActionBar(toolbar)
        if(supportActionBar != null){
            supportActionBar!!.title = "Components Examples"
        }

        /**
         * DrawerLayout Configuration
         */
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        /**
         * FragmentManager Setting
         */
        val tx = supportFragmentManager.beginTransaction()
        tx.replace(R.id.framelayout_main_window,
            InterfaceLayoutsFragment()
        )
        tx.commit()


    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.nav_menu_camerax -> {
                startActivity(Intent(this@MainActivity, CameraXExamplesActivity::class.java))
            }
            R.id.nav_menu_qr_code -> {
                startActivity(Intent(this@MainActivity, QRCodeActivity::class.java))
            }
            R.id.nav_menu_layouts -> {
                val tx = supportFragmentManager.beginTransaction()
                tx.replace(R.id.framelayout_main_window,
                    InterfaceLayoutsFragment()
                )
                tx.commit()

                actualTitle = "Interface and Layouts"
                toolbar.title = actualTitle
            }
            R.id.nav_menu_lists -> {
                val tx = supportFragmentManager.beginTransaction()
                tx.replace(R.id.framelayout_main_window,
                    ListsExamplesFragment()
                )
                tx.commit()

                actualTitle = "Lists Examples"
                toolbar.title = actualTitle
            }
            R.id.nav_menu_widgets -> {
                val tx = supportFragmentManager.beginTransaction()
                tx.replace(R.id.framelayout_main_window,
                    WidgetsExamplesFragment()
                )
                tx.commit()

                actualTitle = "Widget Examples"
                toolbar.title = actualTitle
            }
            R.id.nav_menu_services -> {
                val tx = supportFragmentManager.beginTransaction()
                tx.replace(R.id.framelayout_main_window,
                    BackgroundTasksFragment()
                )
                tx.commit()

                actualTitle = "Background Tasks"
                toolbar.title = actualTitle
            }
            R.id.nav_menu_sensors -> {
                val tx = supportFragmentManager.beginTransaction()
                tx.replace(R.id.framelayout_main_window,
                    SensorsFragment()
                )
                tx.commit()

                actualTitle = "Sensors"
                toolbar.title = actualTitle
            }
            R.id.nav_menu_device_resources -> {
                val tx = supportFragmentManager.beginTransaction()
                tx.replace(R.id.framelayout_main_window,
                    DeviceResourcesFragment()
                )
                tx.commit()

                actualTitle = "Storage and Permissions"
                toolbar.title = actualTitle
            }
            R.id.nav_menu_prototypes -> {
                val tx = supportFragmentManager.beginTransaction()
                tx.replace(R.id.framelayout_main_window,
                    PrototypesFragment()
                )
                tx.commit()

                actualTitle = "My Prototypes"
                toolbar.title = actualTitle
            }
            R.id.nav_menu_inputs -> {
                val tx = supportFragmentManager.beginTransaction()
                tx.replace(R.id.framelayout_main_window,
                    InputsExamplesFragment()
                )
                tx.commit()

                actualTitle = "Input Examples"
                toolbar.title = actualTitle
            }
            R.id.nav_menu_spinners -> {
                val tx = supportFragmentManager.beginTransaction()
                tx.replace(R.id.framelayout_main_window,
                    SpinnersFragment()
                )
                tx.commit()

                actualTitle = "Spinners Examples"
                toolbar.title = actualTitle
            }
            R.id.nav_menu_media -> {
                val tx = supportFragmentManager.beginTransaction()
                tx.replace(R.id.framelayout_main_window,
                    MediaExamplesFragment()
                )
                tx.commit()

                actualTitle = "Media Examples"
                toolbar.title = actualTitle
            }
            R.id.nav_menu_room_db -> {
                val tx = supportFragmentManager.beginTransaction()
                tx.replace(R.id.framelayout_main_window,
                    AndroidArchitectureFragment()
                )
                tx.commit()

                actualTitle = "Android Architecture"
                toolbar.title = actualTitle
            }
            R.id.nav_menu_http_requests -> {
                val tx = supportFragmentManager.beginTransaction()
                tx.replace(R.id.framelayout_main_window,
                    HttpRequestsExamplesFragment()
                )
                tx.commit()

                actualTitle = "Http Requests"
                toolbar.title = actualTitle
            }
            R.id.nav_menu_async_tasks -> {
                val tx = supportFragmentManager.beginTransaction()
                tx.replace(R.id.framelayout_main_window,
                    AsyncExamplesFragment()
                )
                tx.commit()

                actualTitle = "Async Examples"
                toolbar.title = actualTitle
            }
            R.id.nav_menu_transitions -> {
                goToFragment("Transitions", TransitionsFragment())
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun goToFragment(title: String, fragment: Fragment){
        val tx = supportFragmentManager.beginTransaction()
        tx.replace(R.id.framelayout_main_window, fragment)
        tx.commit()

        actualTitle = title
        toolbar.title = actualTitle
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if(count == 0) {
            AlertDialog.Builder(this)
                .setMessage("Deseja Sair do Aplicativo?")
                .setPositiveButton("Sim") { _, _ ->
                    finish()
                    super.onBackPressed()
                }
                .setNegativeButton("Cancelar", null)
                .show()
            //}else if(count == 1){

        }else{
            supportFragmentManager.popBackStack()
            supportActionBar?.title = actualTitle
        }
    }
}
