import java.io.IOException;

public class Main {
	// Собственное исключение TimeoutException
	static class TimeoutException extends Exception {
		public TimeoutException(String message) {
			super(message);
		}
	}
	
	// Функция для ввода данных без использования Scanner
	public static int readInput() throws IOException {
		StringBuilder sb = new StringBuilder();
		int ch;
		while ((ch = System.in.read()) != '\n' && ch != -1) {
			if (ch != '\r') { // Игнорируем возврат каретки
				sb.append((char) ch);
			}
		}
		
		try {
			return Integer.parseInt(sb.toString().trim());
		} catch (NumberFormatException e) {
			throw new IOException("Некорректный ввод");
		}
	}
	
	public static void main(String[] args) {
		boolean validInput = false;
		
		while (!validInput) {
			try {
				System.out.println("Введите делитель (не равный нулю):");
				
				// Ждем 30 секунд для ввода
				long startTime = System.currentTimeMillis();
				long timeoutInMillis = 30 * 1000;
				int divisor = 0;
				boolean inputReceived = false;
				
				// Проверяем доступность данных для чтения
				while (!inputReceived && 
					  (System.currentTimeMillis() - startTime) < timeoutInMillis) {
					if (System.in.available() > 0) {
						divisor = readInput();
						inputReceived = true;
					}
				}
				
				if (!inputReceived) {
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
			} catch (IOException e) {
				System.out.println("Некорректный ввод. Введите целое число. Попробуйте снова.");
			} catch (Exception e) {
				System.out.println("Произошла ошибка: " + e.getMessage());
			}
		}
		
		System.out.println("Программа завершена.");
		System.exit(0); // Завершаем программу
	}
}