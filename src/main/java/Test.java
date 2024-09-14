import entity.BankAccount;
import service.ConcurrentBank;
import service.impl.ConcurrentBankImpl;

public class Test {

    public static void main(String[] args) {
        ConcurrentBank bank = new ConcurrentBankImpl();

        // Создание счетов
        BankAccount account1 = bank.createAccount(1000);
        BankAccount account2 = bank.createAccount(500);

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    if (Math.random() > 0.5) {
                        bank.transfer(account1, account2, (int) (Math.random() * 100));
                    } else {
                        bank.transfer(account2, account1, (int) (Math.random() * 100));
                    }
                }
            });
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Total balance: " + bank.getTotalBalance());
    }
}
