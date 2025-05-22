# Symulator Wirtualnego Świata (Java)

Konsolowa aplikacja przeniesiona do środowiska graficznego Swing, w której na dwuwymiarowej planszy żyją rośliny i zwierzęta. Gracz steruje Człowiekiem, wykorzystując interfejs Swing do dynamicznej i responsywnej rozgrywki.

## Cele projektu

* **Praktyczne zastosowanie OOP**: projekt realizowany od podstaw z wykorzystaniem zasad dziedziczenia, polimorfizmu, abstrakcji i enkapsulacji.
* **Interfejs graficzny**: nauka tworzenia GUI w Swing, zarządzania eventami i rysowania elementów na `JPanel`.
* **Wzorce projektowe**: implementacja fabryki organizmów oraz singletona dla zarządcy świata.
* **Zarządzanie stanem**: serializacja i deserializacja stanu gry do pliku, obsługa wyjątków IO.

## Nabyte umiejętności

* **Projektowanie architektury**: podział na warstwę logiki (`model`) i prezentacji (`ui`), separacja odpowiedzialności.
* **Java SE & Swing**: praca z `JFrame`, `JPanel`, `JMenuBar`, `ActionListener` i mechanizmem odświeżania komponentów.
* **Kolekcje i strumienie**: zarządzanie listami organizmów i filtrowanie zdarzeń.
* **Obsługa plików**: zapisywanie i wczytywanie stanu świata za pomocą `ObjectOutputStream` i `ObjectInputStream`.
* **Debugowanie i optymalizacja**: profilowanie czasu reakcji GUI i optymalizacja algorytmu rozprzestrzeniania roślin.

## Funkcjonalności

* **Ruch i walka**: zwierzęta poruszają się losowo, wykorzystując siłę do rozstrzygania kolizji; wyjątki dla żółwia (niskie prawdopodobieństwo ataku) i cyber-owcy.
* **Rozmnażanie roślin**: pięć gatunków roślin rozprzestrzenia się wg ustalonego prawdopodobieństwa.
* **Człowiek**: sterowany strzałkami, posiada umiejętność specjalną (5 tur działania + 5 tur odnowienia).
* **Interaktywne dodawanie**: możliwość tworzenia nowych organizmów przez kliknięcie w planszę.
* **Raport zdarzeń**: panel `JTextArea` prezentujący historię zdarzeń – jedzenie, walka, rozwój.

## Technologie i narzędzia

* **Język**: Java SE 8+
* **Framework GUI**: Swing (AWT)
* **Build**: Maven / Gradle
* **Kontrola wersji**: Git, GitHub
* **IDE**: IntelliJ IDEA / Eclipse

## Struktura katalogów

```
Struktura plików źródłowych

Wszystkie pliki .java znajdują się w katalogu src/main/java/pl/npnpdev/virtualworld/:
Antylopa.java
Barszcz_sosnowskiego.java
Czlowiek.java
Gra.java
Guarana.java
Lis.java
Main.java
Mlecz.java
Organizm.java
Owca.java
Pole.java
Roslina.java
Swiat.java
SwiatHex.java
SwiatKratowy.java
Trawa.java
Wilcze_jagody.java
Wilk.java
Zolw.java
Zwierze.java
```

## Sterowanie

* **Strzałki** – ruch Człowieka
* **Kliknięcie™** – dodawanie organizmu na planszy
* **Menu** – następna tura, zapis/wczytanie stanu świata

## Autor

**Igor Tomkowicz** (npnpdev)

* GitHub: [npnpdev](https://github.com/npnpdev)
* LinkedIn: [Igor Tomkowicz](https://www.linkedin.com/in/igor-tomkowicz-a5760b358/)
* E-mail: [npnpdev@gmail.com](mailto:npnpdev@gmail.com)

## Licencja

Projekt udostępniony na licencji MIT.
