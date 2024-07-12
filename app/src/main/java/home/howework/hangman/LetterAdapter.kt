package home.howework.hangman


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton


class LetterAdapter(context: Context):BaseAdapter() {
  private lateinit  var letterBtn: TextView
  val mContext=context
   private var letters: Array<String> = Array(26){""}
    private var letterInf:LayoutInflater?=null
    init {
        for (a in 0..25) {
            letters[a] = "" + (a + 'A'.code).toChar()
        }

    }
    override fun getCount(): Int {
      return letters.size
    }

    override fun getItem(position: Int): Any? {
    return null
    }

    override fun getItemId(position: Int): Long {
     return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView1 = convertView
        if (letterInf == null) {
            letterInf =
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
           convertView1= letterInf!!.inflate(R.layout.letter,parent,false)
        }

        letterBtn.text = letters[position]
        return convertView1!!
    }
}