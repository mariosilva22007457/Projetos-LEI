fun wantSpecificPiece(message: String): Boolean? {
    when (message) {
        "y", "Y" -> return true
        "n", "N" -> return false
        else -> return null
    }

}

fun isASpecificPieceValid(piece: String): Boolean {
    if (piece =="K" || piece == "k" ) {
        return true
    }
    if (piece =="H" || piece == "h" ) {
        return true
    }

    if (piece =="T" || piece == "t" ) {
        return true
    }

    if (piece =="B" || piece == "b" ) {
        return true
    }
    if (piece =="Q" || piece == "q" ) {
        return true
    }

    return piece =="P" || piece == "p"

}




fun replaceWithSpecificPiece(pieces: Array<Pair<String,String>?>, piece: String) {
    var count = 0
    do{
        if ( pieces[count] != null){
            val color = pieces[count]!!.second
            pieces[ count ] = Pair(piece,color)
        }
        count++

    } while ( pieces.size > count)

}


fun emptyString(): String {
    return ""
}

fun emptyStringSpace(): String {
    return " "
}

fun whiteBound(displayPiece: String, countLinhas: Int, numLines: Int, showPieces: Boolean, startWhite: String,
               end: String): String {
    return "$startWhite $displayPiece $end"
}

fun greyBound(displayPiece: String, countLinhas: Int, numLines: Int, showPieces: Boolean, startGrey: String,
              end: String): String {
    return "$startGrey $displayPiece $end"
}

fun invalidResponse(): String {
   return "Invalid response."
}

fun buildMenu(): String {
    return ("1-> Start New Game;\n2-> Exit Game.\n") /* Menu Principal
                                                    Termina na 2 opção    */
}

fun createInitialBoard(numColumns: Int, numLines: Int): Array<Pair<String, String>?> { //Tabuleiros de tamahos diferentes
    when //Tabuleiro 8 por 8
    {numColumns == 8 && numLines == 8 -> return arrayOf(Pair("T", "b"), Pair("H","b"),Pair("B","b"),Pair("Q","b"),
        Pair("K", "b"), Pair("B", "b"), Pair("H", "b"), Pair("T", "b"), Pair("P", "b"), Pair("P", "b"),
        Pair("P", "b"), Pair("P", "b"), Pair("P", "b"),
        Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("P", "w"),
        Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("P", "w"),
        Pair("T", "w"), Pair("H", "w"), Pair("B", "w"), Pair("K", "w"), Pair("Q", "w"), Pair("B", "w"),
        Pair("H", "w"), Pair("T", "w"))
    }
    when { //Tabuleiro 7 por 7
        numColumns == 7 && numLines == 7 -> return arrayOf(Pair("T", "b"), Pair("H", "b"), Pair("B", "b"),
            Pair("K", "b"), Pair("B", "b"), Pair("H", "b"),
            Pair("T", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"),
            Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("P", "w"),
            Pair("P", "w"), Pair("P", "w"), Pair("T", "w"), Pair("H", "w"), Pair("B", "w"), Pair("K", "w"),
            Pair("B", "w"),
            Pair("H", "w"), Pair("T", "w"))
    }
    when { //Tabuleiro 6 por 7
        numColumns == 6 && numLines == 7 -> return arrayOf(
            Pair("T", "b"), Pair("B", "b"), Pair("Q", "b"), Pair("K", "b"), Pair("B", "b"),
            Pair("H", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"),
            Pair("P", "b"), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("P", "w"),
            Pair("P", "w"), Pair("T", "w"), Pair("B", "w"), Pair("K", "w"), Pair("Q", "w"), Pair("B", "w"),
            Pair("H", "w")
        )
    }
    when { //Tabuleiro 6 por 6
        numColumns == 6 && numLines == 6 -> return arrayOf(Pair("H", "b"), Pair("B", "b"), Pair("Q", "b"), Pair("K", "b"),
            Pair("B", "b"), Pair("T", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"),
            Pair("P", "b"), null,
            null, null, null, null, null, null, null, null, null, null, null, Pair("P", "w"), Pair("P", "w"),
            Pair("P", "w"), Pair("P", "w"),
            Pair("P", "w"), Pair("P", "w"),
            Pair("H", "w"), Pair("B", "w"), Pair("K", "w"), Pair("Q", "w"), Pair("B", "w"), Pair("T", "w"))
    }
    when { //Tabuleiro 4 por 4
        numColumns == 4 && numLines == 4 -> return arrayOf(
            null, null, Pair("T", "b"), Pair("B", "b"), null, null, null, null, null, null, null, null,
            Pair("T", "w"), Pair("Q", "w"), null, null)
    }
    return arrayOf()
}


fun createTotalPiecesAndTurn(numColumns: Int, numLines: Int): Array<Int?> { // Numero de Pecas do Array createTotalPiecesAndTurn

    when {
        (numColumns == 8 && numLines == 8) -> return arrayOf(16, 16, 0)
        (numColumns == 7 && numLines == 7) -> return arrayOf(14, 14, 0)
        (numColumns == 6 && numLines == 7) -> return arrayOf(12, 12, 0)
        (numColumns == 6 && numLines == 6) -> return arrayOf(12, 12, 0)
        (numColumns == 4 && numLines == 4) -> return arrayOf(2, 2, 0)
        else -> return arrayOf()
    }
}


fun convertStringToUnicode(piece: String, color: String): String {
    if ((piece == "P" || piece == "p" ) && color == "w") { //Knight_white
        return "\u2659"
    }
    if ((piece == "P"  || piece == "p")  && color == "b") { //Knight_black
        return "\u265F"
    }
    if ((piece == "K" || piece == "k")  && color == "w") { // King_white
        return "\u2654"
    }
    if ((piece == "K" || piece == "k")  && color == "b") { //King_black
        return "\u265A"
    }

    if ((piece == "B" || piece == "b") && color == "w") { //Bishop_white
        return "\u2657"
    }
    if ((piece == "B" || piece == "b")  && color == "b") { //Bishop_black
        return "\u265D"
    }

    if ((piece == "H" || piece == "h")  && color == "w") { //Horse_white
        return "\u2658"
    }
    if ((piece == "H" || piece == "h") && color == "b") { // Horse_black
        return "\u265E"
    }

    if ((piece == "T" || piece == "t")  && color == "w") { // Tower_white
        return "\u2656"
    }
    if ( (piece == "T" || piece == "t") && color == "b") { // Tower_black
        return "\u265C"
    }

    if ((piece == "Q" || piece == "q") && color == "w") { // Queen_white
        return "\u2655"
    }
    if ((piece == "Q" || piece == "q") && color == "b") { // Queen_black
        return "\u265B"
    } else {
        return " "
    }
}


fun getCoordinates(readText: String?): Pair<Int, Int>? {
    var coordinates = 0
    if (readText == null || readText.length != 2 || readText[0].toInt() !in 48..57 && (readText[1] !in 'A'..'H' ||
                readText[1] !in 'a'..'h')) {
        return null
    } else if (readText[1] == 'A' || readText[1] == 'a') {
        coordinates = 1
    } else if (readText[1] == 'B' || readText[1] == 'b') {
        coordinates = 2
    } else if (readText[1] == 'C' || readText[1] == 'c') {
        coordinates = 3
    } else if (readText[1] == 'D' || readText[1] == 'd') {
        coordinates = 4
    } else if (readText[1] == 'E' || readText[1] == 'e') {
        coordinates = 5
    } else if (readText[1] == 'F' || readText[1] == 'f') {
        coordinates = 6
    } else if (readText[1] == 'G' || readText[1] == 'g') {
        coordinates = 7
    } else if (readText[1] == 'H' || readText[1] == 'h') {
        coordinates = 8
    }

    return Pair(readText[0].toInt() - 48, coordinates)
}


fun checkRightPieceSelected(pieceColor: String, turn: Int): Boolean {
    when {
        (pieceColor == "b" && turn == 1) -> return true //second Player
        (pieceColor == "w" && turn == 0) -> return true // first player
        else -> return false
    }
}


fun isCoordinateInsideChess(coord: Pair<Int, Int>, numColumns: Int, numLines: Int): Boolean {
    if (coord.first in 1..numLines && coord.second in 1..numColumns) {
        return true
    } else {
        return false
    }
}

fun isKnightValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>, pieces: Array<Pair<String, String>?>,
                  numColumns: Int, numLines: Int): Boolean {
    val movmentCoord = ((((currentCoord.first - 1) * numLines) + currentCoord.second) - 1)
    val attackCoord = ((((targetCoord.first - 1) * numLines) + targetCoord.second) - 1)

    if ((currentCoord.first + 1 == targetCoord.first && currentCoord.second == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first - 1 == targetCoord.first && currentCoord.second == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    } else {
        return false
    }
}

fun isHorseValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>, pieces: Array<Pair<String,
        String>?>, numColumns: Int, numLines: Int): Boolean {
    val movmentCoord = ((((currentCoord.first - 1) * numLines) + currentCoord.second) - 1)
    val attackCoord = ((((targetCoord.first - 1) * numLines) + targetCoord.second) - 1)

    if ((currentCoord.first + 2 == targetCoord.first && currentCoord.second + 1 == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first + 2 == targetCoord.first && currentCoord.second - 1 == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first - 2 == targetCoord.first && currentCoord.second - 1 == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first - 2 == targetCoord.first && currentCoord.second + 1 == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first + 1 == targetCoord.first && currentCoord.second + 2 == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first + 1 == targetCoord.first && currentCoord.second - 2 == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first - 1 == targetCoord.first && currentCoord.second - 2 == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first - 1 == targetCoord.first && currentCoord.second + 2 == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    return false
}

fun isKingValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>, pieces: Array<Pair<String,
        String>?>, numColumns: Int, numLines: Int): Boolean {
    val movmentCoord = ((((currentCoord.first - 1) * numLines) + currentCoord.second) - 1)
    val attackCoord = ((((targetCoord.first - 1) * numLines) + targetCoord.second) - 1)

    if ((currentCoord.first + 1 == targetCoord.first && currentCoord.second == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first - 1 == targetCoord.first && currentCoord.second == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first == targetCoord.first && currentCoord.second + 1 == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first == targetCoord.first && currentCoord.second - 1 == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first + 1 == targetCoord.first && currentCoord.second + 1 == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first + 1 == targetCoord.first && currentCoord.second - 1 == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first - 1 == targetCoord.first && currentCoord.second - 1 == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first - 1 == targetCoord.first && currentCoord.second + 1 == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    } else {
        return false
    }
}

fun isTowerValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>, pieces: Array<Pair<String,
        String>?>, numColumns: Int, numLines: Int): Boolean {
    val movmentCoord = ((((currentCoord.first - 1) * numLines) + currentCoord.second) - 1)
    val attackCoord = ((((targetCoord.first - 1) * numLines) + targetCoord.second) - 1)

    if ((currentCoord.first == targetCoord.first && currentCoord.second != targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    }
    if ((currentCoord.first != targetCoord.first && currentCoord.second == targetCoord.second)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    } else {
        return false
    }
}

fun isBishopValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>, pieces: Array<Pair<String,
        String>?>, numColumns: Int, numLines: Int): Boolean {
    var counter = 1
    val movmentCoord = ((((currentCoord.first - 1) * numLines) + currentCoord.second) - 1)
    val attackCoord = ((((targetCoord.first - 1) * numLines) + targetCoord.second) - 1)
    val getBishop = (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
            || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")
    do {
        if ((currentCoord.first + counter == targetCoord.first && currentCoord.second + counter == targetCoord.second)
            && getBishop) {
            return true
        }
        if ((currentCoord.first - counter == targetCoord.first && currentCoord.second - counter == targetCoord.second)
            && getBishop) {
            return true
        }
        if ((currentCoord.first - counter == targetCoord.first && currentCoord.second + counter == targetCoord.second)
            && getBishop) {
            return true
        }
        if ((currentCoord.first + counter == targetCoord.first && currentCoord.second - counter == targetCoord.second)
            && getBishop) {
            return true
        }
        counter++
    } while (counter < numColumns)
    return false
}

fun isQueenValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>, pieces: Array<Pair<String,
        String>?>, numColumns: Int, numLines: Int): Boolean {
    val movmentCoord = ((((currentCoord.first - 1) * numLines) + currentCoord.second) - 1)
    val attackCoord = ((((targetCoord.first - 1) * numLines) + targetCoord.second) - 1)
    if (isBishopValid(currentCoord, targetCoord, pieces, numColumns, numLines)
        || isTowerValid(currentCoord, targetCoord, pieces, numColumns, numLines)
        && (pieces[movmentCoord]?.second == "w" && pieces[attackCoord]?.second != "w")
        || (pieces[movmentCoord]?.second == "b" && pieces[attackCoord]?.second != "b")) {
        return true
    } else {
        return false
    }
}


fun isValidTargetPiece(currentSelectedPiece: Pair<String, String>, currentCoord: Pair<Int, Int>,
                       targetCoord: Pair<Int, Int>,
                       pieces: Array<Pair<String, String>?>, numColumns: Int, numLines: Int): Boolean {
    if (currentSelectedPiece.first == "P" && isKnightValid(currentCoord, targetCoord, pieces, numColumns, numLines)) {
        return true
    }
    if (currentSelectedPiece.first == "H" && isHorseValid(currentCoord, targetCoord, pieces, numColumns, numLines)) {
        return true
    }
    if (currentSelectedPiece.first == "K" && isKingValid(currentCoord, targetCoord, pieces, numColumns, numLines)) {
        return true
    }
    if (currentSelectedPiece.first == "Q" && isQueenValid(currentCoord, targetCoord, pieces, numColumns, numLines)) {
        return true
    }
    if (currentSelectedPiece.first == "T" && isTowerValid(currentCoord, targetCoord, pieces, numColumns, numLines)) {
        return true
    }
    if (currentSelectedPiece.first == "B" && isBishopValid(currentCoord, targetCoord, pieces, numColumns, numLines)) {
        return true
    } else {
        return false
    }
}


fun movePiece(pieces: Array<Pair<String, String>?>, numColumns: Int, numLines: Int, currentCoord: Pair<Int, Int>,
              targetCoord: Pair<Int, Int>,
              totalPiecesAndTurn: Array<Int>): Boolean {


    val attackCoordinates = ((((targetCoord.first - 1) * numLines) + targetCoord.second) - 1)
    val movmentCoordinates = ((((currentCoord.first - 1) * numLines) + currentCoord.second) - 1)
    val chosenPiece = pieces[movmentCoordinates]
    if (isValidTargetPiece(chosenPiece!!, currentCoord, targetCoord, pieces, numColumns, numLines)
        && checkRightPieceSelected(pieces[movmentCoordinates]?.second.toString(), totalPiecesAndTurn[2])) {
        when {
            (pieces[attackCoordinates] != null && totalPiecesAndTurn[2] == 0) -> totalPiecesAndTurn[1]--
            (pieces[attackCoordinates] != null && totalPiecesAndTurn[2] == 1) -> totalPiecesAndTurn[0]--
        }
        pieces[attackCoordinates] = pieces[movmentCoordinates]
        pieces[movmentCoordinates] = null
        when {
            (totalPiecesAndTurn[2] == 0) -> totalPiecesAndTurn[2] = 1
            else -> totalPiecesAndTurn[2] = 0
        }

        return true
    }
    return false
}

fun chooseAPieceText(): String {
    return ", choose a piece (e.g 2D).\n" +
            "Menu-> m;\n"
}

fun attackChoice(): String {
    return ", choose a target piece (e.g 2D).\n" +
            "Menu-> m;\n"
}


fun startNewGame(whitePlayer: String, blackPlayer: String, pieces: Array<Pair<String, String>?>,
                 totalPiecesAndTurn: Array<Int>, numColumns: Int, numLines: Int, showLegend: Boolean = false,
                 showPieces: Boolean = false) {
    val player1Option = "$whitePlayer${chooseAPieceText()}";
    val player2Option = "$blackPlayer${chooseAPieceText()}"
    val player1Attack = "$whitePlayer${attackChoice()}";
    val player2Attack = "$blackPlayer${attackChoice()}"
    var moveP1Attack = ""
    var moveP2Attack = ""
    var color = emptyString()
    while (totalPiecesAndTurn[0] != 0 && totalPiecesAndTurn[1] != 0) {
        println(buildBoard(numColumns, numLines, showLegend, showPieces, pieces))
        if (totalPiecesAndTurn[2] == 0) {
            println(player1Option)
            val move1 = readLine().toString()
            if (move1 == "m" || move1 == "M") { return
            } else { if (move1.length == 2) {
                val pecaCoordMov = ((((getCoordinates(move1)!!.first - 1) * numLines) + getCoordinates(move1)!!.second) - 1)
                when {(pecaCoordMov in 0..(numLines * numColumns - 1) && pieces[pecaCoordMov] == null) -> color =emptyString()
                    (pecaCoordMov in 0..(numLines * numColumns - 1)) -> color = pieces[pecaCoordMov]!!.second }
                if ((isCoordinateInsideChess(getCoordinates(move1)!!, numColumns, numLines)
                            && move1.length == 2 && checkRightPieceSelected(color, totalPiecesAndTurn[2]))
                    || (move1[0].toString() in "1"..numLines.toString()) &&
                    (move1[1].toString() in "A"..(numColumns + 64).toString()
                            || move1[1].toString() in "a"..(numColumns + 96).toString())) {
                    if (isCoordinateInsideChess(getCoordinates(move1)!!, numColumns, numLines)
                        && checkRightPieceSelected(color, totalPiecesAndTurn[2])) { println(player1Attack)
                        moveP1Attack = readLine().toString()
                        if (moveP1Attack == "m" || moveP1Attack == "M") { return}
                        val gcT = getCoordinates(moveP1Attack)
                        if (gcT == null) { println(invalidResponse())
                        } else if (isCoordinateInsideChess(gcT, numColumns, numLines)
                            && moveP1Attack.length == 2 ||moveP1Attack[0].toString() in "1"..numLines.toString()
                            && (moveP1Attack[1].toString() in "A"..(numColumns + 64).toString()
                                    || moveP1Attack[1].toString() in "a"..(numColumns + 96).toString())) {
                            if (!movePiece(pieces, numColumns, numLines, getCoordinates(move1)!!,
                                    getCoordinates(moveP1Attack)!!,totalPiecesAndTurn)) { println(invalidResponse()) } } }
                    else { println(invalidResponse()) }
                } else { println(invalidResponse()) }
            } else { println(invalidResponse()) } }
        } else { println(player2Option)
            val move2 = readLine().toString()
            if (move2 == "m" || move2 == "M") { return } else { if (move2.length == 2) {
                val pecaCoordMov = ((((getCoordinates(move2)!!.first - 1) * numLines)
                        + getCoordinates(move2)!!.second) - 1)
                when {(pecaCoordMov in 0..(numLines * numColumns - 1) &&
                        pieces[pecaCoordMov] == null) -> color = emptyString()
                    (pecaCoordMov in 0..(numLines * numColumns - 1)) -> color = pieces[pecaCoordMov]!!.second }
                if ((isCoordinateInsideChess(getCoordinates(move2)!!, numColumns, numLines)
                            && move2.length == 2 && checkRightPieceSelected(color, totalPiecesAndTurn[2]))
                    || (move2[0].toString() in "1"..numLines.toString()) &&
                    (move2[1].toString() in "A"..(numColumns + 64).toString()
                            || move2[1].toString() in "a"..(numColumns + 96).toString())) {
                    if (isCoordinateInsideChess(getCoordinates(move2)!!, numColumns, numLines)
                        && checkRightPieceSelected(color, totalPiecesAndTurn[2])) { println(player2Attack)
                        moveP2Attack = readLine().toString()
                        if (moveP2Attack == "m" || moveP2Attack == "M") { return }
                        val gcT = getCoordinates(moveP2Attack)
                        if (gcT == null) { println(invalidResponse()) }
                        else if (isCoordinateInsideChess(gcT, numColumns, numLines)
                            && moveP2Attack.length == 2 ||moveP2Attack[0].toString() in "1"..numLines.toString()
                            && (moveP2Attack[1].toString() in "A"..(numColumns + 64).toString()
                                    || moveP2Attack[1].toString() in "a"..(numColumns + 96).toString())) {
                            if (!movePiece(pieces, numColumns, numLines, getCoordinates(move2)!!,
                                    getCoordinates(moveP2Attack)!!,totalPiecesAndTurn)){println(invalidResponse()) } }
                    } else { println(invalidResponse()) }
                } else { println(invalidResponse()) }
            } else { println(invalidResponse()) } } } }
    when {(totalPiecesAndTurn[0] == 0) -> println("Congrats! $blackPlayer wins!")
        (totalPiecesAndTurn[1] == 0) -> println("Congrats! $whitePlayer wins!") }
}



fun buildBoard(numColumns: Int, numLines: Int, showLegend: Boolean = false, showPieces: Boolean = false, /* Retorna info
                                                                                  do tabuleiro */
               pieces: Array<Pair<String, String>?>): String {
    val esc: String = Character.toString(27);
    val end = "$esc[0m";
    val startBlue = "$esc[30;44m";
    val startGrey = "$esc[30;47m";
    val startWhite = "$esc[30;30m";
    var counterOfColuns: Int
    var counterOfLines = 0;
    var displaygame = emptyString();
    var symbol = 'A';
    var counterOfPieces = 0;
    val emptyEdge = "$startBlue   $end";
    var displayPiece = emptyString();
    val blueStripes = when {(showLegend) -> 2 else -> 0 }
    while (counterOfLines < numLines + blueStripes) {
        counterOfColuns = 0
        if (showLegend && (counterOfLines == 0 || counterOfLines == numLines + 1)) {
            while (counterOfColuns < numColumns + 2) {
                if (counterOfLines == 0) {
                    if (counterOfColuns == 0 || counterOfColuns == numColumns + 1) {
                        displaygame += emptyEdge
                    } else {
                        displaygame += "$startBlue $symbol $end"
                        symbol++ }
                } else if (counterOfLines == numLines + 1) {
                    displaygame += emptyEdge
                };counterOfColuns++ } }
        when {(showLegend && counterOfColuns == 0) -> displaygame += "$startBlue $counterOfLines $end" }
        while (counterOfColuns < numColumns) {
            displayPiece = if (showPieces && pieces[counterOfPieces] == null) { emptyStringSpace()
            } else if (showPieces) {
                convertStringToUnicode(pieces[counterOfPieces]?.first.toString(),
                    pieces[counterOfPieces]?.second.toString())
            } else { emptyStringSpace() }
            if (showLegend) {
                displaygame += if (counterOfColuns % 2 == 0) {
                    when {(counterOfLines % 2 == 0) -> whiteBound(displayPiece,
                        counterOfLines,numLines,showPieces,startGrey,end)
                        else -> greyBound(displayPiece, counterOfLines, numLines, showPieces, startWhite, end) }
                } else {
                    when {(counterOfLines % 2 == 1) -> greyBound(displayPiece,counterOfLines,
                        numLines,showPieces,startGrey,end)
                        else -> whiteBound(displayPiece, counterOfLines, numLines, showPieces, startWhite, end) }
                };counterOfColuns++;counterOfPieces++
            }
            if (!showLegend) {
                if (counterOfColuns % 2 == 0) {
                    when {(counterOfLines % 2 == 0) ->
                        displaygame += whiteBound(displayPiece, counterOfLines,numLines,showPieces, startWhite, end)
                        else -> displaygame += greyBound(displayPiece, counterOfLines,numLines,showPieces,startGrey,end) }
                } else {
                    when {(counterOfLines % 2 == 1) ->
                        displaygame += greyBound(displayPiece, counterOfLines, numLines, showPieces, startWhite,end)
                        else -> displaygame += whiteBound(displayPiece,counterOfLines,numLines,showPieces,startGrey,end) }
                };counterOfColuns++;counterOfPieces++ } }
        when {(showLegend && counterOfColuns == numColumns) -> displaygame += emptyEdge
        };displaygame += "\n";counterOfLines++ }
    return displaygame }


fun checkIsNumber(number: String): Boolean {
    return number.toIntOrNull() != null
}

fun checkName(username: String): Boolean {
    var space = 0
    var counter = 0
    if(username[0] == 'B'){
        println("Aviso\n")
    }
    while (counter < username.length - 1) {
        if (username[counter] == ' ') {
            space++
            if ((username[0] in 'A'..'Z') && (username[counter + 1] in 'A'..'Z') && space == 1) {
                return true
            }

        }
        counter++
    }

    return false
}

fun showChessLegendOrPieces(message: String): Boolean? {
    return when {
        (message == "y" || message == "Y") -> true
        (message == "n" || message == "N") -> true
        else -> false
    }
}

fun tabuelreiro(numLines: Int, numColumns: Int): Boolean {
    if (numLines == 8 && numColumns == 8) {
        return true
    }
    if (numLines == 7 && numColumns == 7) {
        return true
    }

    if (numLines == 6 && numColumns == 7) {
        return true
    }

    if (numLines == 6 && numColumns == 6) {
        return true
    }
    if (numLines == 4 && numColumns == 4) {
        return true
    }
    return false
}

fun main() { //inicio
    var choice: Int? = 1
    var numLines: String = emptyString()
    var message : String = emptyString()
    var piece : String = emptyString()
    var bool = false
    println("Welcome to the Chess Board Game!")
    do { println(buildMenu())
        choice = readLine()?.toIntOrNull()
        if (choice == 1) {
            var firstPlayer: String = emptyString()
            do { println("First player name?\n")
                firstPlayer = readLine().toString()
                bool = checkName(firstPlayer)
                if (!bool) {
                    println(invalidResponse()) }
            } while (!bool)
            var secondPlayer = emptyString()
            do { println("Second player name?\n")
                secondPlayer = readLine().toString()
                if (!checkName(secondPlayer)) {
                    println(invalidResponse()) }
            } while (!checkName(secondPlayer))
            var numColumns: String = emptyString()
            do {
                do { println("How many chess columns?\n")
                    numColumns = readLine().toString()
                    if (!checkIsNumber(numColumns) || numColumns.toInt() !in 4..8) {
                        println(invalidResponse()) }
                } while (!checkIsNumber(numColumns) || numColumns.toInt() !in 4..8)
                println("How many chess lines?\n")
                numLines = readLine().toString()
                if (!tabuelreiro(numLines.toInt(), numColumns.toInt())) {
                    println(invalidResponse()) }
            } while (!tabuelreiro(numLines.toInt(), numColumns.toInt()))
            var showLegend: String
            val pieces: Array<Pair<String, String>?> = createInitialBoard(numColumns.toInt(), numLines.toInt())
            do { println("Show legend (y/n)?\n")
                showLegend = readLine().toString()
                if (showChessLegendOrPieces(showLegend) == null) {
                    println(invalidResponse()) }
            } while (showChessLegendOrPieces(showLegend) == null)
            var showPieces: String
            do { println("Show pieces (y/n)?\n")
                showPieces = readLine().toString()
                if (showChessLegendOrPieces(showPieces) == null) {
                    println(invalidResponse()) }
            } while (showChessLegendOrPieces(showPieces) == null)
            do { println("Do you want to play with only one type of piece on the board? (y/n)?\n")
                message = readLine().toString()
                if (wantSpecificPiece(message) == null){
                    println(invalidResponse())
                } } while( wantSpecificPiece(message)== null )
            if ( wantSpecificPiece(message) == true) {
                do { println("Choose a specific piece.\n")
                    piece = readLine().toString()
                    if (!isASpecificPieceValid(piece)) {
                        println(invalidResponse())
                    } } while ((!isASpecificPieceValid(piece)))
                replaceWithSpecificPiece(pieces,piece) }
            val totalPiecesAndTurn = createTotalPiecesAndTurn(numColumns.toInt(), numLines.toInt())
            startNewGame(firstPlayer, secondPlayer, pieces, totalPiecesAndTurn as Array<Int>, numColumns.toInt(),
                numLines.toInt(), showChessLegendOrPieces(showLegend)
                    ?: false, showChessLegendOrPieces(showPieces) ?: false) }
    } while (choice != 2)
println("Ola\n")}


