package academy.bangkit.monopolyberbelanja

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MonopolyViewModel(private val pref: UserPreference) : ViewModel() {
    fun savePosition(posisi: Int) {
        viewModelScope.launch {
            pref.savePosition(posisi)
        }
    }

    fun saveSaldo(saldo: Int) {
        viewModelScope.launch {
            pref.saveSaldo(saldo)
        }
    }

    fun saveJumlahJajanan(jmlJajanan: Int) {
        viewModelScope.launch {
            pref.saveJumlahJajanan(jmlJajanan)
        }
    }

    fun getUser(pref: UserPreference): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

}