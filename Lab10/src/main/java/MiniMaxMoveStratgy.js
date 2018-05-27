var board = new Array();
var BOARD_SIZE_SQRT;
var BOARD_SIZE;
var UNOCCUPIED;
var HUMAN_PLAYER;
var COMPUTER_PLAYER;
var active_turn;
var aiPlayer;
var humanPlayer;
var choice;

var BoardType = Java.type('Lab10.TicTacToe.Core.Board');
var BoardPosition = Java.type('Lab10.TicTacToe.Core.BoardPosition');
var intType = Java.type("int");

var toWinInARow;

function minimaxMoveStrategy(currentPlayer, enemyPlayer, boardJava) {
    BOARD_SIZE_SQRT = boardJava.getSize();
	BOARD_SIZE = BOARD_SIZE_SQRT * BOARD_SIZE_SQRT;
	aiPlayer = currentPlayer;
	humanPlayer = enemyPlayer;

    toWinInARow = boardJava.SAME_TIC_TAC_TOE_VALUE_WINNER_LENGTH;

    HUMAN_PLAYER = humanPlayer.getValue();
    COMPUTER_PLAYER = aiPlayer.getValue();
    UNOCCUPIED = null;

    //prepare
    for (var i = 0; i < BOARD_SIZE; i++)
    {
        var x = i % BOARD_SIZE_SQRT;
        var y = Math.floor(i / BOARD_SIZE_SQRT);

        var pos = boardJava.getPos(x, y);
        board[i] = pos.getValue();
    }

    active_turn = "COMPUTER";
    choice = null;

	minimax(board, 0);

    print(choice);

	return intToBoardPosition(choice);
}

function score(game, depth) {
    var score = CheckForWinner(game);
    if (score === 1)
        return 0;
    else if (score === 2)
        return depth-(BOARD_SIZE + 1);
    else if (score === 3)
        return (BOARD_SIZE + 1)-depth;
}

function minimax(tempBoardGame, depth) {
    if (CheckForWinner(tempBoardGame) !== 0)
        return score(tempBoardGame, depth);
    
    depth += 1;
    var scores = new Array();
    var moves = new Array();
    var availableMoves = GetAvailableMoves(tempBoardGame);
    var move, possible_game;
    for(var i=0; i < availableMoves.length; i++) {
        move = availableMoves[i];
        possible_game = GetNewState(move, tempBoardGame);
        scores.push(minimax(possible_game, depth));
        moves.push(move);
        tempBoardGame = UndoMove(tempBoardGame, move);
    }

    var max_score, max_score_index, min_score, min_score_index;
    if (active_turn === "COMPUTER") {
        max_score = Math.max.apply(Math, scores);
        max_score_index = scores.indexOf(max_score);
        choice = moves[max_score_index];
        return scores[max_score_index];

    } else {
        min_score = Math.min.apply(Math, scores);
        min_score_index = scores.indexOf(min_score);
        choice = moves[min_score_index];
        return scores[min_score_index];
    }
}

function UndoMove(game, move) {
    game[move] = UNOCCUPIED;
    ChangeTurn();
    return game;
}

function GetNewState(move, game) {
    var piece = ChangeTurn();
    game[move] = piece;
    return game;
}

function ChangeTurn() {
    var piece;
    if (active_turn === "COMPUTER") {
        piece = aiPlayer.getValue();
        active_turn = "HUMAN";
    } else {
        piece = humanPlayer.getValue();
        active_turn = "COMPUTER";
    }
    return piece;
}

function GetAvailableMoves(game) {
    var possibleMoves = new Array();
    for (var i = 0; i < BOARD_SIZE; i++)
        if (game[i] === UNOCCUPIED)
            possibleMoves.push(i);
    return possibleMoves;
}

// Check for a winner.  Return
//   0 if no winner or tie yet
//   1 if it's a tie
//   2 if HUMAN_PLAYER won
//   3 if COMPUTER_PLAYER won
function CheckForWinner(game) {
    var b = new BoardType(BOARD_SIZE_SQRT, toWinInARow);
    for (var i = 0; i < game.length; i++)
    {
        var x = i % BOARD_SIZE_SQRT;
        var y = Math.floor(i / BOARD_SIZE_SQRT);

        b.tryMarkField(x, y, game[i]);
    }


    var isAiPlayerWinner = b.isPlayerWinner(aiPlayer);
    var isHumanPlayerWinner = b.isPlayerWinner(humanPlayer);
    var areAllMarked = b.areAllFieldsMarked();

    if(!isAiPlayerWinner && !isHumanPlayerWinner && !areAllMarked)
        return 0;
    else if(!isAiPlayerWinner && !isHumanPlayerWinner && areAllMarked)
        return 1;
    else if(isHumanPlayerWinner)
        return 2;
    else if(isAiPlayerWinner)
        return 3;
}


function sleep(){
    for(var j = 0; j < 10000 * 10000; j++){}
}


function intToBoardPosition(i){
    var x = i % BOARD_SIZE_SQRT;
    var y = Math.floor(i / BOARD_SIZE_SQRT);

    return new BoardPosition(x, y);
}