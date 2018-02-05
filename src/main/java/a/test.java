package a;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long n = 30000;
		System.out.println("Start... " + n);

		long start1 = System.currentTimeMillis();
		String s1 = new String("hello");
		for (long i = 0; i < n; i++) {
			s1 += "拼接字符串的时间";
		}
		long end1 = System.currentTimeMillis();
		long time1 = end1 - start1;
		System.out.println("用String+=拼接字符串的时间" + time1);

		long start2 = System.currentTimeMillis();
		String s2 = new String("hello");
		for (long i = 0; i < n; i++) {
			s2 = s2 + "拼接字符串的时间";
		}
		long end2 = System.currentTimeMillis();
		long time2 = end2 - start2;
		System.out.println("用String=String+拼接字符串的时间" + time2);

		long start3 = System.currentTimeMillis();
		String s3 = new String("hello");
		for (long i = 0; i < n; i++) {
			s3 = s3.concat("拼接字符串的时间");
		}
		long end3 = System.currentTimeMillis();
		long time3 = end3 - start3;
		System.out.println("用String.concat拼接字符串的时间" + time3);

		long start4 = System.currentTimeMillis();
		StringBuffer s4 = new StringBuffer("hello");
		for (long i = 0; i < n; i++) {
			s4.append("拼接字符串的时间");
		}
		long end4 = System.currentTimeMillis();
		long time4 = end4 - start4;
		System.out.println("用StringBuffer.append拼接字符串的时间" + time4);

		long start5 = System.currentTimeMillis();
		StringBuilder s5 = new StringBuilder("hello");
		for (long i = 0; i < n; i++) {
			s5.append("拼接字符串的时间");
		}
		long end5 = System.currentTimeMillis();
		long time5 = end5 - start5;
		System.out.println("用StringBuilder.append拼接字符串的时间" + time5);

		System.out.println("End...");
	}

}