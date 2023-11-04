package as;

import org.javatuples.Pair;

import java.sql.*;



/*
封装对mysql的操作（我懒的写一堆乱七八糟的函数了，直接写一个以后直接调用省事儿（笑））
 */
public class mysqlmanipulate {
    //加载驱动
    public static void LoadDriver(){

        try{
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
        }catch(ClassNotFoundException e1){
            System.out.println("找不到MySQL驱动!");
            e1.printStackTrace();
        }


    }
    //增添对应表的条目
    public static void AddItem(String database,String table,String serial,String alg,long startdate,long enddate,String publickey,String privatekey){
        LoadDriver();
        Connection conn;
        String url="jdbc:mysql://localhost:3306/"+database;
        try {

            conn = DriverManager.getConnection(url,    "root","25832233l");
            //创建一个Statement对象
            Statement stmt = conn.createStatement(); //创建Statement对象
            System.out.print("成功连接到数据库！");
            String sql =  "INSERT INTO "+table+" (serial,alg,startdate,enddate,publickey,privatekey) VALUES ('"+serial+"', '"+alg+"', "+String.valueOf(startdate)+", "+String.valueOf(enddate)+", '"+publickey+"', '"+privatekey+"')";
            // 执行SQL语句
            int resultSet = stmt.executeUpdate(sql);
            System.out.println("新增的行号为"+resultSet);
            // 关闭连接和释放资源
            stmt.close();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    //删除条目
    public static void DeleteItem(String port,String databases,String table,String user,String password,String serial){
        LoadDriver();
        String url="jdbc:mysql://localhost:"+port+"/"+databases;    //JDBC的URL
        //调用DriverManager对象的getConnection()方法，获得一个Connection对象
        Connection conn;
        try {
            conn = DriverManager.getConnection(url,user,password);
            //创建一个Statement对象
            Statement stmt = conn.createStatement(); //创建Statement对象
            System.out.print("成功连接到数据库！");
            String sql =  "Delete from "+table+" where serial='"+serial+"'";
            // 执行SQL语句
            int resultSet = stmt.executeUpdate(sql);
            System.out.println("删除的行号为"+resultSet);
            // 关闭连接和释放资源
            stmt.close();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }






    }
    //插入新字段
    public static void AddField(String port,String databases,String table,String user,String password,String field){
        LoadDriver();
        String url="jdbc:mysql://localhost:"+port+"/"+databases;    //JDBC的URL
        //调用DriverManager对象的getConnection()方法，获得一个Connection对象
        Connection conn;
        try {
            conn = DriverManager.getConnection(url,user,password);
            //创建一个Statement对象
            Statement stmt = conn.createStatement(); //创建Statement对象
            System.out.print("成功连接到数据库！");
            String sql =  "alter table "+table+" add "+field+" int(4)";
            // 执行SQL语句
            int resultSet = stmt.executeUpdate(sql);
            System.out.println("新字段插入成功！！");
            // 关闭连接和释放资源
            stmt.close();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    //修改字段名
    public static void RenameField(String port,String databases,String table,String user,String password,String oldfield,String newfield){
        LoadDriver();
        String url="jdbc:mysql://localhost:"+port+"/"+databases;    //JDBC的URL
        //调用DriverManager对象的getConnection()方法，获得一个Connection对象
        Connection conn;
        try {
            conn = DriverManager.getConnection(url,user,password);
            //创建一个Statement对象
            Statement stmt = conn.createStatement(); //创建Statement对象
            System.out.print("成功连接到数据库！");
            String sql =  "alter table "+table+" rename column "+oldfield+" to "+newfield;
            // 执行SQL语句
            int resultSet = stmt.executeUpdate(sql);
            System.out.println("字段名修改成功！！");
            // 关闭连接和释放资源
            stmt.close();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    //获取表内容（按照序列号）
    public static Pair<String,String> QueryTable(String port,String databases,String table,String user,String password,String serial){
        LoadDriver();
        String url="jdbc:mysql://localhost:"+port+"/"+databases;    //JDBC的URL
        //调用DriverManager对象的getConnection()方法，获得一个Connection对象
        Connection conn;
        String pub=null;
        String pri=null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            //创建一个Statement对象
            Statement stmt = conn.createStatement(); //创建Statement对象
            System.out.print("成功连接到数据库！");
            String sql = "SELECT * FROM "+table+" where serial='"+serial+"'";
            // 执行SQL语句
            ResultSet resultSet = stmt.executeQuery(sql);
            // 处理结果集

            while (resultSet.next()) {
                pub = resultSet.getString("publickey");
                pri = resultSet.getString("privatekey");
                System.out.println("publickey: " + pub + ", privatekey: " + pri);

            }

            // 关闭连接和释放资源
            resultSet.close();
            stmt.close();
            conn.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
        Pair<String,String> pair=new Pair<>(pub,pri);
        return pair;
    }
    //创建表（个人用，省点事儿）
    public static void CreateTable(String port,String databases,String table,String user,String password){
        LoadDriver();
        String url="jdbc:mysql://localhost:"+port+"/"+databases;    //JDBC的URL
        //调用DriverManager对象的getConnection()方法，获得一个Connection对象
        Connection conn;
        try {

            conn = DriverManager.getConnection(url,user,password);
            //创建一个Statement对象
            Statement stmt = conn.createStatement(); //创建Statement对象
            System.out.print("成功连接到数据库！");
            String sql =  "create table "+table+" (id int, account VARCHAR(100), serial VARCHAR(100) , alg VARCHAR(100), startdate BIGINT, enddate BIGINT, publickey TEXT, privatekey TEXT) ";
            // 执行SQL语句
            int resultSet = stmt.executeUpdate(sql);
            System.out.println("新表创建成功！！");
            // 关闭连接和释放资源
            stmt.close();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    //测试
    public static void main(String[]args){
        //QueryTable("3306","ca","info","root","25832233l","gM6NVM5lXDk6O3Nt");
        //DeleteItem("3306","ca","info","root","25832233l","gM6NVM5lXDk6O3Nt");
        //AddField("3306","ca","info","root","25832233l","gender");
        //RenameField("3306","ca","info","root","25832233l","gender","newgender");

    }
}
