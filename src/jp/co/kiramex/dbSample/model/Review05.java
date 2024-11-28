package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement spstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "Systemoutprintln121212!");

            String selectSql = "SELECT name, age FROM person where id = ?";
            spstmt = con.prepareStatement(selectSql);

            System.out.print("検索キーワードを入力してください > ");
            String input = keyIn();

            int id = Integer.parseInt(input);
            spstmt.setInt(1, id);

            rs = spstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println(name);
                System.out.println(age);
            } else {
                System.out.println("No data found for id: " + id);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("無効な入力です。数値のIDを入力してください。");
        } finally {
            try {
                if (rs != null) rs.close();
                if (spstmt != null) spstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("データベース切断時にエラーが発生しました。");
                e.printStackTrace();
            }
        }
    }

    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {
            System.err.println("Error reading input.");
        }
        return line;
    }
}
