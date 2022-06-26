package com.example.slideimagepuzzlegame

import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {
    var tpGame = PuzzleGame()
    var moves = 0
    var resource = 0
    var buttonArray = Array(PuzzleGame.BOARD_ROW) {
        arrayOfNulls<ImageButton>(
            PuzzleGame.BOARD_COL
        )
    }

    /*Both xVal and yVal are meant to hold the actual x and y
       *coordinates of the buttons on the screen
       */
    private val xVal = Array(PuzzleGame.BOARD_ROW) {
        FloatArray(
            PuzzleGame.BOARD_COL
        )
    }
    private val yVal = Array(PuzzleGame.BOARD_ROW) {
        FloatArray(
            PuzzleGame.BOARD_COL
        )
    }

    /*tempButtonArray and numArray are used as part of the
       *shuffling process in order to shuffle the order of the buttons*/
    var tempButtonArray = arrayOfNulls<ImageButton>(PuzzleGame.BOARD_SIZE)
    var numArray = IntArray(PuzzleGame.BOARD_SIZE)

    /*buttonPressRow and buttonPressCol are meant to map
     *button ID's to their row and column within the buttonArray*/
    var buttonPressRow = HashMap<Int, Int>()
    var buttonPressCol = HashMap<Int, Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        initializeButtonArray()
        val imgs = resources.obtainTypedArray(R.array.flags)
        val rand = Random()
        val rndInt: Int = rand.nextInt(imgs.length())
        resource = imgs.getResourceId(rndInt, 0)
        addImageToPuzzle(resource)
        disableAllButtons()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.getItemId()
        if (id == R.id.action_help) {
            val alertDialog: AlertDialog = AlertDialog.Builder(this@MainActivity).create()
            alertDialog.setTitle("Help")
            alertDialog.setMessage("Click in one of the buttons close to white space to move it. \n\nHave fun!")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Close",
                DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
            alertDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        /*Both xVal and yVal are initialized here instead of
		 *onCreate because UI is not drawn yet in onCreate thus
		 *you cannot get the coordinates of buttons*/
        for (i in 0 until PuzzleGame.BOARD_ROW) {
            for (j in 0 until PuzzleGame.BOARD_COL) {
                xVal[i][j] = buttonArray[i][j]!!.x
                yVal[i][j] = buttonArray[i][j]!!.y
            }
        }
        shuffle()
    }

    fun enableAllButtons() {
        for (i in 0 until PuzzleGame.BOARD_ROW) {
            for (j in 0 until PuzzleGame.BOARD_COL) {
                buttonArray[i][j]!!.isEnabled = true
            }
        }
    }

    fun disableAllButtons() {
        for (i in 0 until PuzzleGame.BOARD_ROW) {
            for (j in 0 until PuzzleGame.BOARD_COL) {
                buttonArray[i][j]!!.isEnabled = false
            }
        }
    }

    fun addImageToPuzzle(resource: Int) {
        val bmap = BitmapFactory.decodeResource(resources, resource)
        val bMapScaled = Bitmap.createScaledBitmap(bmap, 750, 750, true)
        val bMapArray = arrayOfNulls<Bitmap>(PuzzleGame.BOARD_SIZE)

        /*Here the scaled 750x750 image is being split up into 9
		 *250x250 blocks, these blocks are then set as the image on
		 *the various buttons*/bMapArray[0] = Bitmap.createBitmap(bMapScaled, 0, 0, 250, 250)
        bMapArray[1] = Bitmap.createBitmap(bMapScaled, 250, 0, 250, 250)
        bMapArray[2] = Bitmap.createBitmap(bMapScaled, 500, 0, 250, 250)
        bMapArray[3] = Bitmap.createBitmap(bMapScaled, 0, 250, 250, 250)
        bMapArray[4] = Bitmap.createBitmap(bMapScaled, 250, 250, 250, 250)
        bMapArray[5] = Bitmap.createBitmap(bMapScaled, 500, 250, 250, 250)
        bMapArray[6] = Bitmap.createBitmap(bMapScaled, 0, 500, 250, 250)
        bMapArray[7] = Bitmap.createBitmap(bMapScaled, 250, 500, 250, 250)
        bMapArray[8] = Bitmap.createBitmap(bMapScaled, 500, 500, 250, 250)

        //Leave first button empty and fill out rest of buttons with images
        buttonArray[0][1]!!.setImageBitmap(bMapArray[1])
        buttonArray[0][2]!!.setImageBitmap(bMapArray[2])
        buttonArray[1][0]!!.setImageBitmap(bMapArray[3])
        buttonArray[1][1]!!.setImageBitmap(bMapArray[4])
        buttonArray[1][2]!!.setImageBitmap(bMapArray[5])
        buttonArray[2][0]!!.setImageBitmap(bMapArray[6])
        buttonArray[2][1]!!.setImageBitmap(bMapArray[7])
        buttonArray[2][2]!!.setImageBitmap(bMapArray[8])
    }

    fun initializeButtonArray() {
        buttonArray[0][0] = findViewById<View>(R.id.zero) as ImageButton
        buttonArray[0][1] = findViewById<View>(R.id.one) as ImageButton
        buttonArray[0][2] = findViewById<View>(R.id.two) as ImageButton
        buttonArray[1][0] = findViewById<View>(R.id.three) as ImageButton
        buttonArray[1][1] = findViewById<View>(R.id.four) as ImageButton
        buttonArray[1][2] = findViewById<View>(R.id.five) as ImageButton
        buttonArray[2][0] = findViewById<View>(R.id.six) as ImageButton
        buttonArray[2][1] = findViewById<View>(R.id.seven) as ImageButton
        buttonArray[2][2] = findViewById<View>(R.id.eight) as ImageButton
    }

    fun shuffle() {
        moves = 0

        //enable the tiles so they can be clicked
        enableAllButtons()
        initializeButtonArray()
        val imgs = resources.obtainTypedArray(R.array.flags)
        val randResource = Random()
        val rndInt: Int = randResource.nextInt(imgs.length())
        resource = imgs.getResourceId(rndInt, 0)
        addImageToPuzzle(resource)

        //clear hashmaps denoting relationship between button and location
        buttonPressRow.clear()
        buttonPressCol.clear()
        initializeTempArrays()

        //shuffle array
        val rand = Random()
        for (i in 0 until PuzzleGame.BOARD_SIZE) {
            val randomPos: Int = rand.nextInt(PuzzleGame.BOARD_SIZE)
            val temp = numArray[i]
            val tempButton = tempButtonArray[i]
            numArray[i] = numArray[randomPos]
            tempButtonArray[i] = tempButtonArray[randomPos]
            numArray[randomPos] = temp
            tempButtonArray[randomPos] = tempButton
        }

        //set buttons in random order and update UI accordingly
        var tempIndex = 0
        for (i in 0 until PuzzleGame.BOARD_ROW) {
            for (j in 0 until PuzzleGame.BOARD_COL) {
                tpGame.mBoard[i][j] = numArray[tempIndex]
                buttonArray[i][j] = tempButtonArray[tempIndex]
                if (tpGame.mBoard[i][j] == 0) {
                    tpGame.setEmptySpaceRow(i)
                    tpGame.setEmptySpaceCol(j)
                }
                tempIndex++
                buttonPressRow[buttonArray[i][j]!!.id] = i
                buttonPressCol[buttonArray[i][j]!!.id] = j
                buttonArray[i][j]!!.x = xVal[i][j]
                buttonArray[i][j]!!.y = yVal[i][j]
            }
        }
    }

    fun shuffleButtonClick(v: View) {
        if (v.getId() === R.id.shuffleButton) {
            resetImages()
            shuffle()
        }
    }

    fun resetImages() {
        moves = 0
        buttonArray[0][0]!!.setImageBitmap(null)
        buttonArray[0][1]!!.setImageBitmap(null)
        buttonArray[0][2]!!.setImageBitmap(null)
        buttonArray[1][0]!!.setImageBitmap(null)
        buttonArray[1][1]!!.setImageBitmap(null)
        buttonArray[1][2]!!.setImageBitmap(null)
        buttonArray[2][0]!!.setImageBitmap(null)
        buttonArray[2][1]!!.setImageBitmap(null)
        buttonArray[2][2]!!.setImageBitmap(null)
    }

    fun solveButtonClick(v: View) {
        if (v.getId() === R.id.solveButton) {
            resetImages()
            addImageToPuzzle(resource)
        }
    }

    fun checkIfGameOver() {
        if (tpGame.isComplete) {
            val toast = Toast.makeText(this, "YOU WIN!!!", Toast.LENGTH_LONG)
            toast.show()
        }
    }

    fun initializeTempArrays() {
        /*initialize both temp arrays with integers/buttons
		 *0 to boardSize*/
        for (i in numArray.indices) {
            numArray[i] = i
        }
        tempButtonArray[0] = buttonArray[0][0]
        tempButtonArray[1] = buttonArray[0][1]
        tempButtonArray[2] = buttonArray[0][2]
        tempButtonArray[3] = buttonArray[1][0]
        tempButtonArray[4] = buttonArray[1][1]
        tempButtonArray[5] = buttonArray[1][2]
        tempButtonArray[6] = buttonArray[2][0]
        tempButtonArray[7] = buttonArray[2][1]
        tempButtonArray[8] = buttonArray[2][2]
    }

    fun updateMap(id: Int, row: Int, col: Int) {
        buttonPressRow[id] = PuzzleGame.emptySpaceRow
        buttonPressCol[id] = PuzzleGame.emptySpaceCol
        buttonPressRow[R.id.zero] = row
        buttonPressCol[R.id.zero] = col
    }

    fun UISwap(row: Int, col: Int) {
        buttonArray[row][col]!!.x = xVal[PuzzleGame.emptySpaceRow][PuzzleGame.emptySpaceCol]
        buttonArray[row][col]!!.y = yVal[PuzzleGame.emptySpaceRow][PuzzleGame.emptySpaceCol]
        buttonArray[PuzzleGame.emptySpaceRow][PuzzleGame.emptySpaceCol]!!.x =
            xVal[row][col]
        buttonArray[PuzzleGame.emptySpaceRow][PuzzleGame.emptySpaceCol]!!.y =
            yVal[row][col]
    }

    fun buttonArraySwap(row: Int, col: Int) {
        val temp = buttonArray[row][col]
        buttonArray[row][col] = buttonArray[PuzzleGame.emptySpaceRow][PuzzleGame.emptySpaceCol]
        buttonArray[PuzzleGame.emptySpaceRow][PuzzleGame.emptySpaceCol] = temp
    }

    fun ButtonOnClick(v: View) {
        var buttonRow = 0
        var buttonCol = 0
        if (v.getId() !== R.id.zero) {
            buttonRow = buttonPressRow[v.getId()]!!
            buttonCol = buttonPressCol[v.getId()]!!
            if (tpGame.isAdjacent(buttonRow, buttonCol)) {

                //update button mappings
                updateMap(v.getId(), buttonRow, buttonCol)

                //switch button locations on UI
                UISwap(buttonRow, buttonCol)

                //switch button locations in array to maintain synchronization
                buttonArraySwap(buttonRow, buttonCol)

                //switch in underlying integer array to maintain synchronization
                tpGame.setMove(buttonRow, buttonCol)
                tpGame.setEmptySpaceRow(buttonRow)
                tpGame.setEmptySpaceCol(buttonCol)
                moves++
                val info = findViewById<View>(R.id.info) as TextView
                info.text = "Moves so far: $moves"
                checkIfGameOver()
            }
        }
    }
}