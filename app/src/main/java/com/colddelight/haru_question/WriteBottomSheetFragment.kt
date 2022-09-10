package com.colddelight.haru_question

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.colddelight.haru_question.databinding.FragmentWriteBottomSheetBinding
import com.colddelight.haru_question.presentation.viewmodel.MainViewModel
import com.colddelight.haru_question.presentation.viewmodel.WriteViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class WriteBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentWriteBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val mainModel: MainViewModel by activityViewModels()
    private val model: WriteViewModel by viewModels()

    private val args: WriteBottomSheetFragmentArgs by navArgs()


    private fun getBottomSheetDialogDefaultHeight():Int{
        return getScreenHeight()*90/100
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupRatio(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog){
    val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
    val behavior = BottomSheetBehavior.from(bottomSheet)
    val layoutParams = bottomSheet.layoutParams
    layoutParams.height = getBottomSheetDialogDefaultHeight()
    bottomSheet.layoutParams = layoutParams
    behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }


    private fun getScreenHeight(): Int {
        val context = requireContext()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = wm.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.height() - insets.bottom - insets.top
        } else {
            val displayMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }


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
        binding.tvQuestionNumber.text = DecimalFormat("000").format(args.questionId)
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
//        val be = BottomSheetBehavior.from(binding.constraintLayout)
//        be.state = BottomSheetBehavior.STATE_EXPANDED
        textCountSet()
        binding.btnWrite.setOnClickListener {
            val text = binding.etWrite.text.toString()
            model.onSubmit(text)
            mainModel.answerQuestion()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}