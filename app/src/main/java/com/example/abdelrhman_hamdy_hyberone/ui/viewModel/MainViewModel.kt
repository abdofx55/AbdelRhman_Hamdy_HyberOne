package com.example.abdelrhman_hamdy_hyberone.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abdelrhman_hamdy_hyberone.data.models.Item
import com.example.abdelrhman_hamdy_hyberone.data.repository.Repository
import com.example.abdelrhman_hamdy_hyberone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _resource = MutableLiveData<Resource<List<Item>>>()
    val resource: LiveData<Resource<List<Item>>> = _resource

    init {
        getItems()
    }

    private fun getItems() = viewModelScope.launch {
        _resource.postValue(Resource.loading(null))
        repository.getItems().let {
            if (it.isSuccessful) {
                _resource.postValue(Resource.success(it.body()))
            } else {
                _resource.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    fun setErrorMessage(message: String) {
        _errorMessage.value = message
    }

}