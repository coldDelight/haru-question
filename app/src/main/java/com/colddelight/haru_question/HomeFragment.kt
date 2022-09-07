package com.colddelight.haru_question

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.colddelight.haru_question.core.util.HaruState
import com.colddelight.haru_question.databinding.FragmentHomeBinding
import com.colddelight.haru_question.presentation.viewmodel.HomeViewModel
import com.colddelight.haru_question.presentation.viewmodel.MainViewModel
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class HomeFragment : Fragment() ,NavigationView.OnNavigationItemSelectedListener{
    lateinit var binding : FragmentHomeBinding
//    private val model: HomeViewModel by viewModels()
private val mainModel:MainViewModel by activityViewModels()

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

        //로티 play
        binding.lottieHome.playAnimation()
        lifecycleScope.launchWhenStarted {
            mainModel.state.collectLatest {
                when(it.state){
                    HaruState.READY->{
                        binding.tvHomeTitle.text = resources.getString(R.string.haru_q_ready)
                        binding.lottieHome.setOnClickListener {
                            mainModel.checkQuestion()
                        }
                    }
                    HaruState.SHOW ->{
                        binding.btnToWrite.isEnabled=true
                        binding.tvHomeTitle.text= it.questionData.question
                        binding.lottieHome.setOnClickListener {
                        }
                        binding.lottieHome.setAnimation(R.raw.home_second)
                        binding.lottieHome.playAnimation()
                    }
                    HaruState.WAIT->{
                        binding.tvHomeTitle.text = resources.getString(R.string.haru_q_wait)
                        binding.lottieHome.setAnimation(R.raw.home_third)
                        binding.lottieHome.playAnimation()
                    }
                }
            }
        }
    }

    private fun setUpButton(){
        binding.btnToList.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_haruListFragment)
        }
        binding.btnToWorry.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_haruWorryFragment)
        }
        binding.btnToWrite.setOnClickListener {
            val quote = mainModel.state.value.questionData.quote
            val question = mainModel.state.value.questionData.question
            val author = mainModel.state.value.questionData.quoteAuthor
            val questionId = mainModel.state.value.questionData.id
            val action =HomeFragmentDirections.actionHomeFragmentToWriteBottomSheetFragment(quote,question,questionId,author)
            findNavController().navigate(action)
        }
        //드로어
        binding.btnDrawer.setOnClickListener {
            binding.dlHome.openDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
                R.id.item_open_source->{
                    Intent(requireActivity().applicationContext, OssLicensesMenuActivity::class.java).also {it2->
                        OssLicensesMenuActivity.setActivityTitle("오픈소스 라이선스")
                        startActivity(it2)
                    }
                }
                else->{

                }
            }
        return true
    }
}
