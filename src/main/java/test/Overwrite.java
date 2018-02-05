package test;

import java.io.IOException;

public class Overwrite {
	protected CharSequence print(Number a) throws IOException {
		return "123";
	}
}

class SubClass extends Overwrite {
	protected CharSequence print(Number a) throws IOException {
		return "123";
	}
}