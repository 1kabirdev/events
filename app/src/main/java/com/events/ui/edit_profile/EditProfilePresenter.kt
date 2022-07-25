package com.events.ui.edit_profile

import com.events.data.DataManager
import com.events.model.profile.UpdateAvatar
import com.events.model.profile.UpdateProfile
import com.events.mvp.BasePresenter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfilePresenter(private var dataManager: DataManager) :
    BasePresenter<EditProfileController.View>(), EditProfileController.Presenter {

    private lateinit var call: Call<UpdateAvatar>
    private lateinit var callUpdateProfile: Call<UpdateProfile>

    override fun responseUpdateAvatar(user_id: Int, image: ByteArray) {
        mvpView?.let { view ->
            view.progressAvatar(true)
            val tsLong = System.currentTimeMillis() / 1000
            val nameImage = "$tsLong.jpg"
            val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), image)
            val imageUpload = MultipartBody.Part.createFormData("image", nameImage, requestFile)
            val userId = user_id.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            call = dataManager.updateAvatar(userId, imageUpload)
            call.enqueue(object : Callback<UpdateAvatar> {
                override fun onResponse(
                    call: Call<UpdateAvatar>,
                    response: Response<UpdateAvatar>
                ) {
                    view.progressAvatar(false)
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            view.updateAvatar(data)
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateAvatar>, t: Throwable) {
                    view.progressAvatar(false)
                    view.errorAvatar()
                }
            })
        }
    }

    override fun responseUpdateProfile(user_id: Int, lastName: String, about: String) {
        mvpView?.let { view ->
            view.progressProfile(true)
            callUpdateProfile = dataManager.updateProfile(user_id, lastName, about)
            callUpdateProfile.enqueue(object : Callback<UpdateProfile> {
                override fun onResponse(
                    call: Call<UpdateProfile>,
                    response: Response<UpdateProfile>
                ) {
                    view.progressProfile(false)
                    if (response.isSuccessful) {
                        response.body()?.let { update ->
                            view.updateProfile(updateProfile = update)
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateProfile>, t: Throwable) {
                    view.progressProfile(false)
                    view.errorProfile()
                }
            })
        }
    }
}