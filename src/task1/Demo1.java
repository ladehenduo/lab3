package task1;

public class Demo1 {
    @SuppressWarnings(value ={"deprecation"})   //可以将此行注释掉，观察 Problems 的输出，对比结果
    public static void TestSuperWarnings1() {
        new Person().deprecatedTest();
    }
    @SuppressWarnings("unused")     // 注释掉此行看 Problems 的输出（不需要运行即可看到）
    public static void TestSuperWarnings2() {
        int x;
    }
    @SuppressWarnings("unchecked") // 注释掉此行看 Problems 的输出（不需要运行即可看到）
    public static void checkAnnotation(Class c) {
        MyAnnotation myAnnotation = (MyAnnotation) c.getAnnotation(MyAnnotation.class);    // 返回值是 Annotation 注解对象，如果没有返回null
        if(myAnnotation != null) {
            System.out.println(c.getName() + "拥有MyAnnotation注解\n" + "值1：" + myAnnotation.value1() + "\n值2：" + myAnnotation.value2());
        }
        else{
            System.out.println("未找到" + c.getName() + "类关于MyAnnotation注解的信息");
        }
    }
    //
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        Class person = Person.class;
        Class student = Student.class;
        checkAnnotation(person);
        checkAnnotation(student);
        System.out.println();
        TestSuperWarnings1();
        TestSuperWarnings2();
    }
}
