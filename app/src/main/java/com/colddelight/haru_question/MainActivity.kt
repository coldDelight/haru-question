package com.colddelight.haru_question

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.PurchasesUpdatedListener
import com.colddelight.haru_question.core.util.Current
import com.colddelight.haru_question.databinding.FragmentHomeBinding
import com.colddelight.haru_question.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var backPressed = 0L

    private val mainModel : MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                mainModel.isLoading.value
            }
        }
        setContentView(R.layout.activity_main)
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController  = navHostFragment.navController
    }
    override fun onBackPressed() {
        when(mainModel.current.value){
            Current.DRAWER_OPEN->{
                mainModel.changeCurrent(Current.HOME)
            }
            Current.HOME->{
                mainModel.changeCurrent(Current.HOME_ING)
                if(backPressed + 2800> System.currentTimeMillis()){
                    super.onBackPressed()
                }
                backPressed = System.currentTimeMillis()
            }
            Current.ELSE->{
                super.onBackPressed()
            }
            Current.HOME_ING->{
                if(backPressed + 2800> System.currentTimeMillis()){
                    super.onBackPressed()
                }
            }

            else -> {}
        }
    }
}