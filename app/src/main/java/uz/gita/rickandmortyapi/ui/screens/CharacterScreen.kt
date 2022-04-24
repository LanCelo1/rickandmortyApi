package uz.gita.rickandmortyapi.ui.screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import timber.log.Timber
import uz.gita.rickandmortyapi.App
import uz.gita.rickandmortyapi.R
import uz.gita.rickandmortyapi.databinding.ScreenCharacterBinding
import uz.gita.rickandmortyapi.ui.adapter.ResultAdapter
import uz.gita.rickandmortyapi.ui.adapter.paging.LoaderStateAdapter
import uz.gita.rickandmortyapi.ui.presenter.CharacterViewModel
import uz.gita.rickandmortyapi.ui.presenter.CharacterViewModelImpl

@AndroidEntryPoint
class CharacterScreen : Fragment(R.layout.screen_character) {
    private var _binding: ScreenCharacterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterViewModel by viewModels<CharacterViewModelImpl>()
    private lateinit var charachterAdapter: ResultAdapter
    private lateinit var loaderStateAdapter: LoaderStateAdapter
    private lateinit var rvDoggoLoader: RecyclerView
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = ScreenCharacterBinding.bind(view)
        setUpRecyclerview()
        setUpObservers()
//        viewModel.getAllInfo(5,4)
        if (isNetworkAvailable(context)) {
            getAllInfo()
        } else {
            getAllInfoFromDatabase()
        }
    }

    private fun getAllInfoFromDatabase() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getAllInfoFromDatabase().distinctUntilChanged().collectLatest {
                charachterAdapter.submitData(it)
                Timber.d("it's database source")
            }
        }
    }

    private fun getAllInfo() {
        lifecycleScope.launch {
            viewModel.getAllInfo.distinctUntilChanged().collectLatest {
                charachterAdapter.submitData(it)
            }
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    private fun setUpObservers() {

    }

    private fun setUpRecyclerview() {
        charachterAdapter = ResultAdapter()
        binding.recyclerview.apply {
            adapter = charachterAdapter
            loaderStateAdapter = LoaderStateAdapter { charachterAdapter.retry() }
            charachterAdapter.onClickItemListener {
                navController.navigate(CharacterScreenDirections.actionCharacterScreenToEpisodeScreen(it))
            }
            /*charachterAdapter.haveElementListener {
                binding.imgEmptyList.visibility = View.GONE
            }*/
            binding.recyclerview.adapter = charachterAdapter.withLoadStateFooter(loaderStateAdapter)
            val decoration = DividerItemDecoration(App.instance, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            layoutManager = LinearLayoutManager(this@CharacterScreen.context)
        }
    }
}