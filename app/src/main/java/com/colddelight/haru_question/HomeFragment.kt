package com.colddelight.haru_question

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.colddelight.haru_question.core.util.HaruState
import com.colddelight.haru_question.databinding.FragmentHomeBinding
import com.colddelight.haru_question.feat_home.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val model: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //버튼 onClick
        setUpButton()
        //로티 play
        binding.lottieHome.playAnimation()
        lifecycleScope.launchWhenStarted {
            model.state.collectLatest {
                when(it.state){
                    HaruState.READY->{
                        binding.tvHomeTitle.text = "망원경을 눌러봐라"
                        binding.lottieHome.setOnClickListener {
                            model.checkQuestion()
                        }
                    }
                    HaruState.SHOW ->{
                        binding.tvHomeTitle.text= it.questionData.question
                        binding.lottieHome.setOnClickListener {

                        }
                        binding.lottieHome.setAnimation(R.raw.home_second)
                        binding.lottieHome.playAnimation()

                    }
                    HaruState.WAIT->{
                        binding.tvHomeTitle.text = "내일을 기다리자"
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
            val quote = model.state.value.questionData.quote
            val question = model.state.value.questionData.question
            val author = model.state.value.questionData.quoteAuthor
            val questionId = model.state.value.questionData.id
            val action =HomeFragmentDirections.actionHomeFragmentToWriteBottomSheetFragment(quote,question,questionId,author)
            findNavController().navigate(action)

        }
        //드로어
        binding.btnDrawer.setOnClickListener {
            binding.dlHome.openDrawer(GravityCompat.START)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("ㅇㅇㅇㅇ", "onResume: 이곳이 다ㅏ다다다", )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
