package examples;

public class SomeService {

	public String greet(String greet) {
		return "Hi I'm " + greet;
	}

	public int getAge(int age) {
		return age * 2;
	}

	public static String getName() {
		return "Hi I'm static";
	}
}
