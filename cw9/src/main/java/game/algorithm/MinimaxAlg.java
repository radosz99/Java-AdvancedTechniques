package game.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class MinimaxAlg implements Algorithm {
    final private short boardSize = 5;
    final private short tilesToWin = 4;
    private int depth;
    int minPlayer = 1;
    int maxPlayer = -1;
    public MinimaxAlg(int depth) {
        this.depth = depth;
    }

    public ArrayList<Integer> execute(List<List<Integer>> tilesValue) {
        int player = maxPlayer; // O
        int bestValue = Integer.MIN_VALUE;
        ArrayList<Integer> result = new ArrayList<>(2);
        for(int j = 0; j < boardSize; j++){
            for (int i = 0; i < boardSize; i++){
                if(tilesValue.get(j).get(i)==0){
                    tilesValue.get(j).set(i, player);
                    // dla 1 minimalizacja, dla -1 maksymalizacja
                    int moveValue = minimax(minPlayer, tilesValue, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    tilesValue.get(j).set(i, 0);
                    if(moveValue > bestValue){
                        result.clear();
                        result.add(j);
                        result.add(i);
                        bestValue = moveValue;
                    }
                }
            }
        }

        return result;
    }

    private boolean checkIfMovesLeft(List<List<Integer>> board){
        for(List<Integer> row : board) {
            if (row.contains(0)) {
                return true;
            }
        }
        return false;
    }

    public int minimax(int playerId, List<List<Integer>> board, int currentDepth, int alpha, int beta) {
        int score = evaluateMove(playerId, board);

        if(score >= 1000 && score != Integer.MAX_VALUE){
            return 1000 - currentDepth; // prefer min depth
        }
        if (score <= -1000 && score != Integer.MIN_VALUE) {
            return -1000 + currentDepth; // prefer min depth
        }

        if(!checkIfMovesLeft(board)) {
            return 0;
        }

        if(playerId == maxPlayer){ //maksymalizacja
            int best = Integer.MIN_VALUE;
            if(currentDepth >= depth){
                return best;
            }
            for(int j = 0; j < boardSize; j++){
                for (int i = 0; i < boardSize; i++){
                    if(board.get(j).get(i)==0){
                        board.get(j).set(i, playerId);
                        // dla 1 minimalizacja, dla -1 maksymalizacja
                        best = Math.max(best, minimax(minPlayer, board, currentDepth + 1, alpha, beta));
                        board.get(j).set(i, 0);
                        if(best > alpha){
                            alpha = best;
                        }
                        if(alpha >= beta){
                            return score;
                        }
                    }
                }
            }
        } else if (playerId == minPlayer){ //minimalizacja
            int best = Integer.MAX_VALUE;
            if(currentDepth >= depth){
                return best;
            }
            for(int j = 0; j < boardSize; j++){
                for (int i = 0; i < boardSize; i++){
                    if(board.get(j).get(i)==0){
                        board.get(j).set(i, playerId);
                        // dla 1 minimalizacja, dla -1 maksymalizacja
                        best = Math.min(best, minimax(maxPlayer, board, currentDepth + 1, alpha, beta));
                        board.get(j).set(i, 0);
                        if(best < beta){
                            beta = best;
                        }
                        if(alpha >= beta){
                            return score;
                        }
                    }
                }
            }
        }

        return score;

    }

    public int evaluateMove(int playerId, List<List<Integer>> board){
        int score = 0;
        int repetitions = 2;
        // diagonals 8
        // ((1|0 2|1 3|2 4|3 (I NA ODWROT))) + ((0|0 1|1 2|2 3|3 + 1|1 2|2 3|3 4|4)) (GÓRA -> DÓł)
        // ((3|0 2|1 1|2 0|3 + 4|1 3|2 2|3 1|4)) + ((4|0 3|1 2|2 1|3 + 3|1 2|2 1|3 0|4)) (DÓł -> GÓRA)

        // GÓRA -> DÓł
        for(int i = 0; i < repetitions; i++){
           List<Integer> line = new ArrayList<>(tilesToWin);
           for (int j = 0; j < tilesToWin; j++){
               if(i==0) {
                   line.add(board.get(j + 1).get(j));
               }
               else {
                   line.add(board.get(j).get(j+1));
               }
           }
            score += evaluateLine(line);
        }

        for(int i = 0; i < repetitions; i++){
            List<Integer> line = new ArrayList<>(tilesToWin);
            for(int j = 0; j < tilesToWin; j++){
                line.add(board.get(j + i).get(j + i));
            }
            score += evaluateLine(line);
        }

        // DÓł -> GÓRA
        for(int i = 0; i < repetitions; i++){
            List<Integer> line = new ArrayList<>(tilesToWin);
            for(int j = 0; j < tilesToWin; j++){
                line.add(board.get(boardSize - 2 - j + i).get(j + i));
            }
            score += evaluateLine(line);
        }

        for (int i = 0; i < repetitions; i++){
            List<Integer> line = new ArrayList<>(tilesToWin);
            for(int j = 0; j < tilesToWin; j++){
                line.add(board.get(boardSize - 1 - j - i).get(j + i));
            }
            score += evaluateLine(line);
        }
        // horizontal 10
        // 0|0 0|1 0|2 0|3 + 0|1 0|2 0|3 0|4

        for(int h = 0; h < boardSize; h++) {
            for (int i = 0; i < repetitions; i++) {
                List<Integer> line = new ArrayList<>(tilesToWin);
                for (int j = 0; j < tilesToWin; j++) {
                    line.add(board.get(h).get(j + i));
                }
                score += evaluateLine(line);
            }
        }

        // vertical 10
        for(int h = 0; h < boardSize; h++) {
            for (int i = 0; i < repetitions; i++) {
                List<Integer> line = new ArrayList<>(tilesToWin);
                for (int j = 0; j < tilesToWin; j++) {
                    line.add(board.get(j + i).get(h));
                }
                score += evaluateLine(line);
            }
        }
        return score;
    }

    public int evaluateLine(List<Integer> cells){
        int occurrences = 0;
        int score;
        int lineOwner = 0;

        // jesli to i to to 0
        if(cells.contains(1) && cells.contains(-1)){
            return 0;
        }
        // jesli nie zawiera ani 1 ani -1 to 0
        else if(!cells.contains(1) && !cells.contains(-1)){
            return 0;
        }
        else {
            // jesli zawiera 1 i nie zawiera -1 to zwroc
            if(cells.contains(1)){
                lineOwner = 1;
                occurrences = Collections.frequency(cells, 1);
            }
            // jesli zawiera -1 i nie zawiera 1 to zwroc
            else if(cells.contains(-1)){
                lineOwner = -1;
                occurrences = Collections.frequency(cells, -1);
            }
        }

        /*
        +1000 for EACH 4-in-a-line
        +100 for EACH 3-in-a-line
        +10 for EACH 2-in-a-line
        +1 for EACH 1-in-a-line
         */
        score = (int) Math.pow(10, occurrences - 1);

        if(lineOwner != maxPlayer){
            score *= -2;
        }
        return score;
    }
}
