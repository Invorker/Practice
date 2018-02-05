package test;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class Snippet {

	public static void main(String[] args) {
		Map map = System.getenv();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			System.out.print(entry.getKey() + "=");
			System.out.println(entry.getValue());
		}

//		Properties properties = System.getProperties();
//		Iterator it2 = properties.entrySet().iterator();
//		while (it2.hasNext()) {
//			Entry entry = (Entry) it2.next();
//			System.out.print(entry.getKey() + "=");
//			System.out.println(entry.getValue());
//		}
	}

}
