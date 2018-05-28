
var choice;
var boardSize;
var aiPlayer;
var humanPlayer;
var active_turn;

var intelligence = 3;


function minimaxMoveStrategy(currentPlayer, enemyPlayer, board) {
    boardSize = board.getSize();
    aiPlayer = currentPlayer;
    humanPlayer = enemyPlayer;
    active_turn = "AI";
    choice = null;

    var BoardPosition = Java.type('Lab10.TicTacToe.Core.BoardPosition');

    minimax(board, 0);

    return choice;
}



function minimax(tempBoardGame, depth) {

    if(depth > intelligence){
        //print('depth > intelligence ' + depth);
        return 0;
    }

    if (CheckForWinner(tempBoardGame) !== 0)
        return score(tempBoardGame, depth);

    depth += 1;
    var scores = new Array();
    var moves = new Array();
    var availableMoves = GetAvailableMoves(tempBoardGame);
    var move, possible_game;
    for(var i = 0; i < availableMoves.length; i++) {
        move = availableMoves[i];
        possible_game = GetNewState(move, tempBoardGame);
        scores.push(minimax(possible_game, depth));
        moves.push(move);
        tempBoardGame = UndoMove(tempBoardGame, move);
    }
    
    var max_score, max_score_index, min_score, min_score_index;
    if (active_turn === "AI") {
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

function GetAvailableMoves(board) {
    var possibleMoves = new Array();

    for (var i = 0; i < boardSize; i++){
        for(var j = 0; j < boardSize; j++){
            var pos = board.getPos(j, i);

            if(pos.getValue() == null) {
                possibleMoves.push(pos);
            }
        }
    }

    return possibleMoves;
}

function GetNewState(pos, board) {
    var piece = ChangeTurn();

    board.tryMarkField(pos.getX(), pos.getY(), piece);

    return board;
}

function ChangeTurn() {
    var piece;
    if (active_turn === "AI") {
        piece = aiPlayer.getValue();
        active_turn = "HUMAN";
    } else {
        piece = humanPlayer.getValue();
        active_turn = "AI";
    }

    return piece;
}

function UndoMove(board, pos) {

    board.resetMarkField(pos.getX(), pos.getY());
    ChangeTurn();
    return board;
}

function score(board, depth) {
    var score = CheckForWinner(board);

    var maxScore = boardSize * boardSize + 1;

    if (score === 1)
        return 0;
    else if (score === 2)
        return depth - maxScore;
    else if (score === 3)
        return maxScore - depth;
}


// Check for a winner.  Return
//   0 if no winner or tie yet
//   1 if it's a tie
//   2 if HUMAN_PLAYER won
//   3 if COMPUTER_PLAYER won
function CheckForWinner(board) {
    var isAiPlayerWinner = board.isPlayerWinner(aiPlayer);
    var isHumanPlayerWinner = board.isPlayerWinner(humanPlayer);
    var areAllMarked = board.areAllFieldsMarked();

    if(!isAiPlayerWinner && !isHumanPlayerWinner && !areAllMarked)
        return 0;
    else if(!isAiPlayerWinner && !isHumanPlayerWinner && areAllMarked)
        return 1;
    else if(isHumanPlayerWinner)
        return 2;
    else if(isAiPlayerWinner)
        return 3;
}