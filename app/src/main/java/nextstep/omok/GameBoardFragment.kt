package nextstep.omok

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import androidx.fragment.app.Fragment

class GameBoardFragment: Fragment() {
    private lateinit var board: TableLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.board, container, false)
        setupBoard(view)
        return view
    }

    private fun setupBoard(view: View) {
        board = view.findViewById(R.id.board)
        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, row ->
            row.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, cell ->
                cell.setOnClickListener {
                    Log.d("testt", "${rowIndex}행 ${colIndex}눌림")
                    handleCellClick(rowIndex, colIndex)
                }
            }
        }
    }

    private fun handleCellClick(x: Int, y: Int) {
        if (GameModel.placeStone(x, y)) {
            Log.d("testt", "${x} ${y} 돌 놓기 성공")
            updateCell(x, y, GameModel.currentPlayer)
            if (GameModel.checkWinCondition()) {
                GameModel.handelWin()
            } else {
                GameModel.switchPlayer()
                (activity as? BoardActivity)?.updateTurnImage()
            }
        }
    }

    fun updateCell(x: Int, y: Int, player: Player) {
        val row = board.getChildAt(x) as? TableRow
        val cell = row?.getChildAt(y) as? ImageView
        cell?.setImageResource(player.stoneResId)
    }
}