package cipher;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static String MENU = "1. Zaszyfruj tekst\n2. Zapisz zaszyfrowany tekst do pliku\n3. Odszyfruj tekst z pliku\n9. Exit";
    private static byte[] message;

    public static void main(String[] args) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        Encryptor test = new Encryptor();

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
                case 9:
                    System.exit(1);
            }
            System.out.print("\n");
        }
    }
}
