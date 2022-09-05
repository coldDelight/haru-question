package com.colddelight.haru_question

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.colddelight.haru_question.databinding.FragmentWriteBottomSheetBinding
import com.colddelight.haru_question.presentation.viewmodel.WriteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class WriteBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentWriteBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val model: WriteViewModel by viewModels()

    private val args: WriteBottomSheetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWriteBottomSheetBinding.inflate(inflater, container, false)

        initText()
        return binding.root
    }

    private fun initText(){
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val dateNow = LocalDate.now().format(formatter)

        binding.tvWriteQuestion.text = args.question
        binding.tvWriteQuote.text = args.quote
        binding.tvWriteAuthor.text = args.author
        binding.tvWriteDate.text = dateNow

    }

    private fun textCountSet(){
        with(binding){
            etWrite.addTextChangedListener(object :TextWatcher{
                var text=""
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    tvWriteCnt.text = "0"
                    text = p0.toString()
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val totText = etWrite.text.toString()
                    tvWriteCnt.text = totText.length.toString()
                    if(totText.length>100){
                        etWrite.setText(text)
                        etWrite.setSelection(totText.length-1)
                    }else{
                        tvWriteCnt.text = totText.length.toString()
                    }
                    binding.btnWrite.isEnabled = totText.isNotEmpty()
                }
                override fun afterTextChanged(p0: Editable?) {
                }
            }
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textCountSet()

        binding.btnWrite.setOnClickListener {
            val text = binding.etWrite.text.toString()
            model.onSubmit(text)
            findNavController().popBackStack()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}