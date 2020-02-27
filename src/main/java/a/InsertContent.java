package a;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InsertContent {

    public static void insert(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path));
             BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/hb/workspace/AppOssMapService/OssMapService/bin/dev/gc2.log"))
        ) {
            String line = "";
            String[] arrs = null;
            while ((line = br.readLine()) != null) {
                StringBuilder sb = new StringBuilder(line);
                if (line.indexOf("ParNew") > 0 && line.indexOf("Allocation Failure") > 0) {
//        String line = "2019-12-25T15:25:42.695+0800: 116.028: [GC (Allocation Failure) 2019-12-25T15:25:42.695+0800: 116.028: [ParNew: 26872064K->2985728K(26872064K), 18.9216790 secs] 41341349K->31524266K(122843392K), 18.9218006 secs] [Times: user=184.06 sys=3.30, real=18.92 secs] \n";
                    String parNew = line.substring(line.indexOf("ParNew") + 8);

                    List<Integer> a = new ArrayList<>();
                    do {


                        String temp = parNew.substring(0, parNew.indexOf('K'));
                        parNew = parNew.substring(parNew.indexOf('K'));
                        parNew = parNew.substring(get2(parNew));
                        temp = temp.substring(get(temp));
                        Integer num = Integer.valueOf(temp);
                        a.add(num);


                    } while (a.size() < 6);
                    int i = a.get(0) - a.get(1);
                    int j = a.get(3) - a.get(4);
                    int k = i - j;
                    double v = (double) k / (double) i;

                    sb.append('[').append(k).append(',').append(v).append(']');
                }

                bw.write(sb.toString() + "\t\n");
            }

        }

    }

    private static int get(String s) {
        byte[] bytes = s.getBytes();
        for (int i = bytes.length - 1; i >= 0; i--) {
            if (!Character.isDigit(bytes[i])) {
                return i + 1;
            }
        }
        return 0;
    }

    private static int get2(String s) {
        byte[] bytes = s.getBytes();
        for (int i = 0; i <= bytes.length; i++) {
            if (Character.isDigit(bytes[i])) {
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args)
            throws IOException {
        insert("/Users/hb/workspace/AppOssMapService/OssMapService/bin/dev/gc.log");
    }
}

