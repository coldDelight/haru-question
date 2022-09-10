package com.colddelight.haru_question

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.colddelight.haru_question.databinding.FragmentListDetailBinding
import com.colddelight.haru_question.presentation.viewmodel.ListDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


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
            model.getQna(args.id)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        binding.btnShare.setOnClickListener{
            screenShotShare()
        }
        binding.btnScreen.setOnClickListener{
            saveImg()
        }
        binding.btnDetailBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setObserver() {
        // 뷰모델 관찰
        model.item.observe(viewLifecycleOwner) {
            binding.tvDetailDate.text = it.date
            binding.tvDetailAuthor.text = it.quoteAuthor
            binding.tvDetailAnswer.text = it.answer
            binding.tvDetailQutoe.text = it.quote
            binding.tvDetailQuestion.text = it.question
            binding.tvDetailCnt.text = it.answer.length.toString()
            binding.tvDetailNumber.text = DecimalFormat("000").format(it.id)
        }

    }

    private fun screenShot():Bitmap{
        val bitmap = Bitmap.createBitmap(binding.root.width,binding.root.height,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        binding.root.draw(canvas)
        return bitmap
    }

    private fun screenShotShare(){
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val dateNow = current.format(formatter)
        val path = (requireActivity().getExternalFilesDir(null)?.absolutePath ?: "") +"/"+dateNow+".jpg"

        val bitmap = Bitmap.createBitmap(binding.clDetail.width,binding.clDetail.height,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        binding.clDetail.draw(canvas)

        val imageFile = File(path)
        val outputStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
        outputStream.flush()
        outputStream.close()

        runCatching {
            val URI = FileProvider.getUriForFile(requireContext().applicationContext,"com.colddelight.haru_question",imageFile)
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"test")
            intent.putExtra(Intent.EXTRA_STREAM,URI)
            intent.type = "text/plain"
            startActivity(intent)
        }.onSuccess {
            Log.e("ㅇㅇ", "screenShot: 성공", )

        }.onFailure {
            Log.e("TAG", "screenShotShare: $path", )
            Log.e("ㅇㅇ", "screenShot: ${it.message}", )
        }

    }


    private fun saveImg() {
        if (!checkPermission())
            return

        runCatching {
            val path = Environment.getExternalStorageDirectory().toString()+"/HaruQ"
            val fileDir = File(path)
//            if(!fileDir.exists()){
//                fileDir.mkdir()
//            }
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            val dateNow = current.format(formatter)
            val mpath = path+"ScreenShot_"+dateNow+".png"
            val bitmap = screenShot()
            val file = File(mpath)
            val fOut = FileOutputStream(file)
//            bitmap.compress(Bitmap.CompressFormat.PNG,100,fOut)
//            fOut.flush()
//            fOut.close()
        }.onSuccess {
            Log.e("ㅐㅐ", "saveImg: 성공", )
        }.onFailure {
            Log.e("ㅐㅐ", "saveImg: ${it.message}", )
        }
    }


    private fun checkPermission():Boolean{
        val permission = ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE),1)
            return false
        }
        return true

    }

}