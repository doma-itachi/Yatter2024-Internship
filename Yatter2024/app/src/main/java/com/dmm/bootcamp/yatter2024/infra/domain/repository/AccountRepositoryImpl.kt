package com.dmm.bootcamp.yatter2024.infra.domain.repository

import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import com.dmm.bootcamp.yatter2024.auth.TokenProvider
import com.dmm.bootcamp.yatter2024.domain.model.Account
import com.dmm.bootcamp.yatter2024.domain.model.Me
import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.infra.api.YatterApi
import com.dmm.bootcamp.yatter2024.infra.api.json.CreateAccountJson
import com.dmm.bootcamp.yatter2024.infra.domain.converter.AccountConverter
import com.dmm.bootcamp.yatter2024.infra.domain.converter.MeConverter
import com.dmm.bootcamp.yatter2024.infra.pref.MePreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.net.URI
import java.net.URL

class AccountRepositoryImpl(
  private val yatterApi: YatterApi,
  private val mePreferences: MePreferences,
  private val tokenProvider: TokenProvider
) : AccountRepository {
  override suspend fun create(
    username: Username,
    password: Password
  ): Me = withContext(Dispatchers.IO) {
    val accountJson = yatterApi.createNewAccount(
      CreateAccountJson(
        username = username.value,
        password = password.value
      )
    )

    MeConverter.convertToMe(AccountConverter.convertToDomainModel(accountJson))
  }

  override suspend fun findMe(): Me? = withContext(Dispatchers.IO) {
    val username = mePreferences.getUsername() ?: return@withContext null
    if (username.isEmpty()) return@withContext null
    val account = findByUsername(username = Username(username)) ?: return@withContext null
    MeConverter.convertToMe(account)
  }

  override suspend fun findByUsername(username: Username): Account? = withContext(Dispatchers.IO) {
    val accountJson = yatterApi.getAccountByUsername(username = username.value)
    AccountConverter.convertToDomainModel(accountJson)
  }

  override suspend fun update(
    me: Me,
    newDisplayName: String?,
    newNote: String?,
    newAvatar: URL?,
    newHeader: URL?
  ): Me {
    Log.d("ittt", "かんすう呼び出し")
    val displayNamePart = RequestBody.create("text/plain".toMediaType(), newDisplayName?:"")
    val notePart = RequestBody.create("text/plain".toMediaType(), newNote?:"")

//    var avatar: MultipartBody.Part? = null
//    if(newAvatar!= null){
////      val file: File = newAvatar.toFile()
//      val stream: InputStream = context.get
//      val requestFile: RequestBody = RequestBody.create(MultipartBody.FORM, file)
//      val body: MultipartBody.Part = MultipartBody.Part.createFormData("avatar", "avatar", requestFile)
//      avatar = body
//    }

    val accountJson = yatterApi.updateAccount(
      tokenProvider.provide(),
      displayNamePart,
      notePart,
      null,
      null
    )

    return MeConverter.convertToMe(AccountConverter.convertToDomainModel(accountJson))
  }
}
