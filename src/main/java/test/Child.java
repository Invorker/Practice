package test;

interface ICount {
	int count();
}

class Parent {
	int i = 0;

	int count() {
		return i++;
	}
}

public class Child extends Parent {
	ICount getCount(int i) {
		return new ICount() {
			public int count() {
				return (i);
			}
		};
	}

	public static void main(String[] args) {
		Child c = new Child();
		System.out.println(c.count());
		System.out.println(c.count());
		ICount ic = c.getCount(8);
		System.out.println(ic.count());
		System.out.println(ic.count());
	}
}
