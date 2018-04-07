
Screenshots
![Alt text](/screenshots/1.png?raw=true "Screen 1")



Idea

Temat: 
Projekt aplikacji rozproszonej o zadanej funkcjonalności z aplikacjami wykorzystującymi ziarna Javy. 

Wymagania:
Wiedza o wzorcach projektowych wykorzystywanych przy tworzeniu ziaren Javy, mechanizmie introspekcji i tworzeniu włączanych do projektów paczek jar z odpowiednim manifestem
Umiejętność tworzenia aplikacji rozproszonych z wykorzystaniem RMI

Zadanie: 
Ogólny opis aplikacji: 
Swtórz grę polegającą na zarządzaniu załogą statku kosmicznego (pomysł oparty na grze Spaceteam). 
W grze występują dwie role graczy:
kapitan - jego rolę może pełnić tylko jedna osoba
załoga - może (a nawet powinna ;)) pełnić ją więcej niż jedna osoba

Kapitan posiada odrębny, w stosunku do załogi, ekran gry. Na jego panelu wyświetlają się polecenia, które musi wydać załodze (np. ustaw moc silników na 3). Posiada także pole informujące o aktualnej liczbie punktów, a także przyciski pozwalające na rozpoczęcie lub zakończenie gry. Załoga posiada ekran gry reprezentujący panel sterowniczy złożony z przyrządów różnego typu reprezentowanych za pomocą suwaka, listy, radio buttonów, pola tekstowego. Panel posiada swoją nazwę (np. Sterownia silnika), podobnie jak i każdy przyrząd (np. moc silników). Część z przyrządów może być aktywna, a część nie. 
Każdy gracz posiada swój indywidualny panel. 

Gra polega na głosowym wydawaniu poleceń wyświetlonych na panelu przez kapitana załodze po rozpoczęciu rozgrywki. Polecenia te dotyczą ustawienia wartości konkretnego przyrządu na zadaną wartość. Z racji, że każdy członek załogi powinien posiadać indywidualny zbiór przyrządów, są one skierowane do konkretnego członka załogi. 
Za poprawne wykonanie polecenia (ustawienie danego przyrządu na określoną wartość) przyznawany jest jeden punkt, za niepoprawne (zły przyrząd / zła wartość) odejmowany jest jeden. 
Po poprawnym lub niepoprawnym wykonaniu polecenia pojawia się nowe do czasu zakończenia gry przez kapitana. Można wprowadzić ograniczenie czasowe na wykonanie polecenia. 

Implementacja: 
Gra powinna składać się z 3 części:
Pojedynczej aplikacji dla kapitana
Pojedynczej aplikacji serwera
Kilu wariacji aplikacji załogi

Komunikację pomiędzy aplikacjami należy zrealizować za pomocą mechanizmu RMI. 

Aplikacja kapitana powinna pobierać listę graczy z serwera, umożliwiać rozpoczęcie i kończenie gry, zbierać informacje o działaniu aplikacji załogi podlicząc punkty, a także generować kolejne (wykonywalne) komendy. 

Serwer powinien przyjmować połączenia kolejnych graczy, wyświetlać ich listę i umożliwić wyrzucenie ich z gry ("wykickowanie"). 

Aplikacje załogi, każda reprezentująca osobny panel, powinny być do siebie podobne - zawierać te same typy przyrządów (niektóre niedostępne - zablokowane), ale odpowiadających za równe funkcje, o różnych możliwych wartościach. Dobrym pomysłem byłaby realizacja ogólnej reprezentacji panelu, którą możnaby w zależności od aplikacji, dowolnie dostosowywać. Można to zrealizować przy pomocy zaimplementowanego jednorazowo komponentu Java Beans. Komponent powinien być zbudowany z nazwy panelu, kilku typów przyrządów (wspomniany suwak, lista, radio buttony, pola tekstowe) wraz z ich nazwami. 
Ziarno powinno posiadać: właściwości wszystkich możliwych typów (proste, ograniczone, wiązane), graficzną reprezentację oraz klasę opisową BeanInfo z klasami pomocniczymi edytorów (należy zwrócić uwagę na metodę getJavaInitializationString) i customizera (należy zwrócić uwagę na metodę setObject), służącymi do zmian właściwości ziarenka. 

Właściwościami panelu powinny być co najmniej:
nazwa panelu (właściwość prosta)
nazwy poszczególnych przyrządów
informacja o włączeniu / wyłączeniu poszczególnych przyrządów
rozmiar pola tekstowego (właściwość wiązana)
graniczne wartości przyrządów (właściwość ograniczona)

Ziarno powinno być zastosowane dla kolejnych aplikacji poprzez uprzednie utworzenie paczki jar ze wszystkimi klasami niezbędnymi do jego funkcjonowania, włączeniu do każdej z aplikacji, a następnie wstawienie na wybrany panel z pomocą wizarda i odpowiednie dostosowanie go