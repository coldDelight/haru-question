package com.colddelight.haru_question

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.colddelight.haru_question.core.util.Current
import com.colddelight.haru_question.core.util.HaruState
import com.colddelight.haru_question.databinding.FragmentHomeBinding
import com.colddelight.haru_question.presentation.viewmodel.MainViewModel
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: FragmentHomeBinding
    private val mainModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val dateNow = LocalDate.now().format(formatter)
        binding.tvHomeDate.text = dateNow

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //버튼 onClick
        setUpButton()
        binding.navigationView.setNavigationItemSelectedListener(this)

        lifecycleScope.launch() {
            mainModel.isLoading.collectLatest {
                if (it) {

                } else {
                    binding.lottieHome.playAnimation()
                }
            }


        }

        lifecycleScope.launch(Dispatchers.Main) {
            mainModel.current.observe(viewLifecycleOwner) {
                when (it) {
                    Current.DRAWER_OPEN -> {
                    }
                    Current.HOME -> {
                        binding.dlHome.close()
                    }
                    Current.ELSE -> {
                    }
                    Current.HOME_ING -> {
                        binding.tvHomeBack.startAnimation(getAni())
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted() {

            mainModel.state.collectLatest {
                when (it.state) {
                    HaruState.READY -> {
                        binding.tvHomeTitle.text = resources.getString(R.string.haru_q_ready)
                        binding.lottieHome.setOnClickListener {
                            mainModel.checkQuestion()
                        }
                    }
                    HaruState.SHOW -> {
                        binding.btnToWrite.isEnabled = true
                        binding.tvHomeTitle.text = it.questionData.question
                        binding.tvHomeNumber.text = DecimalFormat("000").format(it.questionData.id)
                        binding.tvHomeNo.text = "NO."
                        binding.lottieHome.setOnClickListener {
                        }
                        binding.lottieHome.setAnimation(R.raw.home_second)
                        binding.lottieHome.playAnimation()
                    }
                    HaruState.WAIT -> {
                        binding.tvHomeTitle.text = resources.getString(R.string.haru_q_wait)
                        binding.tvHomeNumber.visibility = View.INVISIBLE
                        binding.tvHomeNo.visibility = View.INVISIBLE
                        binding.btnToWrite.visibility = View.GONE
                        binding.lottieHome.setAnimation(R.raw.home_third)
                        binding.lottieHome.playAnimation()
                    }
                }
            }
        }
    }

    private fun setUpButton() {
        binding.btnToList.setOnClickListener {
            mainModel.changeCurrent(Current.ELSE)
            findNavController().navigate(R.id.action_homeFragment_to_haruListFragment)
//            mainModel.testFun()
        }
        binding.btnToWorry.setOnClickListener {
            mainModel.changeCurrent(Current.ELSE)
            findNavController().navigate(R.id.action_homeFragment_to_haruWorryFragment)
        }
        binding.btnToWrite.setOnClickListener {
            val quote = mainModel.state.value.questionData.quote
            val question = mainModel.state.value.questionData.question
            val author = mainModel.state.value.questionData.quoteAuthor
            val questionId = mainModel.state.value.questionData.id
            val action = HomeFragmentDirections.actionHomeFragmentToWriteBottomSheetFragment(
                quote,
                question,
                questionId,
                author
            )
            findNavController().navigate(action)
        }
        //드로어
        binding.btnDrawer.setOnClickListener {
            mainModel.changeCurrent(Current.DRAWER_OPEN)
            binding.dlHome.openDrawer(GravityCompat.START)
        }
    }

    override fun onResume() {
        mainModel.changeCurrent(Current.HOME)
        super.onResume()
    }

    private fun getAni(): Animation {
        val ani = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_short)
        ani.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.tvHomeBack.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.fade_out
                    )
                )
                mainModel.changeCurrent(Current.HOME)
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
        return ani
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_open_source -> {
                Intent(
                    requireActivity().applicationContext,
                    OssLicensesMenuActivity::class.java
                ).also { it2 ->
                    OssLicensesMenuActivity.setActivityTitle("오픈소스 라이센스")
                    startActivity(it2)
                }
            }
            R.id.item_donate -> {
                try {
                    val responseCode = mainModel.billingClient.launchBillingFlow(requireActivity(), mainModel.getFlowParams()).responseCode
                }catch (e:Exception){
                    Toast.makeText(requireContext(),"후원 불가능 기기입니다",1000).show()
                }
            }
            R.id.item_react -> {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    val packageName = requireContext().packageName
                    intent.data = Uri.parse("market://details?id=" + packageName)
                    startActivity(intent)
                }catch (e:Exception){
                    Toast.makeText(requireContext(),"반응 가능한 에플리케이션이 없습니다",1000).show()
                }
            }
            R.id.item_ask->{
                try {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "plain/Text"
                    intent.putExtra(Intent.EXTRA_SUBJECT, "HARU Q 문의")
                    intent.putExtra(Intent.EXTRA_TEXT, "HARU Q 문의")
                    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("hno05039@naver.com"))
                    intent.setPackage("com.google.android.gm");
                    intent.type = "message/rfc822"
                    startActivity(intent)
                }catch (e:Exception){
                    Toast.makeText(requireContext(),"문의 가능한 에플리케이션이 없습니다",1000).show()
                }
            }
            else -> {
            }
        }
        return true
    }
}
