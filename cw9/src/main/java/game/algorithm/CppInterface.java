package game.algorithm;

public class CppInterface {
        static {
            System.loadLibrary("native");
        }

        public native int[] makeRandom(int[] board);

        public native int[] makeFirst(int[] board);

}
