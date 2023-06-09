package task3;

import task1.Person;
import java.util.List;
import java.util.Random;

interface MyConsumer {
    void accept(int x);
}
interface MySupplier{
    Integer[] get();
}
interface MyFunction {
    String apply(int x, int y);
}
interface MyPredicate{
    boolean test(String t);
}

public class Demo3 {
    public static void print(int arg) {
        System.out.println("这是接口MyConsumer方法引用，引用的方法，打印传入的参数：" + arg);
    }
    public Integer[] gen() {
        System.out.println("因为构造数组时，需要长度参数，MySupplier接口的方法没有参数，因此创建此函数返回Integer数组");
        Integer[] arr = new Integer[10];
        Random r = new Random();
        for(int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(0, 100);
        }
        return arr;
    }
    public static String rand(int x, int y) {    // 返回一个随机整数
        Random random = new Random();
        System.out.println("生成" + x + "~" + y + "之间的一个整数");
        return "" + random.nextInt(x, y);
    }
    public static boolean judge(String s) {
        if(s.compareTo("abcdefg") > 0) {
            return true;
        }
        return false;
    };

    public static void main(String[] args) {
        // lambda表达式方式调用接口
        MyConsumer myConsumer = (arg) -> {  //打印以下传入的参数
            System.out.println("这里是MyConsumer接口，有参无返，传入的参数为：" + arg);
        };
        MySupplier mySupplier = ()->{   // 创建一个长度为10的数组，存储十个随机数
            System.out.println("这里是MySupplier接口，无参有返, 这里创建了10个随机整数返回");
            Integer[] arr = new Integer[10];
            Random r = new Random();
            for(int i = 0; i < arr.length; i++) {
                arr[i] = r.nextInt(0, 100);
            }
            return arr;
        };
        MyFunction myFunction = (arg, arg1) -> {    // 以字符串的形式返回 arg 的 arg1次幂
            int res = 1;
            for(int i = 0; i < arg1; i++) {
                res = res * arg;
            }
            return "" + res;
        };
        MyPredicate myPredicate = (arg)->{  // 如果传入的字符串是 MyPredicate 则返回 true，否则返回 false
            if(arg.equals("MyPredicate")) {
                return true;
            }
            return false;
        };

        // 方法引用式调用接口
        MyConsumer myConsumer1 = Demo3::print;  // 类名 :: 静态方法
        MySupplier mySupplier1 = new Demo3()::gen; // 对象名 :: 非静态方法
        MyFunction myFunction1 = Demo3::rand;
        MyPredicate myPredicate1 = Demo3::judge;

        myConsumer.accept(20);
        myConsumer1.accept(30);
        Integer[] arr = mySupplier.get();
        Integer[] arr1 = mySupplier1.get();
        System.out.println("mySupplier返回输出");
        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        System.out.println("mySupplier1返回输出");
        for(int i = 0; i < arr1.length; i++) {
            System.out.print(arr1[i] + " ");
        }
        System.out.println();
        System.out.println("myFunction.accept方法：" + myFunction.apply(20, 2));
        System.out.println("myFunction1.accept方法：" + myFunction1.apply(20, 1000));
        System.out.println("myPredicate.test方法：" + myPredicate.test("MyPredicate"));
        System.out.println("myPredicate1.test方法：" + myPredicate1.test("MyPredicate"));
    }

}
