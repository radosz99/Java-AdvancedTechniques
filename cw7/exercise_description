Ćwiczenie 7. Komunikacja sieciowa z użyciem SOAP.

Należy zaprojektować sieciowy system wymiany wiadomości, składający się z pewnej liczby osobnych aplikacji (konsolowych lub okienkowych) działający w topologii jednokierunkowego pierścienia (wszystko fizycznie na jednym komputerze). Tzn. jeśli mamy 4 aplikacje A, B, C, D, to tworzą one topologię A → B → C → D → A, gdzie wiadomość może być przekazana jedynie w kierunku wskazanym przez strzałki.

Zadanie należy zrealizować z wykorzystaniem protokołu SOAP oraz gniazdek sieciowych TCP/IP (można wykorzystać np. pakiet javax.xml.soap.*). Treść samej wiadomości powinna być przenoszona w ciele (body) koperty SOAP-owej, a pozostałe informacje (potrzebne do obsługi transmisji) w jej nagłówku (header). Można też wykorzystać framework Apache Axis/Axis2.
System powinien wspierać 2 tryby adresowania wiadomości:

    Unicast: dane wysyłane do konkretnego węzła.
    Broadcast: dane wysyłane do wszystkich węzłów poza nadawcą.

Interfejs (konsolowy lub okienkowy) każdej aplikacji powinien być podzielony na kilka części:

    Część do wysyłania wiadomości. Powinna umożliwić wybór adresata/trybu adresacji, zredagowanie wiadomości oraz jej wysłanie.
    Część do odbierania wiadomości. Powinna wyświetlać odebrane wiadomości przeznaczone dla danego węzła wraz z nadawcą.
    Część diagnostyczną. Powinna ona wyświetlać informacje diagnostyczne do wizualizacji działania sieci (np. fakt przesłania wiadomości dalej do innego węzła, szczegóły nagłówka itp.).

