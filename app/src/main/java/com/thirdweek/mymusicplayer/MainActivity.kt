package com.thirdweek.mymusicplayer

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.thirdweek.mymusicplayer.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var listView : RecyclerView
    private lateinit var progressBar : ProgressBar
    private  var items : ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listView = binding.listView
        progressBar = binding.progress

        progressBar.visibility = View.VISIBLE
        listView.visibility = View.GONE
        runTimePermission()
        if(items.isEmpty()){
            progressBar.visibility = View.GONE
            Toast.makeText(this,"No music File found", Toast.LENGTH_SHORT).show()
        }else{
            listView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE

            listView.layoutManager = LinearLayoutManager(this)
        }

    }

    private fun runTimePermission() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(
                object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                            displaySong()
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                       p1?.continuePermissionRequest()
                    }

                }
            ).check()
    }

    private fun findSong(file : File): ArrayList<File>{

        val arrayList : ArrayList<File> = arrayListOf()
        val files  = file.listFiles()

        for(singlefile in files!!) {
            if(singlefile.isDirectory && !singlefile.isHidden){
                arrayList.addAll(findSong(singlefile))
            }else {
                if(singlefile.name.endsWith(".mp3")|| singlefile.name.endsWith(".wav")){
                    arrayList.add(singlefile)
                }
            }
        }
        return arrayList
    }


    private fun displaySong() {
        val mySongs : ArrayList<File> = findSong(Environment.getExternalStorageDirectory())

        for (i in mySongs.indices){
           var song  = mySongs[i].name.toString().replace(".mp3","")
               .replace(".m4a","")
            items.add(song)
        }
        Log.i("Info", items.toString())

        listView.adapter = CustomAdapter(this, items, mySongs)

    }
    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(this)
            .setMessage("Permission for this feature is turned off. Turn on in your App settings")
            .setPositiveButton("GO TO SETTINGS"){_,_->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)

                    intent.data = uri
                    startActivity(intent)
                }catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
            .setNegativeButton("CANCEL"){dialog, _->
                dialog.dismiss()
            }.show()
    }

}