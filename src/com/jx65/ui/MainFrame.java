package com.jx65.ui;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        FrameInit();//界面初始化
        getBar();//菜单初始化
        this.setVisible(true);//界面可见
    }

    private void getBar() {
        //菜单初始化
        JMenuBar menu = new JMenuBar();//菜单栏

        JMenu functionMenu = new JMenu("功能");//菜单1
        JMenuItem restartGame = new JMenuItem("重新开始");//子菜单1
        functionMenu.add(restartGame);
        JMenuItem reLogin = new JMenuItem("重新登录");//子菜单2
        functionMenu.add(reLogin);
        JMenuItem exitGame = new JMenuItem("退出游戏");//子菜单3
        functionMenu.add(exitGame);
        menu.add(functionMenu);

        JMenu aboutUsMenu = new JMenu("关于我们");//菜单2
        JMenuItem producerInfos = new JMenuItem("制作人");//子菜单2-1
        aboutUsMenu.add(producerInfos);
        menu.add(aboutUsMenu);
        this.setJMenuBar(menu);//应用菜单格式
    }

    private void FrameInit() {
        //界面初始化
        this.setSize(603, 680);
        this.setAlwaysOnTop(true);//界面位于最上层
        this.setLocationRelativeTo(null);//界面居中
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//关闭模式：关闭任何窗口时结束虚拟机运行
        this.setTitle("拼图游戏V0.1:主界面");
    }


}
