package cipher;

import base.IElement;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.xml.crypto.Data;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.NoSuchFileException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipException;

public class Main {
    private static String MENU = "1. Zaszyfruj tekst\n2. Zapisz zaszyfrowany tekst do pliku\n3. Odszyfruj tekst z pliku\n4. Wczytaj plik JAR z klasami\n5. Wygeneruj dane zmiennoprzecinkowe" +
            "\n6. Wygeneruj dane stałoprzecinkowe\n7. Posortuj\n8. Pokaż obecne dane w pamięci\n9. Exit";
    private static byte[] message;
    private static List<IElement> data;
    public static List<Method> methods = new ArrayList<>();


    public static void main(String[] args) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchMethodException, InterruptedException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchProviderException, SignatureException {
        Encryptor test = new Encryptor();
        JavaClassLoader loader = new JavaClassLoader();

        while(true){
            System.out.print(MENU);
            Scanner scan;
            int ans = 9;
            while(true) {
                System.out.print("\n\nCo chcesz zrobić?: ");
                try {
                    scan = new Scanner(System.in);
                    ans = scan.nextInt();
                    break;
                } catch (InputMismatchException i) {
                    System.err.println("Wprowadź poprawną wartość!");
                }
            }
            switch(ans)
            {
                case 1:
                    System.out.print("Podaj tekst: ");
                    scan = new Scanner(System.in);
                    message = test.encrypt(scan.nextLine().getBytes());
                    System.out .println("\nPoprawnie zaszyfrowano!");
                    break;
                case 2:
                    System.out.print("Podaj nazwę pliku: ");
                    scan = new Scanner(System.in);
                    test.saveToFile(message, scan.nextLine());
                    System.out.println("\nPoprawnie zapisano do pliku!");
                    break;
                case 3:
                    System.out.print("Podaj nazwę pliku: ");
                    scan = new Scanner(System.in);
                    try {
                        System.out.println("\nOdszyfrowany tekst: " + new String(test.decrypt(scan.nextLine())));
                    } catch (NoSuchFileException n){
                        System.err.println("\nTaki plik nie istnieje!");
                    }
                    break;
                case 4:
                    System.out.print("Podaj nazwę pliku JAR: ");
                    scan = new Scanner(System.in);
                    try {
                        loader.loadAlgorithmClasses("C:\\Users\\Radek\\Desktop\\6semestr\\Java_Techniki_Zaawansowane\\cw8\\target\\" + scan.nextLine());
                        methods = loader.getMethods();
                        if(methods.size()!=0) {
                            System.err.println("\nWczytano plik JAR!");
                        }
                    } catch (FileNotFoundException f){
                        System.err.println(f.getMessage());
                    } catch (ZipException z){
                        System.err.println(z.getMessage());
                    }
                    break;
                case 5:
                    int min, max, quantity;
                    System.err.print("\nIlość liczb: ");
                    scan = new Scanner(System.in);
                    quantity = scan.nextInt();
                    System.err.print("\nMinimum: ");
                    scan = new Scanner(System.in);
                    min= scan.nextInt();
                    System.err.print("\nMaksimum: ");
                    scan = new Scanner(System.in);
                    max = scan.nextInt();
                    data =  DataGenerator.getFloatData(quantity, min, max);
                    break;
                case 6:
                    System.err.print("\nIlość liczb: ");
                    scan = new Scanner(System.in);
                    quantity = scan.nextInt();
                    System.err.print("\nMinimum: ");
                    scan = new Scanner(System.in);
                    min= scan.nextInt();
                    System.err.print("\nMaksimum: ");
                    scan = new Scanner(System.in);
                    max = scan.nextInt();
                    data =  DataGenerator.getIntData(quantity, min, max);
                    break;
                case 7:
                    System.out.println("\nWybierz metodę:");
                    int counter = 0;
                    for(Method meth : methods) {
                        System.out.println(counter + ": " + meth.getDeclaringClass());
                        counter++;
                    }
                    System.out.print("\nId algorytmu: ");
                    scan = new Scanner(System.in);
                    counter = scan.nextInt();
                    try{
                        sort(counter);
                        System.err.println("\nPosortowano algorytmem " + methods.get(counter).getDeclaringClass() + "!");
                    } catch(InvocationTargetException n){
                        System.err.println("\nBrak danych do posortowania!");
                    } catch(IndexOutOfBoundsException e){
                        System.err.println("\nNiewłaściwy id");
                    }
                    break;
                case 8:
                    try{
                    for(IElement f : data){
                        System.out.println(f);
                    }}catch(NullPointerException n){
                        System.err.println("Brak danych!");
                    }
                    break;
                case 9:
                    System.exit(1);
            }
            System.out.print("\n");
        }
    }

    public static void sort(int id) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Method algorithm;
        try {
            algorithm = methods.get(id);
        } catch (IllegalArgumentException i) {
            System.err.println("Brak algorytmów sortujących, wczytaj plik JAR!");
            return;
        }
        algorithm.invoke(algorithm.getDeclaringClass().newInstance(), data);
    }

}
