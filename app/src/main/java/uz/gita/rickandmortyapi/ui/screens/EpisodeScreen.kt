package uz.gita.rickandmortyapi.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import uz.gita.rickandmortyapi.R
import uz.gita.rickandmortyapi.databinding.ScreenEpisodeBinding
import uz.gita.rickandmortyapi.ui.presenter.EpisodeViewModel
import java.util.*

class EpisodeScreen : Fragment(R.layout.screen_episode) {
    private var _binding: ScreenEpisodeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EpisodeViewModel by viewModels()
    private val args : EpisodeScreenArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = ScreenEpisodeBinding.bind(view)
        viewModel.result = args.navArgs

        viewModel.apply {
            Glide.with(view.context).load(result.image).into(binding.circleImageView)
            binding.textView.setText("${ result.gender } , ${result.status}")
            result.episode.forEach {

            }
     //       getVolley(result.episode[0])
            binding.recyclerView.apply {
                val a = ArrayAdapter(view.context, R.layout.item_episode,R.id.name,result.episode)
                adapter = a
            }
        }

    }

}
