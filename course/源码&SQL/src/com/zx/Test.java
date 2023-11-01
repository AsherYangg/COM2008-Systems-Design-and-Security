package com.zx;

import com.zx.util.MysqlUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author CaesarChang张旭
 * @Date 2020/12/29  9:56 上午
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        JFrame jf = new JFrame("登陆");
        jf.setLayout(new FlowLayout(FlowLayout.LEFT));
        jf.setBounds(460,300,300,200);

        JLabel label1 = new JLabel("账号:");
        JTextField usernameText = new JTextField("", 20);
        JLabel label2 = new JLabel("密码:");
        JPasswordField pwdText = new JPasswordField("",20);
        JTextField out = new JTextField("登陆状态",20);
        JButton button = new JButton("登陆");

        jf.add(label1);
        jf.add(usernameText);
        jf.add(label2);
        jf.add(pwdText);
        jf.add(out);
        jf.add(button);

        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username=usernameText.getText();
                String pwd=pwdText.getText();
                try {
                    Connection connection = MysqlUtil.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery("select * from user ");
                    int flag=0;
                    while (rs.next()) {

                        if(rs.getString(2).equals(username)&&rs.getString(3).equals(pwd)){
                            flag=1;
                            out.setText("登陆成功");
                        }
                    }
                    if (flag == 0) {
                        out.setText("登录失败");
                    }




                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                finally {
                    try {
                        MysqlUtil.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

            }
        });
        jf.setResizable(false);
        button.setSize(40,20);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
