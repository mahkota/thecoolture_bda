package com.thecoolture.batikdetectionapp
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.thecoolture.batikdetectionapp.databinding.ActivityMainBinding
import com.thecoolture.batikdetectionapp.ui.detail.ScannedBatikDetailActivity
import java.io.ByteArrayOutputStream
import java.io.File

class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    lateinit var filepath : Uri

    var imgString: String = ""

    companion object {
        const val EXTRA_STORE = "1"
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMainBinding.inflate(layoutInflater)
     setContentView(binding.root)

        var txtTry: TextView = findViewById(R.id.txt_try)
        var imgTry: ImageView = findViewById(R.id.img_try)
        txtTry.animate().alpha(0f).setDuration(50000).setStartDelay(30000)
        imgTry.animate().alpha(0f).setDuration(50000).setStartDelay(30000)
        txtTry.animate().translationX(100f).setDuration(3000).setStartDelay(0)
        imgTry.animate().translationX(100f).setDuration(3000).setStartDelay(0)


        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener {
            fileChooser()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_store, R.id.nav_history), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        mainActivityViewModel.onProcess.observe(this, {
            when (it) {
                0 -> Snackbar.make(binding.appBarMain.root, "Loading detection result...", Snackbar.LENGTH_INDEFINITE).show()
                2 -> Snackbar.make(binding.appBarMain.root, "Detection failed due to: ${mainActivityViewModel.errorDetails}. Please try again.", Snackbar.LENGTH_LONG).show()
            }
        })

        mainActivityViewModel.predictionResult.observe(this, Observer {
            Snackbar.make(binding.appBarMain.root, "Detection complete! Opening detection result...", Snackbar.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, ScannedBatikDetailActivity::class.java)
                intent.putExtra(ScannedBatikDetailActivity.EXTRA_BATIK_NAME, it[0].label)
//                binding.appBarMain.fab.show()
                this.startActivity(intent)
            }, 1500)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun fileChooser() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Pilih Gambar"), 111)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==111 && resultCode == Activity.RESULT_OK && data != null){
            filepath = data.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)

            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
            val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)

            Log.d("MainActivity", "imageString: $imageString")
            Log.d("MainActivity", "imageString: ${imageString.length}")


            val path: File = this.filesDir

            mainActivityViewModel.runPrediction(imageString, path)
        }
    }
}