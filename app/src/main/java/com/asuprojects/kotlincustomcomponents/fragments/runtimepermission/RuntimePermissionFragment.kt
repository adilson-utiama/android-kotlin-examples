package com.asuprojects.kotlincustomcomponents.fragments.runtimepermission


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.fragment_runtime_permission.*

class RuntimePermissionFragment : Fragment() {

    private val STORAGE_PERMISSION_CODE = 23
    private val CAMERA_PERMISSION_CODE = 24

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_runtime_permission, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnStoragePermission.setOnClickListener {
            if(isStorageReadable()){
                ToastUtil.msg(requireContext(), "You already have the permission to Access Storage")
            }else{
                requestStoragePermission()
            }
        }

        btnCameraPermission.setOnClickListener {
            if(canOpenCamera()){
                ToastUtil.msg(requireContext(), "You already have the permission to Open Camera")
                openCamera()
            }else{
                requestCameraPermission()
            }
        }
    }

    private fun isStorageReadable(): Boolean {
        val result = ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
        if(result == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun canOpenCamera(): Boolean {
        val result = ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
        if(result == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            Toast.makeText(requireActivity(), "Permission Required to Access Storage", Toast.LENGTH_SHORT).show()
        }

        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
    }

    private fun requestCameraPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                Manifest.permission.CAMERA)) {

            Toast.makeText(requireActivity(), "Permission Required to Open Camera", Toast.LENGTH_SHORT).show()
        }

        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
    }


    //This method will be called when the user will tap on allow or deny
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                ToastUtil.msg(requireContext(),"Permission granted!! Now you can read the Storage")
            }else{
                ToastUtil.msg(requireContext(),"Oops, You just denied the Storage Permission!")
            }
        }

        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera()
                ToastUtil.msg(requireContext(),"Permission granted!! Now you can open Camera")
            }else{
                ToastUtil.msg(requireContext(),"Oops, You just denied the Camera Permission!")
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 100)
    }

}
