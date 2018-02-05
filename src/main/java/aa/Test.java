package aa;

public class Test {

	public static void main(String[] args) {

		System.out.println(null == null);

		int[] q = {};
		System.out.println(q.getClass());

		int[][] a = {};
		System.out.println(a.getClass());

		Object[][] b = {};
		System.out.println(b.getClass());

		Integer[][] c = {};
		System.out.println(c.getClass());
	}
}
