package user;

/**
 * Created by Ethan Zhang on 15/6/24.
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(Test.class.getClassLoader().getResourceAsStream("processes/simple.bpmn"));
    }
}
