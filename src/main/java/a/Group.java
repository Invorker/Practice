package a;

import jersey.repackaged.com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Group {


    public static class A {
        private String i;
        private String j;


        public A(String i, String j) {
            this.i = i;
            this.j = j;
        }

        public A(String i) {
            this.i = i;
        }

        public String getI() {
            return i;
        }

        public String getJ() {
            return j;
        }


        @Override
        public  int hashCode(){
            return this.i.hashCode();
        };
        @Override
        public boolean equals(Object obj) {
            return (this.i.equals(((A) obj).getI()));
        }
    }

    public static void main(String[] args) {
        A a1 = new A("q", "q");
        A a2 = new A("q", "w");
        A a3 = new A("w", "q");
        A a4 = new A("w", "w");
        A a5 = new A("e", "q");

        List<A> l = Lists.newArrayList(a1, a2, a3, a4, a5);
        Map<A, List<A>> collect = l.stream().collect(Collectors.groupingBy(
                a -> new A(a.getI())));
        System.out.println("f");

    }
}
