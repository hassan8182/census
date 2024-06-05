package com.census.ui.auth
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import com.census.R
import com.census.BR
import com.census.data.request.LoginRequest
import com.census.databinding.ActivityLoginBinding
import com.census.ui.base.BaseActivity
import com.census.ui.dashboard.DashboardActivity
import com.census.utils.CommonUtils
import com.census.utils.NetworkUtils
import com.census.utils.showToast
import com.google.gson.Gson


import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity :
    BaseActivity<LoginActivityViewModel, ActivityLoginBinding>(R.layout.activity_login) {


    companion object {

        const val ON_LOGIN_CLICK = 0


        fun newIntent(
            context: Context
        ): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }


    override val viewModel: LoginActivityViewModel by viewModels()

    override fun getBindingVariable() = BR.viewModel


    override fun onNetworkConnected() {

    }


    override fun initUi() {
        viewModel.event.observe(this) {
            when (it) {
                ON_LOGIN_CLICK -> {
                    onLogInClick()
                }
            }
        }

        addListener()
        updateStatusBar()
        setStatusBarTextColor(false)


    }





    /////////////////////////////////////////////////////////

    private fun addListener() {
        bindings.etUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(
                s: CharSequence?, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.isErrorTextShowing.value = text.isEmpty()
                bindings.tvErrorText.text = ""

            }
        })
        bindings.etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(
                s: CharSequence?, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.isErrorTextShowing.value = text.isEmpty()
                bindings.tvErrorText.text = ""
            }
        })
    }
    private fun onLogInClick() {

        val userName = bindings.etUserName.text.toString()
        val password = bindings.etPassword.text.toString()


        if (userName.isEmpty()) {
            viewModel.isErrorTextShowing.value = true
            bindings.tvErrorText.text = "Please enter user name."
        }  else if (password.isEmpty()) {
            viewModel.isErrorTextShowing.value = true
            bindings.tvErrorText.text = "Please enter your password."
        } else {
            var loginRequest=LoginRequest()
            loginRequest.userName=userName
            loginRequest.password=password
            callLoginApi(loginRequest)
        }
    }

    private fun callLoginApi(request: LoginRequest) {
        if (!NetworkUtils.isNetworkConnected(this)) {
            CommonUtils.showToast(this, getString(R.string.internet_error))
            return
        }
        viewModel.login(request, onSuccess = { message,user->
            Log.d("userDetail","${user?.id}")
            viewModel.dataManager.getPreferencesHelper().saveUserItem(Gson().toJson(user))
            startActivity(DashboardActivity.newIntent(this))
            finish()


        }) { message ->
            showToast("Login Failed")
        }
    }







}