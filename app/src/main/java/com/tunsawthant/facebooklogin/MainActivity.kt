package com.tunsawthant.facebooklogin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.facebook.CallbackManager
import kotlinx.android.synthetic.main.activity_main.*
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import java.util.*
import android.content.Intent
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import org.json.JSONException
import com.facebook.GraphResponse
import org.json.JSONObject
import com.facebook.GraphRequest




class MainActivity : AppCompatActivity() {

    private val EMAIL = "email"
    val callbackManager = CallbackManager.Factory.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnfacebookLogin.setReadPermissions(Arrays.asList(EMAIL,"public_profile"))


        btnfacebookLogin.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {


                val accessToken = AccessToken.getCurrentAccessToken()
                val isLoggedIn = accessToken != null && !accessToken.isExpired
                useLoginInformation(accessToken)

                Toast.makeText(applicationContext,"success",Toast.LENGTH_SHORT).show()
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun useLoginInformation(accessToken:AccessToken) {
/**
 * Creating the GraphRequest to fetch user details
 * 1st Param - AccessToken
 * 2nd Param - Callback (which will be invoked once the request is successful)
 */
      val request = GraphRequest.newMeRequest(accessToken
) { `object`, response ->
    //OnCompleted is invoked once the GraphRequest is successful
    try {
        val name = `object`.getString("name")
        val email = `object`.getString("email")
        val image = `object`.getJSONObject("picture").getJSONObject("data").getString("url")

        txt_name.text=name
        txt_email.text=email
    } catch (e:JSONException) {
        e.printStackTrace()
    }
}
        // We set parameters to the GraphRequest using a Bundle.
      val parameters = Bundle()
parameters.putString("fields", "id,name,email,picture.width(200)")
        request.parameters = parameters
 // Initiate the GraphRequest
      request.executeAsync()
}
}

