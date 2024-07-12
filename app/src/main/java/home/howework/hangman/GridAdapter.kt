package home.howework.hangman

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

class GridAdapter(context:Context):BaseAdapter() {
    val contexL=context
    private var layoutInflater: LayoutInflater? = null
    private lateinit var charButton: AppCompatButton
    val mContext=context
    private var letters: Array<String> = Array(26){""}
    private var letterInf:LayoutInflater?=null
    init {
        for (a in 0..25) {
            letters[a] = "" + (a + 'A'.code).toChar()
        }

    }
    override fun getCount(): Int {
      return  letters.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
     return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
      //  convertView

        if (layoutInflater == null) {
            layoutInflater =
                contexL.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            charButton = layoutInflater!!.inflate(R.layout.letter, parent,false) as AppCompatButton
        }
        else
        {
            charButton = convertView as AppCompatButton
        }

        charButton.text=letters[position]
        // at last we are returning our convert view.
        return charButton
    }
}