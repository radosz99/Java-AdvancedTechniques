Ćwiczenie 3. Wątki, miękkie referencje i mechanizm refleksji.

Ćwiczenie polega na stworzeniu konsolowej aplikacji wielowątkowej, w której:

    Wątki sortują losowe dane całkowitoliczbowe.
    Algorytmy sortowania (na bazie ćwiczenia nr 1) ładowane są z osobnego katalogu z wykorzystaniem mechanizmu refleksji.
    Rozwiązania umieszczane są w pamięci podręcznej.
    Badany jest mechanizm miękkich referencji (soft references).

Wymagania szczegółowe:

    Należy utworzyć strukturę danych (klasa zawierająca mapę) będącą pamięcią podręczną przechowującą posortowane dane. Szczegóły:
        Kluczem mapy powinna być liczba typu long (jest to ziarno, patrz dalej).
        Wartością mapy powinna być lista elementów do sortowania/posortowanych (tj. List<IElement>).
        Należy odpowiednio wykorzystać mechanizm miękkich referencji, by Garbage Collector usuwał część wpisów w pamięci podręcznej przy niedostatecznej ilości dostępnej pamięci. Jak działanie aplikacji zmieni się po wykorzystaniu zwykłych (silnych) lub słabych referencji zamiast miękkich?
        Pamięć podręczna powinna także przechowywać liczniki g1, g2 ogólnych odwołań do pamięci podręcznej (liczba prób odniesienia się do klucza) oraz liczniki m1, m2 nieudanych odwołań (liczba prób odniesienia się do klucza, którego nie ma obecnie w pamięci podręcznej). Inkrementacja g1 pociąga za sobą inkrementację g2, a inkrementacja m1 pociąga inkrementację m2 (dlaczego liczniki są w parach jest opisane niżej).
    Należy utworzyć wątki, które cyklicznie próbują posortować losowe dane i umieścić posortowane dane w pamięci podręcznej. Szczegóły:
        Do generowania danych do sortowania należy wykorzystać ziarno dla generatora pseudolosowego. Tzn. powinna być metoda generująca dane na podstawie podanego ziarna (dwukrotne podanie tego samego ziarna ma powodować otrzymanie tych samych danych!). Dane powinny być całkowitoliczbowe (by dało się użyć wszystkich algorytmów sortowania).
        Wątki powinny losować ziarno, po czym sprawdzać czy w pamięci podręcznej istnieją już posortowane dane identyfikowane tym ziarnem. Jeśli nie, to dane należy wygenerować, posortować używając losowego algorytmu (patrz dalej), po czy wynik umieścić w pamięci podręcznej.
        Dostęp do zasobów współdzielonych (pamięć podręczna, konsola, kolekcja algorytmów) musi być synchronizowany.
        W konsoli należy umieścić log: wątki powinny raportować co robią m.in. który wątek sortuje jakie dane (jaki ziarno) i jakim algorytmem.
        Osobny wątek (może to być wątek główny), dzięki któremu można: (1) załadować klasy algorytmów sortowania, (2) wyładować te klasy, (3) wypisać opis obecnie załadowanych klas algorytmów.
        Powyższy wątek powinien też raz na jakiś czas raportować odsetek chybień w pamięci (bazując na licznikach które przechowuje pamięć podręczna) tzn. wartości m1/g1 oraz m2/g2 (ewentualnie razy 100%). Liczniki m2 i g2 powinny być zerowane po każdym takim raporcie (w ten sposób m1/g1 pokazuje globalny odsetek uchybień, a m2/g2 bierze pod uwagę tylko czas od ostatniego raportu).
    Algorytmy do używania przez wątki powinny być wczytywane korzystając z mechanizmu refleksji. Szczegóły:
        Wątki używają kolekcji (listy) algorytmów sortowania.
        Początkowo kolekcja powinna być pusta (jeśli wątki natrafią na taką sytuację, to nie wybierają algorytmu i przechodzą do następnego cyklu).
        Klasy z algorymami rozwiązania mają być dostarczone w postaci plików .class w osobnym katalogu oraz wczytywane do powyższej kolekcji za pomocą mechanizmu refleksji.
        Nie należy czynić żadnych założeń odnośnie liczby i rodzaju algorytmów (zaprojektowany mechanizm ładowania ma sam wczytać wszystkie znalezione klasy, a nie wczytywać pliki na bazie hardkodowanych nazw/ścieżek).
        Należy zabezpieczyć program przed wczytaniem nieprawidłowych plików (plików obrazów, plików .class niezawierających klasy algorytmu która dziedziczy bezpośrednio lub pośrednio po AbstractIntSorter) – program sam powinien stwierdzić czy rozpatrywany plik jest poprawny.
        Nie trzeba ładować innych klas niż konkretne algorytmy tzn. klasy typu AbstractIntSolver czy IntElement mogą być normalnie zdefiniowane w programie.
    Wskazówki i uwagi pomocnicze:
        Należy określić sposób generacji danych na bazie ziarna. W szczególności liczba sortowanych elementów powinien mieścić się w sensownym zakresie pomiędzy pewnymi wartościami MIN i MAX. Jest to istotne gdyż:
            Rozstęp R = MAX - MIN + 1, będzie decydować ile jest możliwych wartości ziarna.
            Pojedynczy wpis w pamięci podręcznej zajmie E bajtów, gdzie E jest mniej więcej od S×MIN do S×MAX bajtów. Z kolei S jest rozmiarem obiektu IntElement w bajtach. S jest więc zależny od długości łańcucha znaków zawartego w IntElement. Warto więc dla przewidywalności wyników zadbać, żeby łańcuchy te były zawsze jednakowej długości.
            Całkowicie zapełniona pamięć podręczna zajmie więc mniej więcej R×E bajtów.
            Można sterować pamięcią sterty dostępną dla maszyny Java'y za pomocą odpowiednich opcji (-Xms, -Xmx).
            "Stała" S, parametry MIN, MAX oraz wybrany rozmiar sterty (-Xms, -Xmx) będą wpływać na obserwowany odsetek chybień w pamięci. Należy znaleźć orientacyjne wartości parametrów dla których będziemy obserwować różne zachowania pamięci podręcznej (np. brak lub prawie brak, 25% chybień, 75% chybień itp.).
        Wątki można usypiać pomiędzy każdym cyklem, tak by dało się obserwować działanie wątków (by informacje na konsoli nie pojawiały się i znikały za szybko).
        Można też usypiać wątki po wykonaniu algorytmu, jeśli algorytm wykonuje się za szybko (symulacja dłuższych obliczeń).
        Załadowanie i wyładowanie klas powinno dać się zauważyć z użyciem odpowiednich narzędzi (jconsole).

