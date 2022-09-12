package com.colddelight.haru_question

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.PixelCopy
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.colddelight.haru_question.core.util.Current
import com.colddelight.haru_question.databinding.FragmentListDetailBinding
import com.colddelight.haru_question.presentation.viewmodel.ListDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
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

        binding.tvSaveNote.bringToFront()
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
                getBitmap(binding.root) { bitmap ->
                    screenToShare(bitmap)
            }
        }
        binding.btnScreen.setOnClickListener{
            getBitmap(binding.root) { bitmap ->
                screenToSave(bitmap)
            }
        }
        binding.btnDetailBack.setOnClickListener {
            findNavController().popBackStack()
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
    private fun getBitmap(view: View, callback: (Bitmap?) -> Unit) {
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

    private fun screenToShare(bitmap: Bitmap?) {
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

    private fun screenToSave(bitmap: Bitmap?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveImageOnAboveAndroidQ(bitmap)
//            Toast.makeText(requireContext(), "이미지 저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            val writePermission = ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if(writePermission == PackageManager.PERMISSION_GRANTED) {
                saveImageOnUnderAndroidQ(bitmap)
//                Toast.makeText(requireContext(), "이미지 저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val requestExternalStorageCode = 1

                val permissionStorage = arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                ActivityCompat.requestPermissions(requireActivity(), permissionStorage, requestExternalStorageCode)
            }
        }

    }
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageOnAboveAndroidQ(bitmap: Bitmap?) {
        val fileName = System.currentTimeMillis().toString() + ".png"
        val contentValues = ContentValues()
        contentValues.apply {
            put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/HaruQ")
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
        val uri = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        try {
            if(uri != null) {
                val image = requireActivity().contentResolver.openFileDescriptor(uri, "w", null)
                if(image != null) {
                    val fos = FileOutputStream(image.fileDescriptor)
                    bitmap?.compress(Bitmap.CompressFormat.PNG, 100, fos)
                    fos.close()
                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                    requireActivity().contentResolver.update(uri, contentValues, null, null)
                    binding.tvSaveNote.startAnimation(getAni())
                }
            }
        } catch(e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveImageOnUnderAndroidQ(bitmap: Bitmap?) {
        val fileName = System.currentTimeMillis().toString() + ".png"
        val externalStorage = Environment.getExternalStorageDirectory().absolutePath
        val path = "$externalStorage/DCIM/HaruQ"
        val dir = File(path)

        if(dir.exists().not()) {
            dir.mkdirs()
        }
        try {
            val fileItem = File("$dir/$fileName")
            fileItem.createNewFile()
            val fos = FileOutputStream(fileItem)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()
            requireActivity().sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))
            binding.tvSaveNote.startAnimation(getAni())

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getAni(): Animation {
        val ani = AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in_short)
        ani.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
            }
            override fun onAnimationEnd(p0: Animation?) {
                binding.tvSaveNote.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.fade_out))
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
        return ani
    }



}