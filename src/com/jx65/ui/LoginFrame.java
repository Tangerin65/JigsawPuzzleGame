package com.jx65.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class LoginFrame extends JFrame implements ActionListener {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JLabel status;

    public LoginFrame()  {
        //基本设置
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("拼图游戏V0.2:登录");

        //界面设计
        JPanel panel=new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        this.setContentPane(panel);

        //标题
        JLabel title=new JLabel("登录/注册",JLabel.CENTER);
        title.setFont(new Font("微软雅黑",Font.BOLD,22));
        panel.add(title,BorderLayout.NORTH);

        //输入框
        JPanel centerPanel = new JPanel(new GridBagLayout());
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        JPanel inputPanel=new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel username=new JLabel("用户名：", SwingConstants.RIGHT);
        usernameField=new JTextField(16);
        JLabel password=new JLabel("密 码：", SwingConstants.RIGHT);
        passwordField=new JPasswordField(16);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(username, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        inputPanel.add(password, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(passwordField, gbc);

        JButton loginRegister=new JButton("登录/注册");
        loginRegister.addActionListener(this);
        loginRegister.setAlignmentX(Component.CENTER_ALIGNMENT);

        status=new JLabel("请输入用户名和密码", SwingConstants.CENTER);
        status.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        status.setForeground(Color.RED);
        status.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardPanel.add(inputPanel);
        cardPanel.add(Box.createVerticalStrut(16));
        cardPanel.add(loginRegister);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(status);

        centerPanel.add(cardPanel);
        panel.add(centerPanel, BorderLayout.CENTER);

        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // 用户存储：模拟用户名及密码存储
    private static final Map<String, String> userDatabase = new HashMap<>();

    static {
        userDatabase.put("admin", "admin123");
        userDatabase.put("root", "root123");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText().trim(); // 获取用户名
        String password = new String(passwordField.getPassword()); // 获取密码

        // 检查用户名和密码是否为空
        if (username.isEmpty() || password.isEmpty()) {
            status.setText("用户名或密码不能为空！");
            status.setForeground(Color.RED);
            return;
        }

        // 用户逻辑处理
        if (userDatabase.containsKey(username)) {
            // 用户已存在，验证密码
            if (userDatabase.get(username).equals(password)) {
                status.setText("登录成功！");
                status.setForeground(Color.GREEN); // 成功提示
                openMainFrame();
            } else {
                status.setText("密码错误，请重试！");
                status.setForeground(Color.RED); // 错误提示
            }
        } else {
            // 用户不存在，注册新用户
            userDatabase.put(username, password); // 添加新用户
            status.setText("注册成功，您已登录！");
            status.setForeground(Color.GREEN); // 成功提示
            openMainFrame();
        }
    }

    private void openMainFrame() {
        new MainFrame();
        this.dispose();
    }
}
