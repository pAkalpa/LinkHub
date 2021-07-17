package com.amrdeveloper.linkhub

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amrdeveloper.linkhub.data.Folder
import com.amrdeveloper.linkhub.data.Result
import com.amrdeveloper.linkhub.data.source.FolderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FolderListViewModel @Inject constructor(
    private val folderRepository: FolderRepository
) : ViewModel() {

    private val _foldersLiveData = MutableLiveData<List<Folder>>()
    val foldersLiveData = _foldersLiveData

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading = _dataLoading

    private val _errorMessages = MutableLiveData<Int>()
    val errorMessages = _errorMessages

    fun getSortedFolderList() {
        _dataLoading.value = true
        viewModelScope.launch {
            val result = folderRepository.getSortedFolderList()
            if(result is Result.Success) {
                val folders = result.data
                _foldersLiveData.value = folders
            } else {
                _errorMessages.value = R.string.error_get_folders
            }
            _dataLoading.value = false
        }
    }

    fun getSortedFolderListByKeyword(keyword: String) {
        _dataLoading.value = true
        viewModelScope.launch {
            val result = folderRepository.getSortedFolderListByKeyword(keyword)
            if(result is Result.Success) {
                val folders = result.data
                _foldersLiveData.value = folders
            } else {
                _errorMessages.value = R.string.error_get_folders
            }
            _dataLoading.value = false
        }
    }

    fun updateFolderClickCount(folderId:  Int, count : Int) {
        viewModelScope.launch {
            folderRepository.updateClickCountByFolderId(folderId, count)
        }
    }
}