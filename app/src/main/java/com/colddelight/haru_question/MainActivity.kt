package com.colddelight.haru_question

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

    private lateinit var billingClient: BillingClient
    private var productDetailsList: List<ProductDetails> = mutableListOf()
    private lateinit var consumeListener : ConsumeResponseListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBilling()
        connectBilling()
        setConsumeListener()

        installSplashScreen().apply {
            setKeepOnScreenCondition{
                mainModel.isLoading.value
            }
        }
        setContentView(R.layout.activity_main)
    }

    //빌링 초기화
    private fun initBilling() {
        billingClient = BillingClient.newBuilder(this)
            .setListener(this)
            .enablePendingPurchases()
            .build()
    }
    //빌링 연결
    private fun connectBilling(){
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode ==  BillingClient.BillingResponseCode.OK) {
                    CoroutineScope(Dispatchers.Main).launch {
                        querySkuDetails()
                    }
                }
            }
            override fun onBillingServiceDisconnected() {
                Log.e("bill", "onBillingServiceDisconnected: 연결종료", )
            }
        })
    }

    private fun setConsumeListener(){
        consumeListener = ConsumeResponseListener { billingResult, purchaseToken ->
            if(billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.e("bill", "initBilling: 구매 성공", )
            } else {
                Log.e("bill", "initBilling: 구매 t실패", )

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
        billingClient.queryProductDetailsAsync(tempParam) { billingResult2, mutableList ->
            productDetailsList = mutableList
        }
    }

    fun consume(){
        val flowProductDetailParams = BillingFlowParams.ProductDetailsParams.newBuilder()
            .setProductDetails(productDetailsList[0])
            .build()
        val flowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(listOf(flowProductDetailParams))
            .build()
        //구글 결제창
        val responseCode = billingClient.launchBillingFlow(this, flowParams).responseCode
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
                billingClient.consumeAsync(consumeParams, consumeListener)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // 유저 취소
        }
    }
}