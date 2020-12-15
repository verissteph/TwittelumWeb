package br.com.caelum.twittelumappweb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.caelum.twittelumappweb.adapter.TweetAdapter
import br.com.caelum.twittelumappweb.databinding.FragmentListaTweetsBinding
import br.com.caelum.twittelumappweb.viewmodel.TweetViewModel
import br.com.caelum.twittelumappweb.viewmodel.ViewModelFactory

class ListaTweetsFragment : Fragment() {
    private val viewModel: TweetViewModel by lazy {
        ViewModelProvider(activity!!,ViewModelFactory).get(TweetViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentListaTweetsBinding.inflate(inflater,
                container,
                false)
        val lista = viewModel.tweet()
        binding.listaTweets.adapter = TweetAdapter(lista)
        return binding.root
    }

}