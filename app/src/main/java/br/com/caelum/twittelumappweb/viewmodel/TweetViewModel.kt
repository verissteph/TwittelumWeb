package br.com.caelum.twittelumappweb.viewmodel

import androidx.lifecycle.ViewModel
import br.com.caelum.twittelumappweb.data.TweetRepository
import br.com.caelum.twittelumappweb.modelo.Tweet

class TweetViewModel(private val repository: TweetRepository) : ViewModel() {


    fun salva(tweet: Tweet) = repository.salva(tweet)
    fun tweet(): List<Tweet> = listOf(
            Tweet("bla",null),
            Tweet("ble",null),
            Tweet("bli",null),
            Tweet("blo",null),
            Tweet("blu",null),
            Tweet("blão",null),


    )


}