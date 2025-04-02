package cnt;
import java.util.*;

public class CNT {
    public int popCount(int value) {
        // Вбудований до Java механізм
//        return Integer.bitCount(value);
        
        // Рішення:
         int count = 0; // Створюємо змінну count, яка буде зберігати кількість одиничних бітів.
         while (value != 0) { // Поки число value не стало рівним 0, продовжуємо працювати.
             value &= (value - 1); // Ця операція знімає найправішу одиничку у двійковому представленні value.
             count++; // Збільшуємо лічильник, оскільки тільки що видалили одну одиничку.
         }
         return count; // Повертаємо підраховану кількість одиничок.
    }

    public static void main(String[] args) {
        Judge.run(new CNT());
    }
}
