package task2;

import org.junit.*;
import org.junit.rules.TestName;
import task1.Person;
import task1.Student;

import javax.swing.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class JUnitTestDemo {
    @Rule
    public TestName testName = new TestName();
    @BeforeClass // 第一个测试之前输出
    public static void beforeMessage() {
        System.out.println("测试开始......");
        System.out.println("---------------------------------------------------------");
    }
    @AfterClass // 全部测试完之后输出
    public static void afterMessage() {
        System.out.println("---------------------------------------------------------");
        System.out.println("测试结束");
    }
    @Before
    public void showMethodBegin() {
        System.out.println(testName.getMethodName() + "开始测试...");
    }
    @After
    public void showMethodEnd() {
        System.out.println(testName.getMethodName() + "测试结束");
        System.out.println("---------------------------------------------------------");
    }
    @Ignore
    @Test
    public void TestAdd() {
        int x = 0, y = 10;
        Student student = new Student() {
            @Override
            public int addOperation(int a, int b) {
                return a + b;
            }
        };
        int result = x + y;
        Assert.assertEquals(result, student.addOperation(x, y));
    }
    @Test
    public void TestDeprecated() {
        Person person = new Person();
        person.deprecatedTest();
    }
    @Test
    public void TestEat() {
        double t = 100.0;
        double result = t;
        Person person = new Person();
        Assert.assertEquals(result, person.eat(t), 0);
    }
    @Test
    public void TestLearn() {
        double t = 1010.0;
        Student student = new Student() {
            @Override
            public int addOperation(int a, int b) {
                return 0;
            }
        };
        student.learnedTime = 20;
        double result = student.learnedTime + t;
        Assert.assertEquals(result, student.learn(t), 0);
        t = -20;
        result = student.learnedTime;
        Assert.assertEquals(result, student.learn(t), 0);
    }
    @Test(timeout = 100)
    public void TestShow() {
        Student student = new Student() {
            @Override
            public int addOperation(int a, int b) {
                return a + b;
            }
        };
        student.showInfo();
    }
}
