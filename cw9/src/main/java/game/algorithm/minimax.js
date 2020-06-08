var solve = function(board){
    var Solver = Java.type('game.algorithm.MinimaxAlg');
    var solver = new Solver();
    var coords = solver.execute(board);
    var d = {
        "x" : coords[0],
        "y" : coords[1]
    }
    return d;
};

solve(board);
