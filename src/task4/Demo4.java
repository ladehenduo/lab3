package task4;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Scanner;

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
            return ;
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
        String sql = "insert into task4 (文章名, 文章) values(?, ?)";
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
                return ;
            } catch (IOException e) {
                System.out.println("I/O异常：" + e.getMessage());
                return ;
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
        String sql = "insert into task4 (文章名, 文章, 图片名, 图片) values(?, ?, ?, ?)";
        PreparedStatement pre;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, textname);
            pre.setString(2, textpath);
            pre.setString(3, picname);
            pre.setBinaryStream(4, new FileInputStream(picpath));
            pre.execute();
        } catch (SQLException e) {
            System.out.println("SQL语法异常：" + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("文件异常：" + e.getMessage());
        }
    }
    public static ResultSet readAll(int idx) {  // idx = 编号
        String sql = "select * from task4 where 编号 = ?";
        PreparedStatement pre;
        ResultSet res;
        try {
            pre = conn.prepareStatement(sql);
            pre.setInt(1, idx);
        } catch (SQLException e) {
            System.out.println("sql语句执行异常");
            return null;
        }
        try {
            res = pre.executeQuery();
        } catch (SQLException e) {
            System.out.println("sql语句执行异常：" + e.getMessage());
            return  null;
        }
        return res;
    }
    // 存储到本地
    public static void savePicture(ResultSet res) throws SQLException {    // 根据结果，读出文章，图片，文章会被输出，图片则被存储起来，picname表示存储时的文件名
        InputStream in=null;
        String textname=null;
        String picname=null;
        String text=null;
        if(res.next()) {
            try {
                picname = res.getString("图片名");
                in = res.getBinaryStream("图片");
                textname = res.getString("文章名");
                text = res.getString("文章");
                System.out.println("文章名：" + textname);
                System.out.println("文章内容：" + text);
                if(in != null) {
                    FileOutputStream out = new FileOutputStream(picname);
                    BufferedInputStream bis = new BufferedInputStream(in);
                    out.write(bis.readAllBytes());
                }

            } catch (SQLException e) {
                System.out.println("sql语句错误:" + e.getMessage());
            } catch (FileNotFoundException e) {
                System.out.println("未找到文件：" + picname);
            } catch (IOException e) {
                System.out.println("缓冲读入出错：" + e.getMessage());
            }
        }
    }
     public static void saveText() {    //存储到数据库
        Scanner scanner = new Scanner(System.in);
        int op;
        String tp;
        System.out.println("1.根据路径存储文章");
        System.out.println("2.输入存储文章");
        System.out.println("请输入操作："); op = scanner.nextInt();
        if(op == 1) {
            System.out.println("请输入文章标题："); tp = scanner.next();
            System.out.println("请输入文章绝对路径："); String path = scanner.next();
            insertText(tp, Paths.get(path).toString(), null);
        }
        else if(op == 2) {
            System.out.println("请输入文章标题："); tp = scanner.next();
            System.out.println("请输入文章内容："); String text = scanner.next();
            insertText(tp, null, text);
        }
        else {
            System.out.println("无效操作，程序结束");
        }
    }
    public static void savePicture() {  //存储到数据库
        Scanner scanner = new Scanner(System.in);
        int op;
        String tp;
        System.out.println("请输入图片名称："); tp = scanner.next();
        System.out.println("请输入图片路径："); String path = scanner.next();
        insertPicture(tp, Paths.get(path).toString());
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int op;
        String tp;
        System.out.println("1.存储文章");
        System.out.println("2.存储图片");
        System.out.println("3.存储文章和图片");
        System.out.println("4.读取文章和图片");
        System.out.println("请输入操作："); op = scanner.nextInt();
        if(op == 1) {
            saveText();
        }
        else if(op == 2) {
            savePicture();
        }
        else if(op == 3){
            String tp1, tp2, tp3, tp4;
            System.out.println("请输入文章标题："); tp1 = scanner.next();
            System.out.println("请输入文章绝对路径："); tp2 = scanner.next();
            System.out.println("请输入图片名称："); tp3 = scanner.next();
            System.out.println("请输入图片路径："); tp4 = scanner.next();
            insertAll(tp1, tp2, tp3, tp4);
        }
        else if(op == 4) {
            System.out.println("请输入编号："); op = scanner.nextInt();
            ResultSet res = readAll(op);
            try {
                savePicture(res);
            } catch (SQLException e) {
                System.out.println("SQL异常：" + e.getMessage());
            }
        }
        else {
                System.out.println("无效的操作，程序结束！");
        }
        System.out.println("完成，结束");
    }

}//
