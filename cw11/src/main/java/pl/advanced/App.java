package pl.advanced;

import base.*;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;

@ManagedResource(objectName = "cw1:type=JMX,name=App", description = "Simply sorting application using MBean and Spring")
public class App {
    public static List<Method> methods = new ArrayList<>();
    public static List<Thread> threads = new ArrayList<>();
    public static List<Long> currentSeeds;
    private static int RANGE = 500;
    private static int THREADS_NUMBER = 10;
    public static long SORT_COUNTER = 0;
    public static long g1 = 0, g2 = 0, m1 = 0, m2 = 0;

    // mutexes
    public static final Object COUNTER_LOCK = new Object();
    public static final Object USE_LOCK = new Object();
    public static final Object METHOD_LOCK = new Object();

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    // cache
    public static Map<Long, List<IElement>> cache = new HashMap<>();

    @ManagedAttribute
    public void setThreadsNumber(int threadsNumber) {
        THREADS_NUMBER = threadsNumber;
    }

    @ManagedAttribute
    public int getThreadsNumber(){
        return THREADS_NUMBER;
    }

    @ManagedAttribute
    public void setCacheSize(int cacheSize){
        RANGE = cacheSize;
    }

    @ManagedAttribute
    public int getCacheSize(){
        return RANGE;
    }

    @ManagedOperation
    public String getInfo(){
        return missesReport();
    }

    @ManagedOperation(description = "Start application")
    public void startApplication() throws Exception {
        startThreads(THREADS_NUMBER);
        loadAlgorithmClasses();
        while (true) {
            Thread.sleep(3000);
            missesReport();
        }
    }

    public static List<IElement> getDataBySeed(long seed) {
        synchronized (cache) {
            if (cache.containsKey(seed)) {
                return null;
            } else {
                return IntGenerator.getIntData(10000, 0, 1000, seed);
            }
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
                        //safePrintln("Seed " + currentSeed + ", " + algorithmName + ", " + executionTime);
                        synchronized (COUNTER_LOCK) {
                            synchronized (cache) {
                                cache.put(currentSeed, dataToSort);
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
                        dataToSort = getDataBySeed(currentSeed);
                    }
                    if (dataToSort == null) {
                        // already sorted
                        incrementCounters(false);
                        return false;
                    } else {
                        incrementCounters(true);
                        sort();
                        return true;
                    }
                } else {
                    // currently sorting
                    return false;
                }
            } else {
                // no methods available
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
                currentSeeds.set(THREAD_ID, currentSeed);
                return false;
            } else {
                return true;
            }
        }
    }

    public static void startThreads(int threadAmount) {
        initializeThreadSeedsList(threadAmount);
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

    public static void initializeThreadSeedsList(int threadAmount) {
        synchronized (USE_LOCK) {
            currentSeeds = new ArrayList<>(threadAmount);
            for (int i = 0; i < threadAmount; i++) {
                currentSeeds.add((long) 0);
            }
        }
    }

    public static boolean checkIfInUse(long seed) {
        synchronized (USE_LOCK) {
            for (Long l : currentSeeds) {
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
        safePrintln("In 5 seconds classes with sorting methods will be load");
        Thread.sleep(5000);
        sortClasses = JavaClassLoader.getSortClasses("C:\\Users\\Radek\\Desktop\\6semestr\\Java_Techniki_Zaawansowane\\cw3\\src\\main\\resources\\cw1.jar");
        loadMethods(sortClasses);
        safePrintln("Classes had been loaded!");
    }

    public static void loadMethods(List<Class> sortClasses) throws NoSuchMethodException {
        synchronized (METHOD_LOCK) {
            for (Class cl : sortClasses) {
                methods.add(cl.getMethod("solve", List.class));
            }
        }
    }

    public static String missesReport() {
        synchronized (COUNTER_LOCK) {
            String info = "";
            if (g1 != 0 && g2 != 0) {
                String missesSinceLast = DECIMAL_FORMAT.format((float) m2 / g2 * 100);
                String misses = DECIMAL_FORMAT.format((float) m1 / g1 * 100);
                info = "\nMisses overall: " + misses + "%." +
                        "\nMisses since last report: " + missesSinceLast + "%." +
                        "\nWorking threads: " + THREADS_NUMBER +
                        "\nCurrent number of elements in cache: " + cache.size() + "/" + RANGE +
                        "\nSorts overall: " + SORT_COUNTER + "\n";
                safePrintln(info);
                m2 = 0;
                g2 = 0;
            }
            return info;
        }
    }

    public static void safePrintln(String string) {
        synchronized (System.err) {
            System.err.println(string);
        }
    }
}
