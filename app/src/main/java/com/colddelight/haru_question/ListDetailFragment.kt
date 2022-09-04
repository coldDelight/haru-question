package com.colddelight.haru_question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.colddelight.haru_question.databinding.FragmentHaruListBinding
import com.colddelight.haru_question.databinding.FragmentListDetailBinding
import com.colddelight.haru_question.presentation.viewmodel.HaruListViewModel
import com.colddelight.haru_question.presentation.viewmodel.ListDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListDetailFragment : Fragment() {
    private var _binding: FragmentListDetailBinding? = null
    private val binding get() = _binding!!
    private val model: ListDetailViewModel by viewModels()

    private val args: ListDetailFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentListDetailBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            model.getQna(args.id)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
    }
    private fun setObserver() {
        // 뷰모델 관찰
        model.item.observe(viewLifecycleOwner) {
            binding.tvDetailDate.text = it.date
            binding.tvDetailAuthor.text = it.quoteAuthor
            binding.tvDetailAnswer.text = it.answer
            binding.tvDetailQutoe.text = it.quote
            binding.tvDetailQuestion.text = it.question
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}