package com.mazur.stationsdistance.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.mazur.stationsdistance.R
import com.mazur.stationsdistance.ui.home.HomeFragment
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_wrapper.*

class WrapperActivity : AppCompatActivity(), Navigator {

    private var menuTransactionId: Int? = null
    private val transactionTag = "transaction_id"

    //Prepared for menu :)
    companion object {
        private const val MENU_HOME = 1
//        private const val MENU_MORE1 = 2
//        private const val MENU_MORE2 = 3
//        private const val MENU_MORE3 = 4
//        private const val MENU_MORE4 = 5
//        private const val MENU_MORE5 = 6
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrapper)

        menuTransactionId = savedInstanceState?.getInt(transactionTag)

        Toasty.Config.getInstance()
            .allowQueue(false)
            .apply()

        loadBottomMenu()

        if (savedInstanceState != null) {
            return
        } else {
            supportFragmentManager.beginTransaction().add(R.id.container, HomeFragment()).commit()
        }

    }

    private fun loadBottomMenu() {
        val bottomNavigation: MeowBottomNavigation = bottom_navigation as MeowBottomNavigation
        bottomNavigation.add(MeowBottomNavigation.Model(MENU_HOME, R.drawable.ic_home_black_24dp))
//        bottomNavigation.add(MeowBottomNavigation.Model(MENU_MORE1, R.drawable.ic_home_black_24dp))
//        bottomNavigation.add(MeowBottomNavigation.Model(MENU_MORE2, R.drawable.ic_home_black_24dp))
//        bottomNavigation.add(MeowBottomNavigation.Model(MENU_MORE3, R.drawable.ic_home_black_24dp))
//        bottomNavigation.add(MeowBottomNavigation.Model(MENU_MORE4, R.drawable.ic_home_black_24dp))
//        bottomNavigation.add(MeowBottomNavigation.Model(MENU_MORE5, R.drawable.ic_home_black_24dp))
        bottomNavigation.show(MENU_HOME)

        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                MENU_HOME -> navigateTo(HomeFragment())
//                MENU_MORE1 -> navigateTo(YourFragment1())
//                MENU_MORE2 -> navigateTo(YourFragment2())
//                MENU_MORE3 -> navigateTo(YourFragment3())
//                MENU_MORE4 -> navigateTo(YourFragment4())
//                MENU_MORE5 -> navigateTo(YourFragment5())
                else -> Toast.makeText(this, R.string.menu_error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            menuTransactionId?.let {
                putInt(transactionTag, it)
            }
        }
        super.onSaveInstanceState(outState)
    }

    @SuppressLint("PrivateResource")
    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean): Int {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        return transaction.commit()
    }

    override fun navigateTo(fragment: BaseFragment, addToBackStack: Boolean) {
        Handler().post {
            replaceFragment(fragment, addToBackStack)
        }
    }

    @SuppressLint("PrivateResource")
    override fun navigateTo(fragment: BaseFragment, target: BaseFragment) {
        Handler().post {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun navigateBack() {
        supportFragmentManager.popBackStack()
    }

    override fun navigateToMenu() {
        menuTransactionId = replaceFragment(HomeFragment(), true)
    }

    override fun navigateBackToMenu() {
        menuTransactionId?.let {
            supportFragmentManager.popBackStack(it, 0)
        }
    }

}