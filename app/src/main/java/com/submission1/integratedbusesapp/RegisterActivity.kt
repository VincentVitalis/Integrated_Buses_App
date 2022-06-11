package com.submission1.integratedbusesapp


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.submission1.integratedbusesapp.databinding.ActivityRegisterBinding
import com.submission1.integratedbusesapp.ui.login.LoginActivity


class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    private val TAG: String = RegisterActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mAuth = Firebase.auth

        val spinner: Spinner = findViewById(R.id.role_spinner)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.role,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            spinner.onItemSelectedListener= object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    if(position==0){
                        onNothingSelected(parent)
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    Toast.makeText(this@RegisterActivity,"Silahkan Pilih Role anda",Toast.LENGTH_SHORT).show()
                }

            }
        }
        binding.btnregister.setOnClickListener {
            register(binding.username.text.toString(),binding.email.text.toString(),binding.password.text.toString(),binding.roleSpinner.selectedItem.toString(),binding.confirmPassword.text.toString())
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun register(name: String, email: String, password: String, role: String,confirm_pass: String){
        Log.e(TAG,check(name,email,password,role,confirm_pass).toString())
        if(check(name,email,password,role,confirm_pass)){
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = task.result.user
                        val profileUpdates = userProfileChangeRequest {
                            displayName =  addRole(role) + name
                        }

                        user!!.updateProfile(profileUpdates)
                            .addOnCompleteListener { taskupdate ->
                                if (taskupdate.isSuccessful) {
                                    reload()
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", taskupdate.exception)
                                    Toast.makeText(
                                        baseContext, "Authentication failed.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else{
            Toast.makeText(baseContext, "Authentication failed.",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun check(name: String, email: String, password: String, role:String, confirm_pass: String): Boolean{
        if(name.isEmpty()){
            Toast.makeText(this@RegisterActivity,"Nama harus diisi",Toast.LENGTH_SHORT).show()
            return false
        }
        if(email.isEmpty() && !email.contains("@")){
            Toast.makeText(this@RegisterActivity,"Email harus diisi",Toast.LENGTH_SHORT).show()
            return false
        }
        if(password.length<=5){
            Toast.makeText(this@RegisterActivity,"Password harus lebih dari 5 karakter ",Toast.LENGTH_SHORT).show()
            return false
        }
        if(password != confirm_pass){
            Toast.makeText(this@RegisterActivity, "Confirm Password dengan password tidak sama", Toast.LENGTH_SHORT).show()
            return false
        }
        if(role == "Pilih Role Anda") {
            Toast.makeText(this@RegisterActivity, (binding.roleSpinner.selectedItem == "User").toString(), Toast.LENGTH_SHORT).show()
        }

        return true
    }

    private fun reload(){
        val moveIntent = Intent(this@RegisterActivity,LoginActivity::class.java)
        moveIntent.putExtra(LoginActivity.EMAIL,binding.email.text)
        moveIntent.putExtra(LoginActivity.PASSWORD,binding.password.text)
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
    private fun addRole(role: String): String{
        return if(role=="User"){
            "u"
        } else{
            "k"
        }
    }
}