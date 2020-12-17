package br.com.caelum.twittelumappweb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.caelum.twittelumappweb.data.TweetRepository
import br.com.caelum.twittelumappweb.data.UsuarioRepository
import br.com.caelum.twittelumappweb.webservices.InicializadorDoRetrofit
import br.com.caelum.twittelumappweb.webservices.TweetWebClient
import br.com.caelum.twittelumappweb.webservices.UsuarioWebClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        TweetViewModel::class.java -> {
            TweetViewModel(Injetor.getTweetRepository, Injetor.getUsuarioRepository) as T
        }
        else -> {
            UsuarioViewModel(Injetor.getUsuarioRepository) as T
        }
    }
}

object Injetor {
    private val getRetrofit = InicializadorDoRetrofit.retrofit
    private val getTweetWebClient = TweetWebClient(getRetrofit)
    val getTweetRepository = TweetRepository(getTweetWebClient)

    private val getUsuarioWebClient = UsuarioWebClient(getRetrofit)
    val getUsuarioRepository: UsuarioRepository = UsuarioRepository(getUsuarioWebClient)
}