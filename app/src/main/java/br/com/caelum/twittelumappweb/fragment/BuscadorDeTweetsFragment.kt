package br.com.caelum.twittelumappweb.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.caelum.twittelumappweb.R
import br.com.caelum.twittelumappweb.adapter.TweetAdapter
import br.com.caelum.twittelumappweb.databinding.FragmentListaTweetsBinding
import br.com.caelum.twittelumappweb.viewmodel.TweetViewModel
import br.com.caelum.twittelumappweb.viewmodel.ViewModelFactory

class BuscadorDeTweetsFragment : Fragment() {
    private val viewModel: TweetViewModel by lazy {
        ViewModelProvider(activity!!, ViewModelFactory).get(TweetViewModel::class.java)
    }
    private lateinit var binding: FragmentListaTweetsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListaTweetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.buscador_menu, menu)
        val botaoBusca = menu.findItem(R.id.barra_busca)
        val search = botaoBusca?.actionView as SearchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(texto: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(texto: String?): Boolean {
                if (!texto.isNullOrEmpty()) {
                    val filtradps = viewModel.filtraTweetsPelo(texto)
                    binding.listaTweets.adapter = TweetAdapter(filtradps)
                }
                return false
            }
        })
    }

}