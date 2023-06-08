package task4;

import java.io.*;
import java.sql.*;

public class Demo4 {
    public static String URL = "jdbc:mysql://localhost:3306/exp3";
    public static String USER = "root";
    public static String PASSWORD = "1234";

    public static Connection conn = null;

    static {    //初始化代码块
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String bytesToString (byte[] bytes, int len) {
        String res = "";
        for(int i = 0; i < len; i++) res += bytes[i];
        return res;
    }
    public static String charToString (char[] chs, int len) {
        String res = "";
        for(int i = 0; i < len; i++) res += chs[i];
        return res;
    }
    public static void useFunction(int a, int b) throws SQLException {  //调用函数并输出结果
        String sql = "select add_sum(?, ?) as res";         // 数据库函数为，将第一个和第二个参数相加返回结果
        PreparedStatement pre = conn.prepareStatement(sql);
        if(pre != null) {
            pre.setInt(1, a);
            pre.setInt(2, b);
            ResultSet resultSet = pre.executeQuery();
            while(resultSet.next()) {
                System.out.println("调用数据库的结果为：" + resultSet.getInt("res"));
            }
        }
    }
    public static void insertPicture(String filename, String path){
        String text = null;
        String sql = "insert into task4 (图片名, 图片) values(?, ?)";
        InputStream is = null;
        try {
            is = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            System.out.println("未找到文件");
        }
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, filename);
            pre.setBinaryStream(2, is, (int)new File(path).length());
            pre.execute();
        } catch (SQLException e) {
            System.out.println("sql执行异常" + e.getMessage());
        }
    }
    public static void insertText(String filename, String path, String txt) {
        String text = txt;
        String sql = "insert into task4 (文章名, 文章)   values(?, ?)";
        InputStreamReader isr = null;
        if(txt == null){
            try {
                isr = new InputStreamReader(new FileInputStream(path));
                char[] chs = new char[1000000];
                int len = isr.read(chs, 0, chs.length);
                text = charToString(chs, len);
//                System.out.println(text);
            } catch (FileNotFoundException e) {
                System.out.println("未找到文件：" + path);
            } catch (IOException e) {
                System.out.println("I/O异常：" + e.getMessage());
            }
        }
        PreparedStatement pre = null;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, filename);
            pre.setString(2, text);
            pre.execute();
        } catch (SQLException e) {
            System.out.println("sql执行异常" + e.getMessage());
        }
    }
    public static void insertAll(String textname, String textpath, String picname, String picpath){
        insertText(textname, textpath, null);
        insertPicture(picname, picpath);
    }
    public static void main(String[] args) {
//        insertText("测试", null, "你好！");
//        insertText("测试", "E:\\JavaEE\\lab3\\src\\task4\\text", null);
        insertPicture("a.jpg", "E:\\JavaEE\\lab3\\src\\task4\\a.jpg");
    }
}//
