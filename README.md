# Virtual World Simulator (Java)

[English](#english-version) | [Polski](#wersja-polska)

---

## English Version

### Project Goals

**Virtual World Simulator** is a graphical Swing application that brings the console-based world simulator to life. Plants and animals inhabit a 2D grid, and the player controls the Human via a responsive Swing UI.

* **OOP in Practice**: applies inheritance, polymorphism, abstraction, and encapsulation from scratch.
* **GUI Development**: builds interactive Swing interfaces using `JFrame`, `JPanel`, `JMenuBar`, and `ActionListener`.
* **Design Patterns**: implements Factory for organism creation and Singleton for world management.
* **State Management**: serializes and deserializes game state, with exception-safe IO handling.

### Skills Demonstrated

* **Architecture Design**: separation of `model` (logic) and `ui` (presentation) layers.
* **Java SE & Swing**: working with components, event handling, and custom painting on `JPanel`.
* **Collections & Streams**: managing organism lists and filtering events.
* **File IO**: saving/loading via `ObjectOutputStream` and `ObjectInputStream`.
* **Debugging & Optimization**: profiling UI responsiveness and optimizing plant spread algorithms.

### Features

* **Movement & Combat**: animals move randomly and resolve collisions by strength, with special rules for Turtle (low attack chance) and Cyber-Sheep.
* **Plant Spreading**: five plant species propagate based on defined probabilities.
* **Human Player**: controlled with arrow keys, with a special ability (`U`) active for 5 turns plus 5-turn cooldown.
* **Interactive Addition**: add new organisms by clicking on the grid.
* **Event Log**: `JTextArea` panel displays a scrollable history of events (eating, fighting, growth).

### Directory Structure

```text
src/main/java/pl/npnpdev/virtualworld/
├── Antylopa.java
├── BarszczSosnowskiego.java
├── Czlowiek.java
├── Gra.java
├── Guarana.java
├── Lis.java
├── Main.java
├── Mlecz.java
├── Organizm.java
├── Owca.java
├── Pole.java
├── PlantFactory.java
├── Roslina.java
├── Swiat.java
├── SwiatHex.java
├── SwiatKratowy.java
├── Trawa.java
├── WilczeJagody.java
├── Wilk.java
├── Zwierze.java
└── UI/
    ├── MainFrame.java
    ├── WorldPanel.java
    └── EventLogPanel.java
```

### Controls

* **Arrow Keys**: move Human
* **Mouse Click**: add an organism at clicked cell
* **Menu**: advance turn, save/load world state

---

## Wersja polska

### Cele projektu

**Symulator Wirtualnego Świata** to aplikacja Swing, która przenosi symulację konsolową do interaktywnego GUI. Rośliny i zwierzęta żyją na dwuwymiarowej siatce, a gracz steruje Człowiekiem za pomocą responsywnego interfejsu Swing.

* **Praktyczne OOP**: wykorzystanie dziedziczenia, polimorfizmu, abstrakcji i enkapsulacji.
* **Tworzenie GUI**: budowanie interfejsów Swing z `JFrame`, `JPanel`, `JMenuBar`, `ActionListener` i malowaniem na `JPanel`.
* **Wzorce**: fabryka organizmów (Factory) i zarządca świata jako Singleton.
* **Zarządzanie stanem**: serializacja/deserializacja stanu gry z bezpiecznym obsługą wyjątków IO.

### Nabyte umiejętności

* **Projekt architektury**: separacja warstw `model` i `ui`.
* **Java SE & Swing**: praca z komponentami, eventami, i niestandardowym rysowaniem.
* **Kolekcje i strumienie**: zarządzanie listami organizmów i filtrowanie zdarzeń.
* **Obsługa plików**: zapisywanie i odczytywanie stanu przez `ObjectOutputStream` i `ObjectInputStream`.
* **Debugowanie i optymalizacja**: profilowanie responsywności UI i optymalizacja algorytmów rozprzestrzeniania roślin.

### Funkcjonalności

* **Ruch i walka**: zwierzęta poruszają się losowo i rozstrzygają kolizje na podstawie siły z wyjątkami dla Żółwia i Cyber-Owcy.
* **Rozmnażanie roślin**: pięć gatunków roślin rozprzestrzenia się wg ustalonych prawdopodobieństw.
* **Człowiek**: sterowany strzałkami, ze specjalną umiejętnością (`U`) aktywną przez 5 tur plus 5 tur odnowienia.
* **Interaktywne dodawanie**: tworzenie organizmów przez kliknięcie na planszy.
* **Log zdarzeń**: panel `JTextArea` wyświetla przewijaną historię zdarzeń (jedzenie, walka, wzrost).

### Struktura katalogów

```text
src/main/java/pl/npnpdev/virtualworld/
├── Antylopa.java
├── BarszczSosnowskiego.java
├── Czlowiek.java
├── Gra.java
├── Guarana.java
├── Lis.java
├── Main.java
├── Mlecz.java
├── Organizm.java
├── Owca.java
├── Pole.java
├── PlantFactory.java
├── Roslina.java
├── Swiat.java
├── SwiatHex.java
├── SwiatKratowy.java
├── Trawa.java
├── WilczeJagody.java
├── Wilk.java
├── Zwierze.java
└── UI/
    ├── MainFrame.java
    ├── WorldPanel.java
    └── EventLogPanel.java
```

### Sterowanie

* **Strzałki**: ruch Człowieka
* **Kliknięcie myszy**: dodawanie organizmu na planszy
* **Menu**: przejście tury, zapis/odczyt stanu świata

---

## Kontakt / Contact

* **Igor Tomkowicz**
* GitHub: [npnpdev](https://github.com/npnpdev)
* LinkedIn: [Igor Tomkowicz](https://www.linkedin.com/in/igor-tomkowicz-a5760b358/)
* E-mail: [npnpdev@gmail.com](mailto:npnpdev@gmail.com)

---

## Licencja / License

Project available under the **MIT** license. See [LICENSE](LICENSE) for details.
