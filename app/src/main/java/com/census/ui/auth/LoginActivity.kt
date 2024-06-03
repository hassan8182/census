package com.census.ui.auth
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import com.census.R
import com.census.BR
import com.census.databinding.ActivityLoginBinding
import com.census.ui.base.BaseActivity



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
        bindings.etPhoneNumber.addTextChangedListener(object : TextWatcher {
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

        val phoneNumber = bindings.etPhoneNumber.text.toString()
        val password = bindings.etPassword.text.toString()


        if (phoneNumber.isEmpty()) {
            viewModel.isErrorTextShowing.value = true
            bindings.tvErrorText.text = "Please enter your phone number."
        } else if (bindings.etPhoneNumber.text?.get(0).toString() != "3" || bindings.etPhoneNumber.text?.length != 10) {
            viewModel.isErrorTextShowing.value = true
            bindings.tvErrorText.text = "Invalid phone number."
        } else if (password.isEmpty()) {
            viewModel.isErrorTextShowing.value = true
            bindings.tvErrorText.text = "Please enter your password."
        } else {
//            startActivity(DashboardActivity.newIntent(this))
        }


    }







}