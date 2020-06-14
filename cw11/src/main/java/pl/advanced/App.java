package pl.advanced;

import base.*;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.ServerError;
import java.text.DecimalFormat;
import java.util.*;

@ManagedResource(objectName = "cw1:type=JMX,name=App", description = "Simply sorting application using MBean and Spring")
public class App {
    public static List<Method> methods = new ArrayList<>();
    public static List<Thread> threads = new ArrayList<>();
    public static List<Long> currentSeeds = new ArrayList<>();
    private static int CACHE_SIZE = 500;
    private static int THREADS_NUMBER = 10;
    public static long SORT_COUNTER = 0;
    public static long g1 = 0, g2 = 0, m1 = 0, m2 = 0;

    // mutexes
    public static final Object REPORT_LOCK = new Object();
    public static final Object USE_LOCK = new Object();
    public static final Object METHOD_LOCK = new Object();
    public static final Object THREADS_LOCK = new Object();


    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    // cache
    public static Map<Long, List<IElement>> cache = new HashMap<>();

    @ManagedAttribute(description = "Change amount of sorting threads")
    public void setThreadsNumber(int threadsNumber) throws InterruptedException {
        synchronized (REPORT_LOCK) {
            synchronized (THREADS_LOCK) {
                try {
                    if (threadsNumber > THREADS_NUMBER) {
                        initializeThreadSeedsList(threadsNumber - THREADS_NUMBER);
                        for (int i = THREADS_NUMBER; i < threadsNumber; i++) {
                            threads.add(new Thread(new SortThread(i)));
                            System.err.println("Dodaje nowy wątek " + i);
                            threads.get(i).start();
                        }
                        THREADS_NUMBER = threadsNumber;
                    } else if (threadsNumber < THREADS_NUMBER) {
                        for (int i = THREADS_NUMBER - 1; i >= threadsNumber; i--) {
                            threads.get(i).interrupt();
                            threads.remove(i);
                            currentSeeds.remove(i);
                        }
                    }
                    THREADS_NUMBER = threadsNumber;
                } catch (IllegalThreadStateException i){
                    i.printStackTrace();
                }
            }
        }
    }

    @ManagedAttribute
    public int getThreadsNumber(){
        synchronized (REPORT_LOCK) {
            return THREADS_NUMBER;
        }
    }

    @ManagedOperation
    public void showSet(){
        List<Long> list = new ArrayList<>();
        for (Map.Entry<Long, List<IElement>> entry : cache.entrySet()) {
            list.add(entry.getKey());
        }
        System.err.println(list);
    }

    @ManagedAttribute
    public void setCacheSize(int cacheSize){
        if(cacheSize >= 1) {
            synchronized (REPORT_LOCK) {
                synchronized (cache) {
                    if (cacheSize < CACHE_SIZE) {
                        try {
                            for (Iterator<Map.Entry<Long, List<IElement>>> it = cache.entrySet().iterator(); it.hasNext(); ) {
                                Map.Entry<Long, List<IElement>> entry = it.next();
                                Long key = entry.getKey();
                                if (key >= cacheSize) {
                                    it.remove();
                                    cache.remove(key);
                                }
                            }
                        } catch (ConcurrentModificationException e){
                            e.printStackTrace();
                        }
                    }
                    CACHE_SIZE = cacheSize;
                }
            }
        }
    }

    @ManagedAttribute
    public int getCacheSize(){
        synchronized (REPORT_LOCK) {
            return CACHE_SIZE;
        }
    }

    @ManagedOperation
    public String getReport(){
        return missesReport();
    }

    public void startApplication() {
        startThreads(THREADS_NUMBER);
        loadAlgorithmClasses();
    }

    public static void startThreads(int threadAmount) {
        initializeThreadSeedsList(threadAmount);
        for (int i = 0; i < threadAmount; i++) {
            threads.add(new Thread(new SortThread(i)));
            threads.get(i).start();
        }
    }

    public static void loadAlgorithmClasses(){
        List<Class> sortClasses;
        try {
            sortClasses = JavaClassLoader.getSortClasses("C:\\Users\\Radek\\Desktop\\6semestr\\Java_Techniki_Zaawansowane\\cw3\\src\\main\\resources\\cw1.jar");
            loadMethods(sortClasses);
            safePrintln("Classes had been loaded!");
        } catch (ClassNotFoundException c){
            System.err.println("Classes not found :" + c.getMessage());
        } catch (NoSuchMethodException m){
            System.err.println("Solving methods not found :" + m.getMessage());
        } catch (IOException i){
            System.err.println("Jar not found :" + i.getMessage());
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
                        safePrintln("Thread " + THREAD_ID + ", seed " + currentSeed + ", " + algorithmName + ", " + executionTime);
                        synchronized (REPORT_LOCK) { //TODO : think about another mutex, because one of threads will own REPORT_LOCK and we getn't the report
                            synchronized (cache) {
                                if(currentSeed < CACHE_SIZE) {
                                    cache.put(currentSeed, dataToSort);
                                }
                            }
                            SORT_COUNTER++;
                        }
                        try{
                            Thread.sleep(1000);
                        } catch(InterruptedException i){
                            Thread.currentThread().interrupt();
                            break;
                        }
                    } else {
                        try {
                            Thread.sleep(0);
                        }
                        catch(InterruptedException i){
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        public boolean lifeThread() throws IllegalAccessException, InstantiationException, InvocationTargetException {
            if (methodsAlive()) {
                synchronized (THREADS_LOCK){

                }
                if(!Thread.currentThread().isInterrupted() && running) {
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
                }
            } else {
                    // no methods available
                    return false;
                }
            return false;
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
            synchronized (THREADS_LOCK) {
                Random rand = new Random();
                currentSeed = rand.nextInt(CACHE_SIZE);
                if (!checkIfInUse(currentSeed) && currentSeeds.size() > THREAD_ID) {
                    currentSeeds.set(THREAD_ID, currentSeed);
                    return false;
                } else {
                    return true;
                }
            }
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

    public static List<IElement> getDataBySeed(long seed) {
        synchronized (cache) {
            if (cache.containsKey(seed)) {
                return null;
            } else {
                return IntGenerator.getIntData(10000, 0, 1000, seed);
            }
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
        synchronized (REPORT_LOCK) {
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


    public static void loadMethods(List<Class> sortClasses) throws NoSuchMethodException {
        synchronized (METHOD_LOCK) {
            for (Class cl : sortClasses) {
                methods.add(cl.getMethod("solve", List.class));
            }
        }
    }

    public static String missesReport() {
        synchronized (REPORT_LOCK) {
            String info = "";
            if (g1 != 0 && g2 != 0) {
                String missesSinceLast = DECIMAL_FORMAT.format((float) m2 / g2 * 100);
                String misses = DECIMAL_FORMAT.format((float) m1 / g1 * 100);
                info = "\nMisses overall: " + misses + "%." +
                        "\nMisses since last report: " + missesSinceLast + "%." +
                        "\nWorking threads: " + THREADS_NUMBER +
                        "\nCurrent number of elements in cache: " + cache.size() + "/" + CACHE_SIZE +
                        "\nSorts overall: " + SORT_COUNTER + "\n";
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
