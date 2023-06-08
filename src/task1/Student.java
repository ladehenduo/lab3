package task1;

public class Student extends Person{
    public long number;
    public String major;
    public double learnedTime = 0;    //已学习时长，单位小时
    @Override
    public void showInfo() {
        System.out.println("姓名：" + name + "\n学号：" + number + "\n专业：" + major);
    }

//    @Override 取消注释，展示Override的作用
    public void learn(double d) {
        if(d < 0) {
            System.out.println("单次学习时长不能小于0");
        }
        else {
            learnedTime += d;
            System.out.println("本次学习时长为：" + d + "h\n" + "累计学习时长为：" + learnedTime);
        }
    }
}
