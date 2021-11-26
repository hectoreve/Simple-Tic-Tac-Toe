package tictactoe

var status = "         "

fun main() {
    println(playGame())
}

fun playGame(): String {
    var result: String
    var count = 0
    printBoard()
    do {
        playCharAt(
            if (count % 2 == 0) {
                count++
                'X'
            } else {
                count++
                'O'
            }
        )
        printBoard()
        result = checkBoard()!!
    } while (result == "Game not finished")
    return result
}

fun playCharAt(symbol: Char){
    while (true) {
        println("Enter the coordinates:")
        val coordinates = readLine()!!.split(" ")
        if (validSize(coordinates) && areNumbers(coordinates)) {
            val coordinatesNum = coordinates.map { it.toInt() }
            val a = coordinatesNum[0]
            val b = coordinatesNum[1]
            if (inValidRange(a, b)) {
                if (canPlayAt(a, b, status)) {
                    val index = (a - 1) * 3 + b - 1
                    status = status.replaceRange(index, index + 1, symbol.toString())
                    break
                } else {
                    println("This cell is occupied! Choose another one!")
                }
            } else {
                println("Coordinates should be from 1 to 3!")
            }
        } else {
            println("You should enter numbers!")
        }
    }
}


//Check if not numbers
fun areNumbers(a: List<String>) =
    a[0].toIntOrNull() != null && a[1].toIntOrNull() != null

//Check if the size is 2
fun validSize(a: List<String>) = a.size == 2

//check valid range
fun inValidRange(a: Int, b: Int) = a in 1..3 && b in 1..3

//Funcion para determinar si se puede jugar en la
// casilla seleccionada en las coodenadas pasadas
fun canPlayAt(a: Int, b: Int, string: String): Boolean {
    val index = ((a - 1) * 3) + (b - 1)
    return string[index] == ' '
}

//Imprime el tablero al haberse pasado un String con las posiciones
fun printBoard() {
    println(
        """
        ---------
        | ${status[0]} ${status[1]} ${status[2]} |
        | ${status[3]} ${status[4]} ${status[5]} |
        | ${status[6]} ${status[7]} ${status[8]} |
        ---------""".trimIndent()
    )
}

fun checkBoard(): String? {
    val analyzed = analyzeBoard()
    return if (!validBoard()) "Impossible"
    else if (analyzed == null) "Impossible"
    else if (analyzed != "No Winner") analyzeBoard()
    else if (checkDraw()) "Draw"
    else "Game not finished"
}

fun checkDraw(): Boolean = status.none { it == ' ' }


fun validBoard(): Boolean {
    val difference = (status.count { it == 'X' } - status.count { it == 'O' })
    return difference <= 1 && difference >= -1
}

fun checkEquals(a: Char, b: Char, c: Char) = a == b && b == c


//Check if some of the players is a winner
fun analyzeBoard(): String? {
    var winnerString: String? = "No Winner"
    var counter = 0
    if (checkEquals(status[0], status[4], status[8]) && status[0] != ' ') {
        winnerString = "${status[0]} wins"
        counter++
    }
    if (checkEquals(status[0], status[3], status[6]) && status[0] != ' ') {
        winnerString = "${status[0]} wins"
        counter++
    }
    if (checkEquals(status[0], status[1], status[2]) && status[0] != ' ') {
        winnerString = "${status[0]} wins"
        counter++
    }
    if (checkEquals(status[1], status[4], status[7]) && status[1] != ' ') {
        winnerString = "${status[1]} wins"
        counter++
    }
    if (checkEquals(status[2], status[4], status[6]) && status[2] != ' ') {
        winnerString = "${status[2]} wins"
        counter++
    }
    if (checkEquals(status[2], status[5], status[8]) && status[2] != ' ') {
        winnerString = "${status[2]} wins"
        counter++
    }
    if (checkEquals(status[3], status[4], status[5]) && status[3] != ' ') {
        winnerString = "${status[3]} wins"
        counter++
    }
    if (checkEquals(status[6], status[7], status[8]) && status[6] != ' ') {
        winnerString = "${status[6]} wins"
        counter++
    }
    return if (counter > 1) null else winnerString
}
