Ćwiczenie 5. Zdalne wywoływanie metod (RMI).

Należy napisać system klient-serwer do sortowania liczb z wykorzystaniem RMI. Szczegóły:

    Powinny powstać trzy typt aplikacji: klient, serwer oraz spis (uwaga: jest to spis serwerów i jest to zupełnie inna rzecz niż rejestr RMI!).
    Aplikacja spis powinna implementować dwie metody możliwe do wywołania poprzez RMI:
        Metodę bool register( OS ), gdzie OS może być prostą klasą przechowującą dwa Stringi: nazwę rejestrowanego serwera i opis implementowanego przez niego algorytmu sortowania. Metoda powinna rejestrować podany serwer w spisie i zwracać wynik true/false, w zależności od tego czy operacja się udała.
        Metodę List<OS> getServers(), która zwraca listę serwerów (tj. listę par nazwa-opis algorytmu sortowania).
    Aplikacja serwera powinna implementować metodę S solve( S ), możliwą do wywołania poprzez RMI, gdzie S jest strukturą danych (np. tablica liczb, lista licz lub odpowiednia klasa). Metoda przyjmuje dane do posortowania i zwraca je w formie posortowanej.
    Powstały system ma umożliwiać przeprowadzenie scenariusza w stylu:
        Uruchomienia aplikacji spisu.
        Uruchomienia aplikacji serwera, które automatycznie powinna rejestrować się w spisie.
        Uruchomienia i przetestowania co najmniej dwóch aplikacji klienckich. Aplikacje klienckie powinny posiadać interfejs graficzny umożliwiający: (1) wyświetlenie obecnie zarejestrowanych w spisie serwerów, (2) wygenerowania losowych danych do posortowania (o zadanej długości), (3) wyświetlenie wygenerowanych/posortowanych danych w konsoli oraz (4) posortowanie danych z użyciem wybranego serwera.
        Uruchomienie drugiej (lub więcej) aplikacji serwerowej, która implementuje inny algorytm sortowania, po czym ponowne przetestowanie obu klientów.
    Aplikacje serwera i spisu również powinny posiadać interfejs konsolowy i drukować log prowadzonych operacji.

