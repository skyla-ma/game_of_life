
public class Tester {

	public static void main(String[] args) {
		Life test = new Life("testinputs/glider.txt");
		System.out.println(test);
		
		test.step();
		System.out.println(test);
		
		test.step(3);
		System.out.println(test);
	}
}