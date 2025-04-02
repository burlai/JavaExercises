package partition;

import java.util.HashSet;

public class Partition_ready {
    boolean checkPartition(int[] array, int n) {
    	// Обчислюємо загальну суму всіх елементів і вкладаємо в змінну totalSum
        int totalSum = 0;
        for (int i = 0; i < array.length; i++) {
        	totalSum += array[i];
        }

        // Якщо сума непарна, то розбиття неможливе --> повертаємо false.
        if (totalSum % 2 != 0) {
            return false;
        }

        // Обчислюємо targetSum — суму, яку повинна мати кожна з двох підмножин
        int targetSum = totalSum / 2;

        HashSet<Integer> possibleSums = new HashSet<>(); // possibleSums — це множина всіх можливих сум, які можна отримати, додаючи елементи з масиву.
        possibleSums.add(0); // Спочатку у множині є тільки 0, тому що без жодних елементів сума дорівнює нулю.

        for (int num : array) { // Перебираємо всі елементи вхідного масиву
            HashSet<Integer> newSums = new HashSet<>(); // newSums — нова множина, куди будемо записувати нові можливі суми, які з'являться після додавання num.

            for (int sum : possibleSums) {
                int currentSum = sum + num;
                if (currentSum == targetSum) {
                    return true;
                }
                newSums.add(currentSum);
            }

            possibleSums.addAll(newSums);
        }

        return possibleSums.contains(targetSum);
    }

    public static void main(String[] args) {
        judge.run(new Partition_ready());
    }
}
