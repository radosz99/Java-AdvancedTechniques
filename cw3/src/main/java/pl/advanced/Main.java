package pl.advanced;

import pl.advanced.classloader.JavaClassLoader;
import base.*;
import pl.advanced.generator.IntGenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// comment
public class Main {
    public static List<Method> methods = new ArrayList<>();
    private static final int RANGE = 30;
    private static final int THREADS_NUMBER = 2;
    public static SortSet cache = new SortSet();

    public static void main(String[] args) throws Exception {
        loadAlgorithmClasses();
        startThreads(THREADS_NUMBER);
    }

    static class SortThread implements Runnable {
        private List<IElement> dataToSort;
        String executionTime;
        String algorithmName;
        private long currentSeed;

        public boolean lifeThread() throws IllegalAccessException, InstantiationException, InvocationTargetException {
            if (methods.size() != 0) {
                Random rand = new Random();
                currentSeed = rand.nextInt(RANGE);
                dataToSort = cache.getDataBySeed(currentSeed);
                //juz posortowane ziarenko
                if (dataToSort == null) {
                    //uchybienie?
                    return false;
                } else {
                    sort();
                    return true;
                }
            }
            //nie ma jeszcze zadnych metod
            else {
                return false;
            }
        }

        public void sort() throws IllegalAccessException, InstantiationException, InvocationTargetException {
            Random rand = new Random();
            Method algorithm = methods.get(rand.nextInt(methods.size()));
            long start = System.nanoTime();
            algorithm.invoke(algorithm.getDeclaringClass().newInstance(), (Object) dataToSort);
            long elapsedTime = System.nanoTime();
            executionTime = getTime(elapsedTime - start);
            algorithmName = getClassName(algorithm.getDeclaringClass().toString());
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (lifeThread()) {
                        System.out.println(algorithmName + ", " + executionTime);
                        //tu na pewno lock
                        //TODO Make locks for synchro
                        cache.sortMap.put(currentSeed, dataToSort);
                        Thread.sleep(1000);
                    } else {

                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void startThreads(int threadAmount) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        new Thread(new SortThread()).start();
        new Thread(new SortThread()).start();
        new Thread(new SortThread()).start();
        new Thread(new SortThread()).start();
    }

    public static String getTime(long time) {
        if (time > 1000000000) {
            return time / 1000000000 + " s";
        } else if (time > 1000000)
            return time / 1000000 + " ms";
        else if (time > 1000)
            return time / 1000 + " μs";
        else
            return time + " ns";
    }

    public static String getClassName(String fullClassName) {
        String[] parts;
        fullClassName = fullClassName.replace('.', ';');
        parts = fullClassName.split(";");
        return parts[parts.length - 1];
    }

    public static void loadAlgorithmClasses() throws IOException, ClassNotFoundException, NoSuchMethodException {
        List<Class> sortClasses;
        sortClasses = JavaClassLoader.getSortClasses("C:\\Users\\Radek\\Desktop\\6semestr\\Java_Techniki_Zaawansowane\\cw3\\src\\main\\resources\\cw1.jar");
        for (Class cl : sortClasses) {
            methods.add(cl.getMethod("solve", List.class));
        }
    }
}
