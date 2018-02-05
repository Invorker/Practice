package classloader;

public final class Singleton {

	private static final Singleton instance = new Singleton();

	private Singleton() {

		System.out.println("ִ�й��캯��");

		System.out.println("�������=" + this.getClass().getClassLoader());

	}

	public static Singleton getInstance() {

		return instance;

	}

}