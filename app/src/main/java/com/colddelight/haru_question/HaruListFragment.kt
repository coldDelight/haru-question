package com.colddelight.haru_question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.colddelight.haru_question.databinding.FragmentHaruListBinding
import com.colddelight.haru_question.presentation.adapter.HaruListAdapter
import com.colddelight.haru_question.presentation.viewmodel.HaruListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HaruListFragment : Fragment() {
    private var _binding: FragmentHaruListBinding? = null
    private val binding get() = _binding!!
    private val model: HaruListViewModel by viewModels()
    private lateinit var adapter: HaruListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentHaruListBinding.inflate(inflater, container, false)
        setView()
        setObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.check()

        binding.btnListBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun setView(){
        adapter =  HaruListAdapter().apply {
            setHasStableIds(true) // 리사이클러 뷰 업데이트 시 깜빡임 방지
        }
        binding.rvHaruList.adapter = adapter // 리사이클러 뷰 연결
    }
    private fun setObserver() {
        // 뷰모델 관찰
        model.itemList.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}