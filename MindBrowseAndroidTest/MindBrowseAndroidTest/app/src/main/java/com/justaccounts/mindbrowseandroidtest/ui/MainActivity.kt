package com.justaccounts.mindbrowseandroidtest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.justaccounts.mindbrowseandroidtest.databinding.ActivityMainBinding
import com.justaccounts.mindbrowseandroidtest.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var mainActivityMainBinding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityMainBinding.root)
        addFragment(HomeFragment.newInstance(), false);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                finish(); // close this activity and return to preview activity (if there is any)
            }
        }
        return super.onOptionsItemSelected(item)
    }

        public fun setToolbarTitle(title: String) {
        setTitle(title)
    }

    public fun showBackButton(showBackBtn: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackBtn);
        supportActionBar?.setDisplayShowHomeEnabled(showBackBtn);
    }

    public fun addFragment(fragment: Fragment, addToBackStack: Boolean) {
        // when app is initially opened the Fragment 1 should be visible
        supportFragmentManager.beginTransaction().apply {
            replace(mainActivityMainBinding.rootContainer.id, fragment)
            if (addToBackStack) {
                addToBackStack(null)
            }
            commit()
        }
    }
}