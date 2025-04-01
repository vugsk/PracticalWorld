import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
		boolean validInput = false;
		
		while (!validInput) {
			try {
				System.out.println("Введите делитель (не равный нулю):");
				
				// Создаем задачу для получения ввода с таймером
				Future<Integer> future = executor.submit(() -> {
					return scanner.nextInt();
				});
				
				// Ждем ввод максимум 30 секунд
				int divisor = 0;
				try {
					divisor = future.get(30, TimeUnit.SECONDS);
				} catch (TimeoutException e) {
					future.cancel(true);
					throw new TimeoutException("Время ожидания ввода истекло (30 секунд)");
				}
				
				// Выполняем деление для проверки ArithmeticException
				int result = 100 / divisor;
				System.out.println("Результат деления 100 на " + divisor + " = " + result);
				validInput = true;
				
			} catch (ArithmeticException e) {
				System.out.println("Ошибка: деление на ноль невозможно. Попробуйте снова.");
			} catch (TimeoutException e) {
				System.out.println(e.getMessage());
				break; // Выходим из цикла при истечении времени
			} catch (InterruptedException | ExecutionException e) {
				System.out.println("Произошла ошибка при обработке ввода: " + e.getMessage());
				break;
			} catch (Exception e) {
				System.out.println("Некорректный ввод. Введите целое число. Попробуйте снова.");
				scanner.nextLine(); // Очищаем буфер ввода
			}
		}
		
		executor.shutdownNow();
		scanner.close();
		System.out.println("Программа завершена.");
	}
}