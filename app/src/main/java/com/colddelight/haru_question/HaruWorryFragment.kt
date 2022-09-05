package com.colddelight.haru_question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.colddelight.haru_question.databinding.FragmentHaruWorryBinding
import com.colddelight.haru_question.databinding.FragmentHomeBinding
import com.colddelight.haru_question.presentation.viewmodel.HaruListViewModel
import com.colddelight.haru_question.presentation.viewmodel.WorryViewModel

class HaruWorryFragment : Fragment() {
    lateinit var binding : FragmentHaruWorryBinding

    private val model: WorryViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHaruWorryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()

        binding.lottieWorry.playAnimation()
        val fadeInAni = AnimationUtils.loadAnimation(this.context,R.anim.fade_in)
        binding.clWorry.startAnimation(fadeInAni)

        binding.btnWorryBack.setOnClickListener {
            findNavController().popBackStack()
        }
//        binding.textView4.visibility = View.INVISIBLE
        binding.lottieWorry.setOnClickListener{
            binding.lottieRefresh.startAnimation(fadeInAni)
            binding.lottieWorry.speed = -1f
            binding.lottieWorry.playAnimation()
            model.getText()
        }

        binding.lottieRefresh.setOnClickListener {
            binding.lottieRefresh.visibility=View.VISIBLE
            binding.lottieRefresh.playAnimation()
            model.getText()
        }
    }


    private fun setObserver() {
        // 뷰모델 관찰
        model.item.observe(viewLifecycleOwner) {
            binding.tvWorry.text = it
        }

    }


}