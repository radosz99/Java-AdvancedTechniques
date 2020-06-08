package game.algorithm;

import jdk.nashorn.api.scripting.JSObject;
import org.json.JSONObject;

import javax.script.*;
import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Skrypt {
    public static void main(String[] args) throws ScriptException, IOException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        List<List<Integer>> board = new ArrayList<>();
        SecureRandom random = new SecureRandom();
        for(int i = 0; i<5; i++) {
            List<Integer> row = new ArrayList<>();
            for(int j=0; j<5; j++){
                //int val = random.nextInt(3);
//                val -= 1;
//                if(j%2==1){
//                    val=0;
//                }
                int val = 0;
                row.add(val);
            }
            board.add(row);
        }
        board.get(2).set(1,1);

        MinimaxAlg minimaxAlg = new MinimaxAlg(6);
        ArrayList<Integer> xd = minimaxAlg.execute(board);

        for(List<Integer> r : board){
            for(int i=0; i<r.size();i++){
                System.out.print(r.get(i) + "\t");
            }
            System.out.println("");
        }

//        Bindings bind = engine.getBindings(ScriptContext.ENGINE_SCOPE);
//        bind.put("board", board);
//        JSObject xd = (JSObject) engine.eval(new FileReader("src/main/java/game/algorithm/random.js"));
//        System.out.println(xd.getMember("x"));


    }
}
