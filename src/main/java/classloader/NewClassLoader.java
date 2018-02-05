package classloader;

import java.io.InputStream;

public class NewClassLoader extends ClassLoader {
	public IAntiSingleton createNewOne() throws Exception {
		InputStream is = getClass().getResourceAsStream("AntiSingleton.class");
		byte[] b = new byte[is.available()];
		is.read(b);
		Class<?> clz = defineClass(null, b, 0, b.length);
		Object o = clz.newInstance();
		return (IAntiSingleton) o;
	}

	public Class getClazz() throws Exception {
		InputStream is = getClass().getResourceAsStream("AntiSingleton.class");
		byte[] b = new byte[is.available()];
		is.read(b);
		Class clz = defineClass(null, b, 0, b.length);
		return clz;
	}
}