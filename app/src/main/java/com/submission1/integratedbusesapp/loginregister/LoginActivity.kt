package com.submission1.integratedbusesapp.loginregister

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.submission1.integratedbusesapp.MapsActivity
import com.submission1.integratedbusesapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth
    private var role: String? = "null"
    private val TAG = LoginActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = Firebase.auth
        binding.register.setOnClickListener {
            val moveIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(moveIntent)
        }
        binding.login.setOnClickListener {
            login(binding.email.text.toString(),binding.password.text.toString())
        }
        if(EMAIL!="EMAIL" && PASSWORD !="PASSWORD"){
            binding.email.setText(intent.getStringExtra(EMAIL))
            binding.password.setText(intent.getStringExtra(PASSWORD))

        }
    }

    private fun check(email: String, password: String): Boolean{
        if(email.length<0 && !email.contains("@")){
            Toast.makeText(this@LoginActivity,"Email harus diisi",Toast.LENGTH_SHORT).show()
            return false
        }
        if(password.length<=5){
            Toast.makeText(this@LoginActivity,"Password harus lebih dari 5 karakter ",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    private fun login(email: String,password: String){
        if(check(email,password)){
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val moveIntent = Intent(this@LoginActivity, MapsActivity::class.java)
                    moveIntent.putExtra(MapsActivity.ROLE,extractRole())
                    startActivity(moveIntent)
                } else {
                    Toast.makeText(this@LoginActivity, "Login Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    private fun reload(){
        val moveIntent = Intent(this@LoginActivity,MapsActivity::class.java)
        moveIntent.putExtra(MapsActivity.ROLE,extractRole())
        startActivity(moveIntent)
    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if(currentUser != null){
            reload()
        }
    }

    private fun extractRole(): String{
        val user = mAuth.currentUser
        Log.d(TAG,user!!.displayName.toString())
        return if(user.displayName!!.get(0) == 'u'){
            "User"
        } else {
            "Kenek"
        }
    }
    companion object{
        const val EMAIL = "EMAIL"
        const val PASSWORD = "PASSWORD"

    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
//fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
//    this.addTextChangedListener(object : TextWatcher {
//        override fun afterTextChanged(editable: Editable?) {
//            afterTextChanged.invoke(editable.toString())
//        }
//
//        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//    })
//}