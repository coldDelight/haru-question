package com.colddelight.haru_question

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
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
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec

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
        val fadeInAni = AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in)
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
        val balloon = Balloon.Builder(requireContext())
            .setWidthRatio(0.5f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("\n결정이 필요한 내용을 마음속으로 생각하고 터치해보세요. 결정에 도움을 드립니다.\n")
            .setTextColorResource(R.color.beige_100)
            .setBackgroundColorResource(R.color.gold_200)
            .setTextSize(16f)
            .setTextTypeface(Typeface.BOLD)
//            .setIconDrawableResource(R.drawable.ic_tooltips)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(12)
//            .setPaddingTop(16)
//            .setPaddingBottom(16)
            .setCornerRadius(16f)
            .setBalloonAnimation(BalloonAnimation.FADE)
            .setLifecycleOwner(viewLifecycleOwner)
            .build()

        binding.btnTip.setOnClickListener{
            balloon.showAlignBottom(anchor = it)
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