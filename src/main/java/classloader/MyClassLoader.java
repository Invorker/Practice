package classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;

import org.apache.tomcat.util.http.fileupload.IOUtils;

public class MyClassLoader extends ClassLoader {

	private String name;

	private String classPath;

	public MyClassLoader(String name) {

		super(null);

		this.name = name;

	}

	@Override

	protected Class<?> findClass(String name) throws ClassNotFoundException {

		byte[] b = getClassBytes(name);

		return this.defineClass(name, b, 0, b.length);

	}

	private byte[] getClassBytes(String name) {

		String classFullPath = classPath + "/" + name.replace(".", "/") + ".class";

		byte[] data = null;

		try {

			FileInputStream fileInputStream = new FileInputStream(classFullPath);

			ByteArrayOutputStream out = new ByteArrayOutputStream();

			IOUtils.copy(fileInputStream, out);

			data = out.toByteArray();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return data;

	}

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public String getClassPath() {

		return classPath;

	}

	public void setClassPath(String classPath) {

		this.classPath = classPath;

	}

	public static void testClassLoader() throws Exception {
		Singleton singleton;
		
		singleton = Singleton.getInstance();

		MyClassLoader myClassLoader = new MyClassLoader("myClassLoader");

		myClassLoader.setClassPath("D:/workspace/Practice/bin");

		Class singletonClass = myClassLoader.findClass("classloader.Singleton");

		System.out.println("singletonClass.getClassLoader() : " + singletonClass.getClassLoader());

		System.out.println("Singleton.class==singletonClass : " + (Singleton.class == singletonClass));

		System.out.println("Singleton.class.equals(singletonClass) : " + (Singleton.class.equals(singletonClass)));

		Constructor constructor1 = Singleton.class.getDeclaredConstructor();

		Constructor constructor2 = Singleton.class.getDeclaredConstructor();

		Constructor constructor3 = singletonClass.getDeclaredConstructor();

		System.out.println("constructor1==constructor2 : " + (constructor1 == constructor2));

		System.out.println("constructor1.equals(constructor2) : " + constructor1.equals(constructor2));

		System.out.println("constructor1==constructor : " + (constructor1 == constructor3));

		System.out.println("constructor1.equals(constructor3) : " + constructor1.equals(constructor3));

		constructor1.setAccessible(true);

		Object singleton1 = constructor1.newInstance();

		constructor3.setAccessible(true);

		Object singleton3 = constructor3.newInstance();

		System.out.println("singleton : " + singleton);

		System.out.println("singleton1 : " + singleton1);

		System.out.println("singleton3 : " + singleton3);

		System.out.println("singleton1==singleton3 : " + (singleton1 == singleton3));

	}

	public static void main(String[] args) {
		try {
			MyClassLoader.testClassLoader();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}