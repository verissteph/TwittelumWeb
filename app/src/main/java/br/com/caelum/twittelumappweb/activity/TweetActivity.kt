package br.com.caelum.twittelumappweb.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.caelum.twittelumappweb.R
import br.com.caelum.twittelumappweb.databinding.ActivityTweetBinding
import br.com.caelum.twittelumappweb.decodificaParaBase64
import br.com.caelum.twittelumappweb.modelo.GPS
import br.com.caelum.twittelumappweb.modelo.Tweet
import br.com.caelum.twittelumappweb.viewmodel.TweetViewModel
import br.com.caelum.twittelumappweb.viewmodel.ViewModelFactory
import java.io.File
import java.util.jar.Manifest


class TweetActivity : AppCompatActivity() {

    private val viewModel: TweetViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory).get(TweetViewModel::class.java)
    }
    private lateinit var gps: GPS
    private var localFoto: String? = null

    lateinit var binding: ActivityTweetBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTweetBinding.inflate(layoutInflater)
       setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        gps = GPS(this)
        if(checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            gps.fazBusca()
        }else{
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),125)
        }

        viewModel.falha().observe(this, Observer { excecao ->
            Toast.makeText(this, "erro:${excecao?.message}", Toast.LENGTH_LONG).show()
            Log.e("Tweet", "falha na requisicao", excecao)
        })
        viewModel.novoTweet().observe(this, Observer { tweet ->
            Toast.makeText(this, "Tweet salvo:${tweet?.mensagem}", Toast.LENGTH_LONG).show()
            Log.i("Tweet", "$tweet criado na API")
        })

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 123){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                gps.fazBusca()
            }else{
                Toast.makeText(this,"Nao será possivel usar as funcionalidades dessa tela",Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        gps.cancela()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.tweet_menu, menu)

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            android.R.id.home -> finish()


            R.id.tweet_menu_cadastrar -> {

                publicaTweet()

                finish()

            }


            R.id.tweet_menu_foto -> {

                tiraFoto()

            }

        }

        return true

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                carregaFoto()
            }
        }
    }


    private fun publicaTweet() {

        val tweet = criaTweet()

        viewModel.salva(tweet)

        Toast.makeText(this, "$tweet foi salvo com sucesso :D", Toast.LENGTH_LONG).show()
    }

    fun criaTweet(): Tweet {
        val(latitude,longitude) = gps.coordenadas()

        val campoDeMensagemDoTweet = binding.tweetMensagem

        val mensagemDoTweet: String = campoDeMensagemDoTweet.text.toString()

        val foto: String? = binding.tweetFoto.tag as String?

        return Tweet(mensagemDoTweet, foto,latitude,longitude)
    }


    private fun tiraFoto() {

        val vaiPraCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val caminhoFoto = defineLocalDaFoto()

        vaiPraCamera.putExtra(MediaStore.EXTRA_OUTPUT, caminhoFoto)

        startActivityForResult(vaiPraCamera, 123)

    }

    private fun defineLocalDaFoto(): Uri {

        localFoto = "${getExternalFilesDir("fotos")}/${System.currentTimeMillis()}.jpg"

        val arquivo = File(localFoto)

        return FileProvider.getUriForFile(this, "TweetProvider", arquivo)
    }


    private fun carregaFoto() {

        val bitmap = BitmapFactory.decodeFile(localFoto)

        val bm = Bitmap.createScaledBitmap(bitmap, 300, 300, true)

        binding.tweetFoto.setImageBitmap(bm)

        val fotoNaBase64 = bm.decodificaParaBase64()

        binding.tweetFoto.tag = fotoNaBase64

        binding.tweetFoto.scaleType = ImageView.ScaleType.FIT_XY

    }


}
