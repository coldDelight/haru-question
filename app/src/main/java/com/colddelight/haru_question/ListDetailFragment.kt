package com.colddelight.haru_question

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.PixelCopy
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.colddelight.haru_question.databinding.FragmentListDetailBinding
import com.colddelight.haru_question.presentation.viewmodel.ListDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DecimalFormat


@AndroidEntryPoint
class ListDetailFragment : Fragment() {
    lateinit var binding : FragmentListDetailBinding
    private val model: ListDetailViewModel by viewModels()
    private val args: ListDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentListDetailBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            model.getQna(args.id,args.aId)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        binding.btnShare.setOnClickListener{
            val resInfoList =
            screenShare(binding.root) { bitmap ->
                screenShotToShare(bitmap)
            }
        }
        binding.btnScreen.setOnClickListener{

        }

        binding.btnDetailBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun screenShotToShare(bitmap: Bitmap?) {
        try {
            val cachePath = File(requireActivity().cacheDir, "images")
            cachePath.mkdirs()
            val stream = FileOutputStream("$cachePath/image.png")
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()
            val newFile = File(cachePath, "image.png")
            val contentUri = FileProvider.getUriForFile(
                requireActivity(),
                "com.colddelight.haru_question", newFile
            )
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/png"
            intent.putExtra(Intent.EXTRA_STREAM, contentUri)
            val resInfoList: List<ResolveInfo> = requireActivity().packageManager
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)

            for (resolveInfo in resInfoList) {
                val packageName = resolveInfo.activityInfo.packageName
                requireActivity().grantUriPermission(
                    packageName,
                    contentUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }

            startActivity(Intent.createChooser(intent, "Share image"))
        }catch (e: IOException){
            e.printStackTrace()
        }
    }

    private fun setObserver() {
        // 뷰모델 관찰
        model.item.observe(viewLifecycleOwner) {
            //TODO 확인!
            binding.tvDetailDate.text = it.date
            binding.tvDetailAuthor.text = it.quoteAuthor
            binding.tvDetailAnswer.text = it.answer
            binding.tvDetailQutoe.text = it.quote
            binding.tvDetailQuestion.text = it.question
            binding.tvDetailCnt.text = it.answer.length.toString()
            binding.tvDetailNumber.text = DecimalFormat("000").format(it.id)
        }

    }

    private fun screenShare(view: View, callback: (Bitmap?) -> Unit) {
        requireActivity().window?.let { window ->
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2)
            view.getLocationInWindow(locationOfViewInWindow)
            try {
                PixelCopy.request(window,
                    Rect(locationOfViewInWindow[0], locationOfViewInWindow[1], locationOfViewInWindow[0] + view.width, locationOfViewInWindow[1] + view.height),
                    bitmap, { copyResult ->
                        if (copyResult == PixelCopy.SUCCESS) callback.invoke(bitmap)
                        else callback.invoke(null)
                    }, Handler(Looper.getMainLooper()))
            } catch (e: IllegalArgumentException) {
                callback.invoke(null)
            }
        }
    }

}