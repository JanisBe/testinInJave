package examples;

public class Person {
	public String name;
	public int age;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String greet(){
		return "I'm " + name;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}
}

