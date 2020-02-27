package a;// 本题为考试多行输入输出规范示例，无需提交，不计分。

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main3 {
//    private static List<Integer> allNum = getAllPrime(1000000);
//    private static HashSet<Integer> allNumSet = new HashSet<>(allNum);

    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        List<Integer> a = new ArrayList<>();
//        while (sc.hasNextInt()) {
//            int i = sc.nextInt();
//            if (i == 0) {
//                break;
//            }
//            a.add(i);
//        }
//        a.forEach(i -> {
//            if (i > 0) {
//                System.out.println(getPosible(i));
//            }
//        });
        long s1 = System.currentTimeMillis();
        List<Integer> allPrime = getAllPrime(1000000);
        System.out.println(allPrime.size());
        long s2 = System.currentTimeMillis();
        System.out.println(s2 - s1);
        List<Integer> allPrime2 = getAllPrime2(1000000);
        System.out.println(allPrime2.size());
        System.out.println(System.currentTimeMillis() - s2);
//        System.out.println(getPosible(290));
        System.out.println("end");
    }

    private static List<Integer> getAllPrime(int max) {
        List<Integer> primes = new ArrayList<>();
        primes.add(2);
        for (int i = 3; i <= max; i += 2) {
            boolean isPrime = true;
            for (int j = 0; j < primes.size() && isPrime; j++) {
                if (i % primes.get(j) == 0) {
                    isPrime = false;
                }
            }
            if (isPrime) {
                primes.add(i);
            }
        }
        return primes;
    }

//    private static int getPosible(int num) {
//        int pos = 0;
//        for (int i = 1; allNum.get(i) <= num / 2; i++) {
//            if (allNumSet.contains(num - allNum.get(i))) {
//                pos++;
//            }
//        }
//        return pos;
//    }

    private static List<Integer> getAllPrime2(int max) {
//        byte[] a = new byte[max / 8];

        Set<Integer> nums = new HashSet<>(100000);
        for (int i = 2; i < 1000; i++) {
            for (int j = i; j <= max / i; j++) {
                nums.add(i * j);
            }
        }
        List<Integer> nums2 = new ArrayList<>();
        for (int i = 1; i < max; i++) {
            if (!nums.contains(i)) {
                nums2.add(i);
            }
        }
        return nums2;
    }

    private static void set(byte[] a, int index) {
//        ,0x80
        byte[] b = new byte[]{0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40};
        int i = index / 8;
        int j = index % 8;
//       a[i] = byte((int)a[i] | (int)b[j]);

    }

    private static boolean get(byte[] a, int index) {
        return true;
    }

}