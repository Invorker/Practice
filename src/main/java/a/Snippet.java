package a;

import java.lang.reflect.Method;

public class Snippet {
	// public static void main(String[] args) {
	// String classDataRootPath = "D:\\workspace\\Practice\\bin";
	// FileSystemClassLoader fscl1 = new
	// FileSystemClassLoader(classDataRootPath);
	// FileSystemClassLoader fscl2 = new
	// FileSystemClassLoader(classDataRootPath);
	// String className = "a.Sample";
	// try {
	// Class<?> class1 = fscl1.loadClass(className);
	// Object obj1 = class1.newInstance();
	// Class<?> class2 = fscl2.loadClass(className);
	// Object obj2 = class2.newInstance();
	// Method setSampleMethod = class1.getMethod("setSample",
	// java.lang.Object.class);
	// setSampleMethod.invoke(obj1, obj1);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	public static void main(String[] args) {
		Integer a = 1;
		Integer b = 2;
		Integer c = 3;
		Integer d = 3;
		Integer e = 321;
		Integer f = 321;
		Long g = 3L;
		System.out.println(c == d);// true
		System.out.println(e == f);// false
		System.out.println(c == (a + b));// true
		System.out.println(c.equals(a + b));// true
		System.out.println(g == (a + b));// false
		System.out.println(g.equals(a + b));// true

	}
}
