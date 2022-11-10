package com.cooper.firebasesdk.module.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.cooper.firebasesdk.R
import com.cooper.firebasesdk.common.BaseActivity
import com.cooper.firebasesdk.common.Constants
import com.cooper.firebasesdk.databinding.ActivityLoginBinding
import com.cooper.firebasesdk.module.addMeeting.ui.AddMeetingActivity
import com.cooper.firebasesdk.module.listing.ui.MOMListingActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginAndRegisterActivity : BaseActivity<ActivityLoginBinding, LoginAndRegisterViewModel>() {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.viewModel = viewModel
        viewModel.isLogin.value = intent.getBooleanExtra(Constants.IS_LOGIN, true)
        addObserver()
    }

    override fun getBindingViewId() = R.layout.activity_login

    override fun getViewModelClass() = LoginAndRegisterViewModel::class.java

    private fun addObserver() {
        viewModel.isSuccess.observe(this) {
            if (it) {
                if (viewModel.isLogin.value == true) {
                    startActivity(
                        Intent(this, MOMListingActivity::class.java)
                            .putExtra(Constants.IS_LOGIN, false)
                    )
                }
                finish()
            }
        }
    }

    fun loginButtonClicked(view: View?) {
        val email = dataBinding.email.text.toString()
        val pass = dataBinding.password.text.toString()
        if (viewModel.isLogin.value == true) {
            viewModel.doLogin(email, pass)
        } else {
            viewModel.registerUser(email, pass)
        }
    }

    fun signUpButtonClicked(view: View?) {
        if (viewModel.isLogin.value == true) {
            startActivity(
                Intent(this, LoginAndRegisterActivity::class.java)
                    .putExtra(Constants.IS_LOGIN, false)
            )
        } else {
            finish()
        }
    }

}