Zadanie: 

Zadaniem jest stworzenie eksploratora plików graficznych - aplikacja ma umożliwiać nawigację pomiędzy katalogami i wyświetlanie miniaturek zawartych w nich plików graficznych (mogą być jednego typu). Aplikacja powinna też umożliwiać manipulację tymi obrazami - obracanie, robienie negatywu, rozmycie itd. 
Słabe referencje powinny być wykorzystane do przechowywania wygenerowanych miniaturek obrazów - w razie potrzeby pamięć ta zostanie zwolniona. 
Miniaturki powinny być generowane w osobnym wątku / wątkach - można do tego wykorzystać zarówno podstawowe mechanizmy, jak i klasę SwingWorker, z pomocą klas z pakietu java.util.concurrent (ConcurrentMap itd.) 
Wykorzystanie własnego ładowacza klas obejmuje ładowanie "pluginów" do transformacji obrazów - pluginy te powinny być umieszczane w określonym katalogu i wylistowane w stosownym menu w aplikacji. Przynajmniej jedna z ładowanych klas powinna być zależna od innej ładowanej klasy (Rozwijanie klas (resolve)). Typami atrybutów metod mogą być typy podstawowe i typ String oraz tablice jednowymiarowe typów podstawowych i typu String. Wywoływane mogą być metody klasy oraz metody instancyjne (w tym drugim przypadku trzeba wywoływać konstruktor tworzący odpowiedni obiekt, aby było łatwiej można przyjąć założenie, że istnieje konstruktor bezargumentowy). Załadowane klasy powinno dać się także wyładować 