package com.jl65.ui;

import javax.swing.*;

public class Test {
    public static void main(String[] args) {

        //游戏主界面
        JFrame MainFrame = new JFrame();
        MainFrame.setSize(603,680);
        MainFrame.setVisible(true);
        MainFrame.setTitle("拼图游戏V0.1");

        //登录界面
        JFrame LoginFrame = new JFrame();
        LoginFrame.setSize(488,430);
        LoginFrame.setVisible(true);
        LoginFrame.setTitle("拼图游戏V0.1:登录");

        //注册界面
        JFrame SignFrame = new JFrame();
        SignFrame.setSize(488,500);
        SignFrame.setVisible(true);
        SignFrame.setTitle("拼图游戏V0.1:登录");

    }
}