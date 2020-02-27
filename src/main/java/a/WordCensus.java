package a;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class WordCensus {
    /**
     * key为数量，value为出现key数量次的单词集合
     */
    private static Map<Integer, List<String>> m = new HashMap();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String path = "/Users/hb/Downloads/novel";
        File file = new File(path);
        //按平均一个文件1M算，共计约2百万个文件，若都在一个文件夹下，还要考虑优化方法，避免file数组太大影响内存
        File[] fs = file.listFiles();
        int threadNum = Runtime.getRuntime().availableProcessors() - 1;
        int filesPerThread = (int) Math.ceil((double) fs.length / threadNum);
        Trie[] tries = new Trie[threadNum];
        IntStream.of(threadNum).forEach(i -> {
            tries[i] = new Trie();
            new Thread(new R(tries[i], fs, i, filesPerThread)).start();

        });
        //将tries数组，用递归方法，两两合并，最后得到一颗树，然后遍历得到结果（这里合并两棵树的方法没实现，
        // 还有一种思路是只使用一棵树，将它实现为线程安全的，可以让多个线程并发操作，考虑到竞争带来的性能影响，可能不如前一种方案）
        //如果需要输出，只要将结果的hashMap 的keyset 排序，然后输出即可
        //这里hashmap不是最好的方法，可以用数组实现，下标n的元素代表出现n+1次的单词集合。考虑到单词数量特性，个别高频单词
        //比如"a"，在2T的文件里可能出现上亿次，数组大小不好确定，而且有许多无用空洞，所以也不是很好的方法。
        //可以使用有序树，应该是最紧凑，节省内存的方法了。
//        traversal(root);
        System.out.println(System.currentTimeMillis() - start);
    }

    private static class R implements Runnable {
        private Trie t;
        private File[] files;
        private int threadIndex;
        private int filesPerThread;

        public R(Trie t, File[] files, int threadIndex, int filesPerThread) {
            this.t = t;
            this.files = files;
            this.threadIndex = threadIndex;
            this.filesPerThread = filesPerThread;
        }

        @Override
        public void run() {
            //这里下标可能有bug，没仔细核算
            int start = filesPerThread * (threadIndex), end = filesPerThread * (threadIndex + 1) - 1;
            for (int i = start; i <= end; i++) {
                if (!files[i].isDirectory()) {
                    String[] strArray = files[i].getName().split("\\.");
                    if (strArray.length > 0 && "txt".equals(strArray[strArray.length - 1])) {
                        analysisFile(files[i]);
                    }
                }
            }

        }

        private void analysisFile(File file) {
            Trie root = t;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String s;
                while ((s = br.readLine()) != null) {
                    char[] chars = s.toCharArray();
                    analysisLine(chars, root);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void analysisLine(char[] c, Trie t) {
            Trie temp = t;
            for (char c1 : c) {
                if (c1 >= 'A' && c1 <= 'Z' ) {
                    c1 = (char) (c1 - 'A' + 'a' );
                }
                if (c1 >= 'a' && c1 <= 'z' ) {
                    if (temp.children == null) {
                        temp.children = new Trie[26];
                    }
                    Trie child = temp.children[c1 - 'a'];
                    if (child == null) {
                        child = new Trie();
                        temp.children[c1 - 'a'] = child;
                    }
                    temp = child;
                } else {
                    if (temp != t) {
                        temp.count++;
                    }
                    temp = t;
                }
            }
            if (temp != t) {
                temp.count++;
            }
        }

    }

    /**
     * 遍历一棵前缀数，将所有单单词的统计结果放到hashmap中
     *
     * @param t 前缀树
     */
    private static void traversal(Trie t) {
        calculate("", t, -1);
    }

    /**
     * 递归方法
     *
     * @param prefix 父节点传递下来的单词前缀
     * @param t      当前节点代表的树
     * @param index  当前节点在父节点children数组的下标
     */
    private static void calculate(String prefix, Trie t, int index) {
        char c = (char) ('a' + index);
        String b = index >= 0 ? prefix + c : prefix;
        if (t.count > 0) {
            List<String> strings = m.computeIfAbsent(t.count, k -> new ArrayList<>());
            strings.add(b);
        }
        if (t.children != null) {
            for (int i = 0; i < t.children.length; i++) {
                if (t.children[i] != null) {
                    calculate(b, t.children[i], i);
                }
            }
        }
    }
}

class Trie {
    int count;
    Trie[] children;  //长度固定为26，代表26个英文字母
}