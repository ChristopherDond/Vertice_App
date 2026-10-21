package com.vertice.app.core.session

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.sessionDataStore by preferencesDataStore(name = "vertice_session")

class SessionManager(private val context: Context) {

    val session: Flow<Session?> = context.sessionDataStore.data.map { prefs ->
        val token = prefs[TOKEN] ?: return@map null
        Session(
            token = token,
            userId = prefs[USER_ID].orEmpty(),
            displayName = prefs[DISPLAY_NAME].orEmpty(),
            isPro = prefs[IS_PRO] ?: false,
            violetaVerified = prefs[VIOLETA_VERIFIED] ?: false,
        )
    }

    suspend fun save(session: Session) {
        context.sessionDataStore.edit { prefs ->
            prefs[TOKEN] = session.token
            prefs[USER_ID] = session.userId
            prefs[DISPLAY_NAME] = session.displayName
            prefs[IS_PRO] = session.isPro
            prefs[VIOLETA_VERIFIED] = session.violetaVerified
        }
    }

    suspend fun updateFlags(isPro: Boolean, violetaVerified: Boolean) {
        context.sessionDataStore.edit { prefs ->
            prefs[IS_PRO] = isPro
            prefs[VIOLETA_VERIFIED] = violetaVerified
        }
    }

    suspend fun clear() {
        context.sessionDataStore.edit { it.clear() }
    }

    private companion object {
        val TOKEN = stringPreferencesKey("token")
        val USER_ID = stringPreferencesKey("user_id")
        val DISPLAY_NAME = stringPreferencesKey("display_name")
        val IS_PRO = booleanPreferencesKey("is_pro")
        val VIOLETA_VERIFIED = booleanPreferencesKey("violeta_verified")
    }
}
