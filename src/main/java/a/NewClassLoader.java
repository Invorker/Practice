package a;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义的类加载器
 * 
 * @author linxz
 * 
 */
public class NewClassLoader extends ClassLoader {
	private static NewClassLoader loader = new NewClassLoader();

	/**
	 * @param name
	 *            class类的文件名
	 */
	// @Override
	// protected Class<?> findClass(String name) throws ClassNotFoundException {
	// byte[] datas = loadClassData(name,location);//将class文件的数据读入到byte数组中
	// return defineClass(name, datas, 0, datas.length);//通过byte数组加载Class对象
	// }
	/**
	 * @param name
	 *            class 类的文件名
	 * @param pack
	 *            类所在的包名 eg:com.szelink.test. 后面需要带个"."<br>
	 * @param location
	 *            类文件的路径，设定目录时，需要在最后带上"/" 自定义路径获取Class对象
	 */
	public static Class<?> findClassByNameAndLocation(String name, String pack, File location)
			throws ClassNotFoundException {
		byte[] datas = loader.loadClassData(name, location); // 将class文件的数据读入到byte数组中
		pack = loader.convert(pack);
		return loader.defineClass(pack + name, datas, 0, datas.length);// 通过byte数组加载Class对象
	}

	/**
	 * 
	 * @param pack
	 *            类所在的包名 eg:com.szelink.test. 后面需要带个"."
	 * @param location
	 *            包所在的路径
	 * @return
	 */
	public static Map<String, Class> findClassesByLocation(String pack, File location) {
		// 列出所有的.class文件
		File[] childFiles = location.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.indexOf(".class") <= -1)
					return false;
				else
					return true;
			}
		});
		Map<String, Class> map = new HashMap<String, Class>();
		for (File f : childFiles) {
			String name = f.getName().substring(0, f.getName().indexOf(".class"));
			Class c = null;
			try {
				c = findClassByNameAndLocation(name, pack, location);
				System.out.println(c.getName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put(name, c);
		}
		return map;
	}

	/**
	 * 将.class文件读入到byte[]数组中
	 * 
	 * @param name
	 *            要读取的文件名称
	 * @param location
	 *            要读取的文件目录
	 * @return 读取到的byte[]数组
	 */
	protected byte[] loadClassData(String name, File location) {
		FileInputStream fis = null;
		byte[] datas = null;
		try {
			// File dir=new File(location);
			File classFile = new File(location, name + ".class");
			fis = new FileInputStream(classFile);
			// fis = new FileInputStream(location+name+".class");
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int b;
			while ((b = fis.read()) != -1) {
				bos.write(b);
			}
			datas = bos.toByteArray();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return datas;

	}

	/**
	 * 转换包名
	 * 
	 * @param pack
	 *            要转换的包名
	 * @return
	 */
	private String convert(String pack) {
		String result = "";
		result = pack.replace('/', '.');// 将所有的/转成.
		result = result.replace('\"', '.');// 将所有的\转成.
		result = result.replace('\\', '.');
		if (!(result.substring(result.length() - 1)).equals(".")) {// 判断最后一位是否为.，若不是，加上一个.
			result += ".";
		}
		return result;
	}
}