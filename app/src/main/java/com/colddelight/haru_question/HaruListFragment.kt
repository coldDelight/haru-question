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
    lateinit var binding : FragmentHaruListBinding
    private val model: HaruListViewModel by viewModels()
    private lateinit var adapter: HaruListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHaruListBinding.inflate(inflater, container, false)
        setView()
        setObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.onItemClick = {
            val action =HaruListFragmentDirections.actionHaruListFragmentToListDetailFragment(it[0],it[1])
            findNavController().navigate(action)
        }
        binding.btnListBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun setView(){
        adapter =  HaruListAdapter().apply {
            setHasStableIds(true)
        }
        binding.rvHaruList.adapter = adapter
    }
    private fun setObserver() {
        // 뷰모델 관찰
        model.itemList.observe(viewLifecycleOwner) {
            if (it.isEmpty()){
                binding.tvNodata.visibility=View.VISIBLE
            }else{
                binding.tvNodata.visibility=View.GONE
            }
            adapter.setData(it)
        }

    }
}