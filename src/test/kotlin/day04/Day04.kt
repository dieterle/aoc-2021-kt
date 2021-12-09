package day04

import utils.solvePuzzle

data class BingoBoard (val board: List<List<Pair<Int, Boolean>>>) {

    fun mark(draw: Int) : BingoBoard = BingoBoard(board.map { it.map { elem -> if(draw == elem.first) elem.copy(second = true) else elem } })

    fun check(draw: Int) : Int? =
        if (board.any{ it.all { elem -> elem.second }} or
            (0..4).any{ board.map { line -> line.elementAt(it) }.all { elem -> elem.second }})
            board
                .map { it.filter{ elem -> !elem.second }
                    .sumOf { elem -> elem.first }}
                .sum() * draw
        else null
    
    companion object {
        fun new(rawBoard: List<String>) = BingoBoard(rawBoard.map { it.split(" ").filter { elem -> !elem.isEmpty() }.map { number -> Pair(number.toInt(), false) } }.toList())
    }

}

fun solveFirstPuzzle(input: Sequence<String>): Long {
    var inputList = input.toList()
    val draws = inputList.first().split(",").map { it.toInt() }
    var bingoBoards = inputList.drop(1).withIndex().groupBy { it.index / 6 }.map { it.value.map { elem -> elem.value } }.map { it.drop(1) }.map { BingoBoard.new(it) }
    for (draw in draws) {
        bingoBoards = bingoBoards.map { it.mark(draw) }
        val winningBoards = bingoBoards.map { it.check(draw) }.filterNotNull()
        if(winningBoards.count() > 0)
            return winningBoards.first().toLong()
    }
    return -1
}

fun solveSecondPuzzle(input: Sequence<String>): Long =
    TODO()

fun main() {
    println("answer 1: ${solvePuzzle(4, ::solveFirstPuzzle)}")
//    println("answer 2: ${solvePuzzle(4, ::solveSecondPuzzle)}")
}
