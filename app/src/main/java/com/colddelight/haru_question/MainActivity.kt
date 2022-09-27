package com.colddelight.haru_question

import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.android.billingclient.api.*
import com.colddelight.haru_question.core.util.Current
import com.colddelight.haru_question.presentation.viewmodel.MainViewModel
import com.google.common.collect.ImmutableList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PurchasesUpdatedListener{
    private var backPressed = 0L
    private val mainModel : MainViewModel by viewModels()
    private lateinit var consumeListener : ConsumeResponseListener
    private lateinit var mediaPlayer:MediaPlayer
    private var bgmTime=0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        initBilling()
        connectBilling()
        setConsumeListener()

//        setBgm()
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                mainModel.isLoading.value
            }
        }
        setContentView(R.layout.activity_main)
        playBgm()

    }

    override fun onRestart() {
        super.onRestart()
        playBgm()
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.stop()
    }

    private fun playBgm(){
        mediaPlayer = MediaPlayer.create(this, R.raw.bgm)
        mediaPlayer.isLooping=true
        mediaPlayer.start()
    }

    //빌링 초기화
    private fun initBilling() {
        mainModel.billingClient = BillingClient.newBuilder(this)
            .setListener(this)
            .enablePendingPurchases()
            .build()
    }
    //빌링 연결
    private fun connectBilling(){
        mainModel.billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode ==  BillingClient.BillingResponseCode.OK) {
                    CoroutineScope(Dispatchers.Main).launch {
                        querySkuDetails()
                    }
                }
            }
            override fun onBillingServiceDisconnected() {
            }
        })
    }

    private fun setConsumeListener(){
        consumeListener = ConsumeResponseListener { billingResult, purchaseToken ->
            if(billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                //구매 성공
            } else {
                //구매 실패
            }
        }
    }

    fun querySkuDetails() {
        val tempParam = QueryProductDetailsParams.newBuilder()
            .setProductList(
                ImmutableList.of(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("donate_small_id")
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build()
                )
            ).build()
        mainModel.billingClient.queryProductDetailsAsync(tempParam) { billingResult2, mutableList ->
            mainModel.productDetailsList = mutableList
        }
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

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                val consumeParams = ConsumeParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                mainModel.billingClient.consumeAsync(consumeParams, consumeListener)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // 유저 취소
        }
    }
}