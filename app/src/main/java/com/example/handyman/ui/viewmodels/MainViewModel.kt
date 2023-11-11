package com.example.handyman.ui.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.handyman.data.model.Handyman
import com.example.handyman.data.model.Profession
import com.example.handyman.data.repo.DefaultHandymanRepository
import com.example.handyman.data.repo.DefaultProfessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HandymanViewState(
    val handymanList: List<Handyman> = emptyList(),
    val professionsList: List<Profession> = emptyList()
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val handymanRepo: DefaultHandymanRepository,
    private val professionRepo: DefaultProfessionRepository
) : StateViewModel<HandymanViewState>(state = HandymanViewState(emptyList())) {

    init {
        Log.d("BOBAN", "MainViewModel -> boban init -> ")
//        viewModelScope.launch(Dispatchers.IO) {
//            Log.d("BOBAN", "MainViewModel ->  -> ---")
//            handymanRepo.handyManFlow.collectLatest {
//                setState {
//                    copy(handymanList = it)
//                }
//            }
//        }
//
//        viewModelScope.launch(Dispatchers.IO) {
//            professionRepo.professionsFlow.collectLatest {
//                setState {
//                    copy(professionsList = it)
//                }
//            }
//        }
        viewModelScope.launch {
            handymanRepo.handyManFlow.collectLatest {
                setState { copy(handymanList = it) }
            }
        }

        viewModelScope.launch {
            professionRepo.professionsFlow.collectLatest {
                setState { copy(professionsList = it) }
            }
        }
    }
}