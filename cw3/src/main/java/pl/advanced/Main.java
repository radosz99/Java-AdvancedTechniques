package pl.advanced;

import pl.advanced.classloader.JavaClassLoader;
import base.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static List<Method> methods = new ArrayList<>();
    public static List<Thread> threads = new ArrayList<>();
    private static int RANGE = 0;
    private static int THREADS_NUMBER = 0;
    public static List<Long> currentSeedes;
    public static SortSet cache;
    public static final Object COUNTER_LOCK = new Object();
    public static final Object USE_LOCK = new Object();
    public static final Object METHOD_LOCK = new Object();
    public static long g1 = 0;
    public static long g2 = 0;
    public static long m1 = 0;
    public static long m2 = 0;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    public static long SORT_COUNTER = 0;

    public static void main(String[] args) throws Exception {
        settings(args);
        startThreads(THREADS_NUMBER);
        loadAlgorithmClasses();
        while (true) {
            Thread.sleep(3000);
            missesRaport();
        }
    }

    public static void settings(String[]args){
        if(args.length!=5){
            safePrintln("Zła liczba argumentów");
            safePrintln("LICZBA ZIAREN | LICZBA ELEMENTOW W ZIARNIE | LICZBA WATKOW | REFERENCJA KEY | REFERENCJA VALUE");
            System. exit(0);
        }
        else{
            RANGE = Integer.parseInt(args[0]);
            THREADS_NUMBER = Integer.parseInt(args[2]);
            cache = new SortSet(args[3], args[4],args[1]);
        }
    }
    static class SortThread implements Runnable {
        private List<IElement> dataToSort;
        String executionTime;
        String algorithmName;
        private long currentSeed;
        private volatile boolean running = true;
        public final int THREAD_ID;

        SortThread(int threadId) {
            this.THREAD_ID = threadId;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted() && running) {
                try {
                    if (lifeThread()) {
                        //safePrintln("Ziarno " + currentSeed + ", " + algorithmName + ", " + executionTime);
                        synchronized (COUNTER_LOCK) {
                            synchronized (cache) {
                                cache.sortMap.put(currentSeed, dataToSort);
                            }
                            SORT_COUNTER++;
                        }
                        Thread.sleep(1000);
                    } else {
                        Thread.sleep(0);
                    }
                } catch (IllegalAccessException | InterruptedException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        public boolean lifeThread() throws IllegalAccessException, InstantiationException, InvocationTargetException {
            if (methodsAlive()) {
                if (!seedIsCurrentlySorted()) {
                    synchronized (cache) {
                        dataToSort = cache.getDataBySeed(currentSeed);
                    }
                    if (dataToSort == null) {
                        //juz posortowane
                        incrementCounters(false);
                        return false;
                    } else {
                        incrementCounters(true);
                        sort();
                        return true;
                    }
                } else {
                    //jest obecnie losowane
                    return false;
                }
            } else {
                //nie ma zadnych metod
                return false;
            }

        }

        public void sort() throws IllegalAccessException, InstantiationException, InvocationTargetException {
            Random rand = new Random();
            Method algorithm;
            synchronized (METHOD_LOCK) {
                algorithm = methods.get(rand.nextInt(methods.size()));
            }
            long start = System.nanoTime();
            algorithm.invoke(algorithm.getDeclaringClass().newInstance(), dataToSort);
            long elapsedTime = System.nanoTime();
            executionTime = getTime(elapsedTime - start);
            algorithmName = getClassName(algorithm.getDeclaringClass().toString());
        }

        public boolean seedIsCurrentlySorted() {
            Random rand = new Random();
            currentSeed = rand.nextInt(RANGE);
            if (!checkIfInUse(currentSeed)) {
                currentSeedes.set(THREAD_ID, currentSeed);
                return false;
            } else {
                return true;
            }
        }
    }

    public static void startThreads(int threadAmount) {
        initializeThreadSeedesList(threadAmount);
        for (int i = 0; i < threadAmount; i++) {
            threads.add(new Thread(new SortThread(i)));
            threads.get(i).start();
        }
    }

    public static boolean methodsAlive() {
        synchronized (METHOD_LOCK) {
            if (methods.size() != 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static void initializeThreadSeedesList(int threadAmount) {
        synchronized (USE_LOCK) {
            currentSeedes = new ArrayList<>(threadAmount);
            for (int i = 0; i < threadAmount; i++) {
                currentSeedes.add((long) 0);
            }
        }
    }


    public static void stopThreads() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }

    public static boolean checkIfInUse(long seed) {
        synchronized (USE_LOCK) {
            for (Long l : currentSeedes) {
                if (l == seed)
                    return true;
            }
            return false;
        }
    }

    public static String getTime(long time) {
        if (time > 1000000000) {
            return time / 1000000000 + " s";
        } else if (time > 1000000) {
            return time / 1000000 + " ms";
        } else if (time > 1000) {
            return time / 1000 + " us";
        } else {
            return time + " ns";
        }
    }

    public static void incrementCounters(Boolean miss) {
        synchronized (COUNTER_LOCK) {
            g1++;
            g2++;
            if (miss) {
                m1++;
                m2++;
            }
        }
    }

    public static String getClassName(String fullClassName) {
        String[] parts;
        parts = fullClassName.split("\\.");
        return parts[parts.length - 1];
    }

    public static void loadAlgorithmClasses() throws IOException, ClassNotFoundException, NoSuchMethodException, InterruptedException {
        List<Class> sortClasses;
        safePrintln("ZA 5 SEKUND KLASY Z ALGORYTMAMI ZOSTANĄ WCZYTANE!");
        Thread.sleep(5000);
        sortClasses = JavaClassLoader.getSortClasses("C:\\Users\\Radek\\Desktop\\6semestr\\Java_Techniki_Zaawansowane\\cw3\\src\\main\\resources\\cw1.jar");
        loadMethods(sortClasses);
        safePrintln("KLASY Z ALGORYTMAMI WCZYTANE!");
    }

    public static void loadMethods(List<Class> sortClasses) throws NoSuchMethodException {
        synchronized (METHOD_LOCK) {
            for (Class cl : sortClasses) {
                methods.add(cl.getMethod("solve", List.class));
            }
        }
    }

    public static void missesRaport() {
        synchronized (COUNTER_LOCK) {
            if (g1 != 0 && g2 != 0) {
                String missesSinceLast = DECIMAL_FORMAT.format((float) m2 / g2 * 100);
                String misses = DECIMAL_FORMAT.format((float) m1 / g1 * 100);
                safePrintln("\nUchybień ogólnie: " + misses + "%." +
                                "\nUchybień od ostatniego raportu: " + missesSinceLast + "%." +
                        "\nElementów w cache'u: " + cache.sortMap.size() + "/" + RANGE +
                        "\nLącznie sortowań: " + SORT_COUNTER + "\n");
                m2 = 0;
                g2 = 0;
            }
        }
    }

    public static void safePrintln(String string) {
        synchronized (System.out) {
            System.out.println(string);
        }
    }
}
