package com.android.onlinefoodorderingapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.android.onlinefoodorderingapp.presentation.util.AppConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val context: Context
) {

    private val Context.dataStore by preferencesDataStore(AppConstants.AUTH)

    companion object {
        val KEY_LOGGED_IN = booleanPreferencesKey(AppConstants.IS_LOGGED_IN)
        val KEY_PHONE = stringPreferencesKey(AppConstants.PHONE)
    }

    suspend fun saveSession(isLoggedIn: Boolean, phone: String) {
        context.dataStore.edit {
            it[KEY_LOGGED_IN] = isLoggedIn
            it[KEY_PHONE] = phone
        }
    }

    val isLoggedIn: Flow<Boolean> =
        context.dataStore.data.map {
            it[KEY_LOGGED_IN] ?: false
        }

    val phone: Flow<String?> =
        context.dataStore.data.map {
            it[KEY_PHONE]
        }

    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}