package com.example.handyman.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.handyman.data.model.Handyman
import com.example.handyman.data.model.Post
import com.example.handyman.data.model.Profession
import com.example.handyman.data.repo.DefaultHandymanRepository
import com.example.handyman.data.repo.DefaultPostRepository
import com.example.handyman.data.repo.DefaultProfessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainViewState(
    val handymanList: List<Handyman> = emptyList(),
    val professionsList: List<Profession> = emptyList(),
    val posts: List<Post> = emptyList()
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val handymanRepo: DefaultHandymanRepository,
    private val professionRepo: DefaultProfessionRepository,
    private val postsRepo: DefaultPostRepository
) : StateViewModel<MainViewState>(state = MainViewState()) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            handymanRepo.handyManFlow.collectLatest {
                setState { copy(handymanList = it) }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            professionRepo.professionsFlow.collectLatest {
                setState { copy(professionsList = it) }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            postsRepo.postsFlow.collectLatest {
                setState { copy(posts = it) }
            }
        }
    }
}