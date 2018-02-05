package test;

public class Practice {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

class Account {
	private String accountNo;
	private double balance;
	private boolean flag;// false ¸Õ´æ

	public Account(String accountNo, double balance) {
		this.accountNo = accountNo;
		this.balance = balance;
		flag = false;
	}

	public double getBalance() {
		return balance;
	}

	public synchronized double store(double money) {
		if (flag == true)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		this.balance += money;
		flag = true;
		notifyAll();
		return balance;
	}

	public synchronized double get(double money) {
		if (flag == false)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		this.balance -= money;
		flag = false;
		notifyAll();
		return balance;
	}
}