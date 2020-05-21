Ćwiczenie 1. Własna biblioteka i javadoc.

Ćwiczenie polega na utworzeniu zestawu klas stanowiących interfejs umożliwiający rozwiązywanie problemu sortowania oraz utworzenie z tych klas biblioteki JAR wraz z dokumentacją.

Etap 1 (sortowanie).

Biblioteka powinna posiadać kilka klas:

    Interfejs IElement. Powinien mieć metody getName() oraz getValue().
    Klasa IntElement. Powinna realizować interfejs IElement i przechowywać parę String i int. Można dodać dodatkowe metody/konstruktory.
    Klasa FloatElement. Powinna realizować interfejs IElement i przechowywać parę String i float. Można dodać dodatkowe metody/konstruktory.
    Klasa abstrakcyjna AbstractIntSorter. Powinna mieć metody:
        List<IntElement> solve(List<IntElement>) – sortuje dane (typu całkowitoliczbowego) danym algorytmem.
        String description() – zwraca opis (np. nazwę) algorytmu sortowania.
        bool isStable() – zwraca czy algorytm sortowania jest stabilny.
        bool isInSitu() – zwraca czy algorytm sortowania działa w miejscu.
    Klasa abstrakcyjna AbstractFloatSorter. Powinna dziedziczyć po AbstractIntSorter oraz dodawać metodę:
        List<IElement> solve(List<IElement>) – sortuje dane (zarówno całkowitoliczbowe jak i zmiennoprzecinkowe) wybraną metodą.
    Co najmniej jedna klasa dziedzicząca po AbstractIntSorter (sugerowane algorytmy: counting sort lub pigeonhole sort).
    Co najmniej jedna klasa dziedzicząca po AbstractFloatSorter z szybkim algorytmem (sugerowane algorytmy: quick sort, heap sort lub merge sort)).
    Co najmniej jedna klasa dziedzicząca po AbstractFloatSorter z wolnym algorytmem (sugerowane algorytmy: insert sort lub selection sort).

Etap 2 (Javadoc).

Dla klas z etapu 1 należy dodać dokumentację Javadoc. Należy umieścić własne opisy: (1) klas, (2) metod, (3) pól, (4) argumentów metod i (5) wartości zwracanych metod. Doumentację Javadoc należy wyświetlić w przeglądarce (jako HTML). Dobrze jeśli Javadoc zapewni podpowiedzi przy korzystaniu z biblioteki (podczas ćwiczenia nr 2).

Etap 3 (biblioteka).

Należy utworzyć bibliotekę (plik .jar zawierający pliki .class, nie .java!) z klas z etapu 1, tak by można go było użyć w innym projekcie (ćwiczenie nr 2). Oznacza to, że projekt docelowy nie powinien znać kodu źródłowego biblioteki (jedynie interfejs, kod bajtowy i ewentualnie javadoc).
