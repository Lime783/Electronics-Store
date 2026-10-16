# 1. Opis projektu

Celem projektu jest stworzenie kompletnego systemu sklepu internetowego umożliwiającego przeglądanie i zakup produktów
elektronicznych, takich jak komputery, smartfony i inne urządzenia elektroniczne.

Klienci mogą przeglądać dostępne produkty, dodawać je do koszyka, usuwać z niego oraz składać zamówienia z fakturą lub
bez niej.

## 1.1. Kategorie produktów

| Kategoria produktu | Dodatkowe właściwości                                   |
|--------------------|---------------------------------------------------------|
| Komputer           | Procesor, ilość pamięci RAM, obudowa                    |
| Smartfon           | Kolor, pojemność baterii, dodatkowe akcesoria, np. etui |
| Inna elektronika   | Podstawowe parametry                                    |

Każdy produkt posiada również identyfikator, nazwę, cenę oraz dostępną ilość.

## 1.2. Obsługa klientów i koszyka

Klienci mogą korzystać z funkcjonalności sklepu, zarządzać swoim koszykiem oraz składać zamówienia. Koszyk umożliwia
przechowywanie wybranych produktów i sprawdzanie łącznej wartości zakupów.

| Element            | Opis                                                     |
|--------------------|----------------------------------------------------------|
| Klient             | Adres e-mail, numer telefonu i hasło                     |
| Koszyk             | Unikalny identyfikator, lista produktów i łączna wartość |
| Produkty w koszyku | Możliwość dodawania, przeglądania i usuwania             |

## 1.3. Proces realizacji zamówień

Każde zamówienie zawiera informacje o kliencie, przypisanym koszyku oraz dacie złożenia.

Zamówienia posiadają cztery możliwe stany:

| Stan zamówienia | Opis                                                     |
|-----------------|----------------------------------------------------------|
| `OCZEKUJĄCE`    | Stan początkowy, nadawany po złożeniu zamówienia         |
| `POTWIERDZONE`  | Zamówienie potwierdzone po symulowanym uiszczeniu opłaty |
| `ANULOWANE`     | Zamówienie zostało anulowane                             |
| `SKOŃCZONE`     | Realizacja zamówienia została zakończona                 |

### Przebieg realizacji zamówienia

``` text
OCZEKUJĄCE
     |
     | Symulacja płatności
     v
POTWIERDZONE
     |
     | Zakończenie realizacji
     v
SKOŃCZONE
````

Zamówienie rozpoczyna proces od stanu oczekującego. Po symulacji płatności może zostać potwierdzone, a po zakończeniu
realizacji otrzymuje stan skończony. Zamówienie może również zostać anulowane zgodnie z regułami biznesowymi systemu.

Wszystkie zmiany stanów są zapisywane w pliku `archive.txt` wraz z opisem zamówienia i datą zmiany.

## 1.4. System rabatowy

Sklep posiada system rabatowy, który automatycznie dobiera korzystniejszą promocję na podstawie godziny zakupu oraz
liczby produktów w zamówieniu.

| Warunek                   | Wysokość rabatu |
|---------------------------|-----------------|
| Zakup po godzinie 16:00   | 15%             |
| Zakup minimum 3 produktów | 25%             |
| Spełnienie obu warunków   | 25%             |

W przypadku spełnienia obu warunków stosowany jest rabat w wysokości 25%, ponieważ jest korzystniejszy dla klienta.
Rabaty nie sumują się.

System został zaprojektowany z myślą o dalszym rozwoju i umożliwia dodawanie kolejnych promocji.

## 1.5. Wielowątkowość i archiwizacja

System umożliwia równoczesną realizację wielu zamówień z wykorzystaniem wielowątkowości. Każde zamówienie może być
przetwarzane niezależnie od pozostałych.

Najważniejsze założenia:

- Niezależna realizacja wielu zamówień.
- Bezpieczna obsługa równoczesnych zapisów do archiwum.

## 1.6. Interfejs użytkownika

System można intuicyjnie testować za pomocą interfejsu CLI (Command-Line Interface), który umożliwia obsługę sklepu z
poziomu terminala.

| Funkcjonalność CLI       | Możliwe działanie                                                |
|--------------------------|------------------------------------------------------------------|
| Przeglądanie produktów   | Wyświetlanie dostępnych produktów i ich parametrów               |
| Obsługa koszyka          | Dodawanie i usuwanie produktów oraz sprawdzanie wartości koszyka |
| Składanie zamówień       | Tworzenie zamówień z fakturą lub bez niej                        |
| Obsługa płatności        | Symulowanie płatności i potwierdzanie zamówień                   |
| Zarządzanie zamówieniami | Sprawdzanie i zmienianie stanów zamówień                         |

# 2. Zastosowane rozwiązania techniczne

## 2.1. Dziedziczenie i modelowanie produktów

W projekcie zastosowano dziedziczenie, w którym konkretne kategorie produktów rozszerzają abstrakcyjną klasę `Product`.
Klasa bazowa definiuje wspólne właściwości produktów, takie jak identyfikator, nazwa, cena i dostępna ilość.

``` java
public abstract class Product {
    private final UUID id;
    private String name;
    private BigDecimal price;
    private int amountAvailable;
}
```

Przykładowa klasa `Smartphone` rozszerza klasę `Product` o właściwości charakterystyczne dla smartfonów.

``` java
public class Smartphone extends Product {
    Accessory accessory;
    BatteryCapacity batteryCapacity;
    Color color;
}
```

Takie rozwiązanie ułatwia dodawanie kolejnych kategorii produktów bez konieczności modyfikowania wspólnej klasy bazowej.

## 2.2. Precyzja obliczeń finansowych

Do przechowywania cen oraz wykonywania obliczeń finansowych wykorzystywany jest typ `BigDecimal` zamiast `double`.
Pozwala to uniknąć typowych błędów precyzji zmiennoprzecinkowej podczas obliczania cen i rabatów.

## 2.3. Interfejsy repozytoriów

W projekcie zastosowano interfejsy określające metody, które muszą zostać zaimplementowane przez repozytoria
odpowiedzialne za zarządzanie danymi.

Przykładem jest interfejs `CartRepository`, który definiuje operacje dodawania, usuwania, wyszukiwania i pobierania
koszyków.

``` java
public interface CartRepository {
    void add(Cart cart);

    void deleteByID(UUID id);

    Optional<Cart> findCartByID(UUID id);

    Cart getCartByID(UUID id);

    List<Cart> getAllCarts();
}
```

Oddzielenie interfejsów od ich implementacji ułatwia rozwój projektu, testowanie oraz ewentualną zmianę sposobu
przechowywania danych.

## 2.4. Własne wyjątki

W celu czytelniejszej obsługi błędów zastosowano własne wyjątki o jednoznacznych nazwach, wskazujących rodzaj
występującego problemu.

Przykładem jest `DuplicateException`, wykorzystywany do reprezentowania błędów związanych z duplikatami.

``` java
public class DuplicateException extends RuntimeException {...}
```

Takie podejście ułatwia rozpoznawanie błędów i pozwala dostosować ich obsługę do konkretnych sytuacji występujących w
systemie.

## 2.5. Wielowątkowość

System wykorzystuje wielowątkowość do równoczesnej realizacji wielu zamówień. Każde zamówienie może być przetwarzane
niezależnie, co pozwala symulować równoległą obsługę klientów.

``` java
try (ExecutorService pool = Executors.newFixedThreadPool(8)){
        orders.forEach(order -> pool.submit(() -> processOrder(order)));
    }
```

## 2.6. Atomowa aktualizacja archiwum

Zmiany stanów zamówień są zapisywane w pliku `archive.txt`. W celu ograniczenia ryzyka udostępnienia niekompletnego
pliku zastosowano mechanizm zapisu do pliku tymczasowego, a następnie atomowej podmiany pliku docelowego.

``` java
Files.writeString(
    archiveTmp,
    message + "\n",
    StandardCharsets.UTF_8,
    StandardOpenOption.APPEND
);

Files.move(
    archiveTmp,
    archive,
    StandardCopyOption.REPLACE_EXISTING,
    StandardCopyOption.ATOMIC_MOVE
);
```

## 2.7. Rozszerzalny system rabatowy

Mechanizm rabatowy został zaprojektowany tak, aby można było rozszerzać go o kolejne promocje. System analizuje warunki
zakupów i wybiera najkorzystniejszy dostępny rabat, zamiast sumować wszystkie spełnione promocje.