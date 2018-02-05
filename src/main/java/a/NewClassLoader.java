package a;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * �Զ�����������
 * 
 * @author linxz
 * 
 */
public class NewClassLoader extends ClassLoader {
	private static NewClassLoader loader = new NewClassLoader();

	/**
	 * @param name
	 *            class����ļ���
	 */
	// @Override
	// protected Class<?> findClass(String name) throws ClassNotFoundException {
	// byte[] datas = loadClassData(name,location);//��class�ļ������ݶ��뵽byte������
	// return defineClass(name, datas, 0, datas.length);//ͨ��byte�������Class����
	// }
	/**
	 * @param name
	 *            class ����ļ���
	 * @param pack
	 *            �����ڵİ��� eg:com.szelink.test. ������Ҫ����"."<br>
	 * @param location
	 *            ���ļ���·�����趨Ŀ¼ʱ����Ҫ��������"/" �Զ���·����ȡClass����
	 */
	public static Class<?> findClassByNameAndLocation(String name, String pack, File location)
			throws ClassNotFoundException {
		byte[] datas = loader.loadClassData(name, location); // ��class�ļ������ݶ��뵽byte������
		pack = loader.convert(pack);
		return loader.defineClass(pack + name, datas, 0, datas.length);// ͨ��byte�������Class����
	}

	/**
	 * 
	 * @param pack
	 *            �����ڵİ��� eg:com.szelink.test. ������Ҫ����"."
	 * @param location
	 *            �����ڵ�·��
	 * @return
	 */
	public static Map<String, Class> findClassesByLocation(String pack, File location) {
		// �г����е�.class�ļ�
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
	 * ��.class�ļ����뵽byte[]������
	 * 
	 * @param name
	 *            Ҫ��ȡ���ļ�����
	 * @param location
	 *            Ҫ��ȡ���ļ�Ŀ¼
	 * @return ��ȡ����byte[]����
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
	 * ת������
	 * 
	 * @param pack
	 *            Ҫת���İ���
	 * @return
	 */
	private String convert(String pack) {
		String result = "";
		result = pack.replace('/', '.');// �����е�/ת��.
		result = result.replace('\"', '.');// �����е�\ת��.
		result = result.replace('\\', '.');
		if (!(result.substring(result.length() - 1)).equals(".")) {// �ж����һλ�Ƿ�Ϊ.�������ǣ�����һ��.
			result += ".";
		}
		return result;
	}
}