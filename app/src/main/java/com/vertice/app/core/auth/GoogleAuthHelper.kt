package com.vertice.app.core.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.vertice.app.BuildConfig

object GoogleAuthHelper {

    suspend fun signInWithGoogle(context: Context): Result<String> {
        val serverClientId = BuildConfig.GOOGLE_WEB_CLIENT_ID.trim()
        if (serverClientId.isBlank()) {
            return Result.failure(
                IllegalStateException("GOOGLE_WEB_CLIENT_ID não configurado. Adicione o Web Client ID no build.gradle.kts."),
            )
        }

        val credentialManager = CredentialManager.create(context)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(serverClientId)
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val response = credentialManager.getCredential(context = context, request = request)
            val credential = response.credential

            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                Result.success(googleIdTokenCredential.idToken)
            } else {
                Result.failure(IllegalStateException("Credencial não reconhecida: ${credential::class.java.simpleName}"))
            }
        } catch (e: GetCredentialCancellationException) {
            Result.failure(e)
        } catch (e: Exception) {
            val isNoCreds = e::class.java.simpleName.contains("NoCredential", ignoreCase = true) ||
                (e.message?.contains("No credentials", ignoreCase = true) == true)
            val msg = if (isNoCreds) {
                "Nenhuma conta Google conectada neste emulador. Adicione uma conta em 'Configurações > Contas' do Android ou teste no seu celular físico."
            } else {
                e.message ?: "Falha ao autenticar com o Google"
            }
            Result.failure(IllegalStateException(msg, e))
        }
    }
}
