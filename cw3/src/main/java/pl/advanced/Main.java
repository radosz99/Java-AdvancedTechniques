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

// comment
public class Main {
    public static List<Method> methods = new ArrayList<>();
    private static final int RANGE = 1000;
    private static final int THREADS_NUMBER = 16;
    public static List<Long> currentSeedes;
    public static SortSet cache = new SortSet();
    public static final Object counterLock = new Object();
    public static long g1 = 0;
    public static long g2 = 0;
    public static long m1 = 0;
    public static long m2 = 0;
    public static List<Thread> threads = new ArrayList<>();
    private static DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) throws Exception {
        startThreads(THREADS_NUMBER);
        loadAlgorithmClasses();
        while (true) {
            Thread.sleep(15000);
            missesRaport();
        }
    }

    static class SortThread implements Runnable {
        private List<IElement> dataToSort;
        String executionTime;
        String algorithmName;
        private long currentSeed;
        private volatile boolean running = true;
        public final int threadId;

        SortThread(int threadId) {
            this.threadId = threadId;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted() && running) {
                try {
                    if (lifeThread()) {
                        //System.out.println("Ziarno " + currentSeed + ", " + algorithmName + ", " + executionTime);
                        System.out.println(currentSeed);
                        cache.sortMap.put(currentSeed, dataToSort);
                        Thread.sleep(100);
                    } else {
                        Thread.sleep(0);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        public boolean lifeThread() throws IllegalAccessException, InstantiationException, InvocationTargetException {
            if (methods.size() != 0) {
                Random rand = new Random();
                currentSeed = rand.nextInt(RANGE);
                if (!checkIfInUse(currentSeed)) {
                    currentSeedes.set(threadId, currentSeed);
                    dataToSort = cache.getDataBySeed(currentSeed);
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
            //synchronized (this) {
            Random rand = new Random();
            Method algorithm = methods.get(rand.nextInt(methods.size()));
            long start = System.nanoTime();
            algorithm.invoke(algorithm.getDeclaringClass().newInstance(), dataToSort);
            long elapsedTime = System.nanoTime();
            executionTime = getTime(elapsedTime - start);
            algorithmName = getClassName(algorithm.getDeclaringClass().toString());
            // }
        }
    }

    public static void startThreads(int threadAmount) {
        initializeThreadSeedesList(threadAmount);
        for (int i = 0; i < threadAmount; i++) {
            threads.add(new Thread(new SortThread(i)));
            threads.get(i).start();
        }
    }

    public static void initializeThreadSeedesList(int threadAmount) {
        currentSeedes = new ArrayList<>(threadAmount);
        for (int i = 0; i < threadAmount; i++) {
            currentSeedes.add((long) 0);
        }
    }


    public static void stopThreads() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }

    public static boolean checkIfInUse(long seed) {
        for (Long l : currentSeedes) {
            if (l == seed)
                return true;
        }
        return false;
    }

    public static String getTime(long time) {
        if (time > 1000000000) {
            return time / 1000000000 + " s";
        } else if (time > 1000000) {
            return time / 1000000 + " ms";
        } else if (time > 1000) {
            return time / 1000 + " μs";
        } else {
            return time + " ns";
        }
    }

    public static void incrementCounters(Boolean miss) {
        synchronized (counterLock) {
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
        fullClassName = fullClassName.replace('.', ';');
        parts = fullClassName.split(";");
        return parts[parts.length - 1];
    }

    public static void loadAlgorithmClasses() throws IOException, ClassNotFoundException, NoSuchMethodException, InterruptedException {
        List<Class> sortClasses;
        System.out.println("ZA 5 SEKUND KLASY Z ALGORYTMAMI ZOSTANĄ WCZYTANE!");
        Thread.sleep(5000);
        sortClasses = JavaClassLoader.getSortClasses("C:\\Users\\Radek\\Desktop\\6semestr\\Java_Techniki_Zaawansowane\\cw3\\src\\main\\resources\\cw1.jar");
        for (Class cl : sortClasses) {
            methods.add(cl.getMethod("solve", List.class));
        }
        System.out.println("KLASY Z ALGORYTMAMI WCZYTANE!");
    }

    public static void missesRaport() {
        synchronized (counterLock) {
            if (g1 != 0 && g2 != 0) {
                String missesSinceLast = df.format((float) m2 / g2 * 100);
                String misses = df.format((float) m1 / g1 * 100);
                System.out.println("Uchybień ogólnie: " + misses + "%.");
                System.out.println("Uchybień od ostatniego raportu: " + missesSinceLast + "%.");
                System.out.println("Elementów: " + cache.sortMap.size());
                m2 = 0;
                g2 = 0;
            }
        }
    }
}
