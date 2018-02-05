package classloader;

import java.io.IOException;

import sun.misc.Launcher;

public class WrongClassLoaderTest {

	public static void main(String[] args) {
		try {
			WrongClassLoader loader = new WrongClassLoader();
			Class classLoaded = loader.loadClass("mail.Mail");
			
			ClassLoader SystemclassLoaded = 	ClassLoader.getSystemClassLoader();
			Class WrongClassLoaderTest = loader.loadClass("classloader.WrongClassLoaderTest");
			
			
			System.out.println(classLoaded.getName());
			System.out.println(classLoaded.getClassLoader());
			System.out.println(WrongClassLoaderTest.equals(WrongClassLoaderTest.class));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}