package classloader;

public class TestAntiSingleton {
	public static void main(String[] args) throws Exception {
		AntiSingleton instance = AntiSingleton.getInstance();
		NewClassLoader loader = new NewClassLoader();
		IAntiSingleton newObj = loader.createNewOne();
		System.out.println(AntiSingleton.getInstance() == newObj);
		Class a = loader.getClazz();
		Class b = loader.getClazz();
		System.out.println(a);
		System.out.println(b);
		System.out.println(a==b);
	}
}