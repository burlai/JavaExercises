package container;

public class container {
    private int[] bitset;  // Масив int[], де кожен int зберігає 32 числа у вигляді бітів
    private int size;  // Максимальне значення числа (верхня межа)

    // Ініціалізуємо контейнер
    public void init(int size) {
        this.size = size;
        // Створюємо масив для бітів. Кожен int містить 32 біти, тому треба (size + 31) / 32 елементів
        this.bitset = new int[(size + 31) / 32];
    }

    // Додає число в контейнер (якщо його ще немає)
    public boolean add(int a) {
        if (contains(a)) return false; // Якщо число вже є, не додаємо повторно
        int index = a / 32;  // Визначаємо, в якому елементі масиву знаходиться потрібний біт
        int bitPosition = a % 32;  // Визначаємо позицію біта всередині int
        bitset[index] |= (1 << bitPosition); // Увімкнути (зробити 1) відповідний біт
        return true;
    }

    // Перевіряє, чи є число в контейнері
    public boolean contains(int a) {
        int index = a / 32;  // Визначаємо, в якому елементі масиву шукати біт
        int bitPosition = a % 32;  // Визначаємо позицію біта всередині int
        return (bitset[index] & (1 << bitPosition)) != 0; // Перевіряємо, чи біт увімкнений
    }

    // Видаляє число з контейнера
    public boolean remove(int a) {
        if (!contains(a)) return false; // Якщо числа немає, нічого не робимо
        int index = a / 32;  // Визначаємо, в якому елементі масиву знаходиться біт
        int bitPosition = a % 32;  // Визначаємо позицію біта всередині int
        bitset[index] &= ~(1 << bitPosition); // Вимкнути (зробити 0) відповідний біт
        return true;
    }

    public static void main(String[] args) {
        judge.run(new container()); // Запускаємо тестовий скрипт
    }
}


