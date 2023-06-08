package task1;

import java.lang.annotation.Inherited;

@MyAnnotation(value1 = "没有名字", value2 = 0)
public class Person {
    public String name;
    public String gender;
    public void eat(double t) {
        System.out.println(name + "本次吃饭吃了" + t + "小时");
    }
    @Deprecated
    public void deprecatedTest() {
        System.out.println("这个方法被弃用了");
    }
    public void showInfo(){
        System.out.println("姓名：" + name + "\n" + "性别：" + gender);
    }
}
//
