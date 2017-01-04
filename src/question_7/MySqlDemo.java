package question_7;

/**
 * Created by Jimmy on 2017/1/4.
 */

import java.sql.*;

public class MySqlDemo {
    public static void main(String[] args){
        try{

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("注册驱动成功!");
        }catch(ClassNotFoundException e1){
            System.out.println("注册驱动失败!");
            e1.printStackTrace();
        }

        String url="jdbc:mysql://192.168.1.100:3306/test";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, "root", "123456");

            Statement stmt = conn.createStatement();
            System.out.print("创建Statement成功！");
            stmt.execute("UPDATE t_stu SET stu_No='10132510259' WHERE stu_No='10132510239' AND stu_Name='朱政'");
            /*ResultSet rs = stmt.executeQuery("select id, id1 from t_test order by id1 desc limit 0, 20");
            while(rs.next())
            {
                System.out.println(rs.getInt(1) + "," + rs.getInt(2));
            }
            rs.close();*/
            stmt.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(null != conn)
                {
                    conn.close();
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}