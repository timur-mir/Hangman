package home.howework.hangman


import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textview.MaterialTextView
import kotlin.random.Random


class GameActivity : AppCompatActivity() {
    private var words: List<String> = arrayListOf()
    private var rand: Random? = null
    private var currWord: String? = null
    private var wordLayout: LinearLayout? = null
    private var charViews: MutableList<TextView> = mutableListOf<TextView>()
    lateinit var lettersGrid: GridView
    lateinit var imV1: ImageView
    lateinit var imV2: ImageView
    lateinit var imV3: ImageView
    lateinit var imV4: ImageView
    lateinit var imV5: ImageView
    lateinit var imV6: ImageView
    lateinit var ltrAdapt: GridAdapter

    // ImageView для частей тела
    var bodyParts: MutableList<ImageView> = mutableListOf<ImageView>()
    var n = 0

    // сколько всего частей тела
    private var numParts = 0

    // счетчик попыток
    private var currPart = 0

    // число символов в текущем слове
    private var numChars = 0

    // число угаданных вариантов
    private var numCorr = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        numParts = 6
        imV1 = findViewById<View>(R.id.head) as ImageView
        imV2 = findViewById<View>(R.id.body) as ImageView
        imV3 = findViewById<View>(R.id.arm1) as ImageView
        imV4 = findViewById<View>(R.id.arm2) as ImageView
        imV5 = findViewById<View>(R.id.leg1) as ImageView
        imV6 = findViewById<View>(R.id.leg2) as ImageView
        lettersGrid = findViewById<GridView>(R.id.grView)
        bodyParts.add(imV1)
        bodyParts.add(imV2)
        bodyParts.add(imV3)
        bodyParts.add(imV4)
        bodyParts.add(imV5)
        bodyParts.add(imV6)
        //  actionBar!!.setDisplayHomeAsUpEnabled(true)
        val res = resources
        words = List(10) { "" }
        words = res.getStringArray(R.array.words).toList()
        rand = Random
        wordLayout = findViewById<View>(R.id.word) as LinearLayout
        playGame()
    }

    private fun playGame() {
        //play a new game
        // Выбираем случайным образом новое слово
        var newWord = words[rand!!.nextInt(words.size)]
        // Если слово совпадает с текущим, то повторяем еще раз, пока не выберем новое слово
        while (newWord == currWord) {
            newWord = words[rand!!.nextInt(words.size)]
        }
        currWord = newWord // выбрали

        // программно создадим столько TextView, сколько символов в слове
        charViews = ArrayList<TextView>(currWord!!.length) as MutableList<TextView>
        // но сначала удалим все элементы от прошлой игры
        wordLayout!!.removeAllViews()
        // создаём массив новых TextView
        for (c in 0 until currWord!!.length) {
            charViews.add(TextView(this))
            charViews[c].text = "" + currWord!![c]
            charViews[c].layoutParams =
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            charViews[c].gravity = Gravity.CENTER
            charViews[c].setTextColor(Color.WHITE)
            charViews[c].setBackgroundResource(R.drawable.letter_bg)
            // добавляем в разметку
            wordLayout!!.addView(charViews[c])
        }
        ltrAdapt = GridAdapter(this@GameActivity);
        lettersGrid.adapter = ltrAdapt

        currPart = 0
        numChars = currWord!!.length
        numCorr = 0

        for (p in 0 until numParts) {
            bodyParts[p].visibility = View.INVISIBLE
        }
    }

    fun letterPressed(view: View) {
        val ltr = (view as AppCompatButton).text.toString()
        val letterChar = ltr[0]
        view.setEnabled(false);
        view.setBackgroundResource(R.drawable.letter_down);
        var correct = false;

        for (k in 0 until currWord!!.length) {
            if (currWord!![k] == letterChar) {
                correct = true;
                numCorr++;
                charViews[k].setTextColor(Color.BLACK);
            }
        }
        if (correct) {
            // удачная попытка
            if (numCorr == numChars) {
                // блокируем кнопки
                disableBtns();
                // выводим диалоговое окно
                val winBuild = AlertDialog.Builder(this);
                winBuild.setTitle("Поздравляем");
                winBuild.setMessage("Вы победили!\n\nОтвет:\n\n" + currWord);
                winBuild.setPositiveButton(
                    "Сыграем ещё?"
                ) { p0, p1 -> playGame() }
                winBuild.setNegativeButton(
                    "Выход"
                ) { p0, p1 -> finish() };

                winBuild.show();
            }
        } else if (currPart < numParts) {
            bodyParts[currPart].visibility = View.VISIBLE;
            currPart++

        } else {
            disableBtns();
            val loseBuild = AlertDialog.Builder(this)
            loseBuild.setTitle("Увы")
            loseBuild.setMessage("Вы проиграли!\n\nБыло загадано:\n\n"+currWord)
            loseBuild.setPositiveButton(
                "Сыграем ещё?"
            ) { dialog, id -> playGame() }

            loseBuild.setNegativeButton(
                "Выход"
            ) { dialog, id -> finish() }

            loseBuild.show()

        }
    }
    fun disableBtns() {
        val numLetters = lettersGrid.childCount
        for (l in 0 until numLetters) {
            lettersGrid.getChildAt(l).isEnabled = false
        }
    }
}