/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phien
 */
public class DB {

    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;

    public DB() {
        String dbURL = "jdbc:mysql://localhost/cachephantan";
        String username = "root";
        String password = "123456";
        try {
            conn = (Connection) DriverManager.getConnection(dbURL, username, password);

        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean check(String acc, String pass) {
        String sql = "select * from hoso where Taikhoan='" + acc + "' and Matkhau='" + pass + "'";
        boolean check_tai_khoan=true;

        try {
            // Tạo đối tượng thực thi câu lệnh Select
            st = (Statement) conn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.isBeforeFirst() == false){
               check_tai_khoan=false;
            }else{
               check_tai_khoan=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Thực thi 
        // Trong khi chưa hết dữ liệu
        return check_tai_khoan;
        
    }
//    public static void main(String[] args) {
//        DB database=new DB();
//        boolean a = database.check("tuyen","1");
//        System.out.println(a);
//        
//    }

}
