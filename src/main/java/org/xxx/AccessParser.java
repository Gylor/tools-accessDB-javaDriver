package org.xxx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class AccessParser {

    public static void main(String[] args) {

        String dbPath = "xxx.accdb";
        String password = "123";

        String url = "jdbc:ucanaccess://" + dbPath + ";jackcessOpener=org.xxx.JackcessOpener";

        System.out.println("正在连接数据库并解密...");

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            // 获取连接
            try (Connection conn = DriverManager.getConnection(url, "", password);
                 Statement stmt = conn.createStatement()) {

                System.out.println("解密并连接成功！正在读取表结构...");

                // 查询所有表并打印数据
                ResultSet rsTables = conn.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
                java.util.List<String> tableNames = new java.util.ArrayList<>();
                while (rsTables.next()) {
                    tableNames.add(rsTables.getString("TABLE_NAME"));
                }
                System.out.println("数据库中的表：" + tableNames);

                for (String tableName : tableNames) {
                    System.out.println("\n===== 表: " + tableName + " =====");
                    String sql = "SELECT * FROM [" + tableName + "]";
                    try (ResultSet rs = stmt.executeQuery(sql)) {
                        int columns = rs.getMetaData().getColumnCount();
                        while (rs.next()) {
                            for (int i = 1; i <= columns; i++) {
                                System.out.print(rs.getString(i));
                                if (i < columns) System.out.print("\t");
                            }
                            System.out.println();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("解析失败，可能原因为密码错误或文件损坏：");
            e.printStackTrace();
        }
    }


}
