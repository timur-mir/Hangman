package home.howework.hangman


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {



     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//         val gridAdapt=GridAdapter(this)
//         var  lettersGrid2:GridView=findViewById(R.id.grView2)
//         lettersGrid2.numColumns = 7
//         lettersGrid2.isEnabled = true;
//         lettersGrid2?.adapter   = gridAdapt
        val playBtn = findViewById<View>(R.id.playBtn) as Button
        playBtn.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        if (v!!.id == R.id.playBtn) {
            val playIntent = Intent(this, GameActivity::class.java)
            this.startActivity(playIntent)
        }
    }
}