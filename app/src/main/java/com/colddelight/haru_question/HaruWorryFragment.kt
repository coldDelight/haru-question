package com.colddelight.haru_question

import android.animation.Animator
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
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
        binding.clWorry.startAnimation(getAni())
        setOnClicks()
    }

    private fun setObserver() {
        // 뷰모델 관찰
        model.item.observe(viewLifecycleOwner) {
            binding.tvWorry.text = it
        }

    }

    private fun setOnClicks(){
        //새로고침
        binding.lottieRefresh.setOnClickListener {
            binding.lottieRefresh.visibility=View.VISIBLE
            binding.lottieRefresh.playAnimation()
            binding.clWorry.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in_short))
            model.getText()
        }

        //뒤로가기
        binding.btnWorryBack.setOnClickListener {
            findNavController().popBackStack()
        }
        //툴팁
        val balloon = getBalloon()
        binding.btnTip.setOnClickListener{
            balloon.showAlignBottom(anchor = it)
        }

    }

    private fun getAni():Animation {
        val ani = AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in)
        ani.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {

                setLottieClick()

            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
        return ani
    }

    private fun setLottieClick(){
        binding.lottieWorry.setOnClickListener{
            binding.lottieRefresh.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in))
            binding.lottieWorry.setMaxFrame(100)
//            Log.e("TAG", "setLottieClick: ${binding.lottieWorry.maxFrame}", )
            binding.lottieWorry.speed = -1f
            binding.lottieWorry.playAnimation()
            binding.lottieWorry.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                }

                override fun onAnimationEnd(animation: Animator) {
                    binding.lottieWorry.setAnimation(R.raw.worry_sub)
                    binding.lottieWorry.speed = 1f
                    binding.lottieWorry.playAnimation()
                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }
            })





//            binding.clWorry.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.fade_out))
            binding.clWorry.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in))
            model.getText()
        }

    }

    private fun getBalloon():Balloon{
        return Balloon.Builder(requireContext())
            .setWidthRatio(0.5f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("\n결정이 필요한 내용을 마음속으로 생각하고 터치해보세요. 결정에 도움을 드립니다.\n")
            .setTextColorResource(R.color.beige_100)
            .setBackgroundColorResource(R.color.gold_200)
            .setTextSize(16f)
            .setTextTypeface(Typeface.BOLD)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setCornerRadius(16f)
            .setBalloonAnimation(BalloonAnimation.FADE)
            .setLifecycleOwner(viewLifecycleOwner)
            .build()
    }
}