package dev.morais.patterning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

abstract class AuthenticatedActivity : AppCompatActivity() {
    companion object {
        private const val RC_AUTH = 100
    }

    override fun onResume() {
        super.onResume()

        if (!isAuthenticated()) {
            Intent(this, NonAuthenticatedActivity::class.java).also {
                startActivityForResult(it, RC_AUTH)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_AUTH) {
            this.onResume()
        }
    }

    private fun isAuthenticated() = Firebase.auth.currentUser != null
}