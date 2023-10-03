package academy.bangkit.monopolyberbelanja

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[POSISI_KEY] ?: 0,
                preferences[SALDO_KEY] ?: 0,
                preferences[JMLJAJANAN_KEY] ?: 0
            )
        }
    }

    suspend fun savePosition(posisi: Int) {
        dataStore.edit { preferences ->
            preferences[POSISI_KEY] = posisi
        }
    }

    suspend fun saveSaldo(saldo: Int) {
        dataStore.edit { preferences ->
            preferences[SALDO_KEY] = saldo
        }
    }

    suspend fun saveJumlahJajanan(jmlJajanan: Int) {
        dataStore.edit { preferences ->
            preferences[JMLJAJANAN_KEY] = jmlJajanan
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val POSISI_KEY = intPreferencesKey("posisi")
        private val SALDO_KEY = intPreferencesKey("saldo")
        private val JMLJAJANAN_KEY = intPreferencesKey("jmlJajanan")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}