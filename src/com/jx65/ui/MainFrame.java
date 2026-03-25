package com.jx65.ui;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainFrame extends JFrame {
    public MainFrame() {
        //界面初始化
        FrameInit();

        //菜单初始化
        getBar();

        //图片初始化
        initImage();

        //界面可见
        this.setVisible(true);


    }

    private void initImage() {
        int index=1;//图片名称索引

        //打乱图片顺序
        Integer[]array=new Integer[16];
        for (int i = 1; i <= 15; i++) {
            array[i-1]=i;
        }
        Collections.shuffle(Arrays.asList(array));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //图片初始化
                JLabel img_label=new JLabel(new ImageIcon("Image/EMUs/"+array[index-1]+".png"));//管理容器直接添加图片对象
                img_label.setBounds(150*j, 150*i, 150, 150);//坐标尺寸设置
                this.getContentPane().add(img_label);
                index++;
            }
        }





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
        this.setSize(603, 660);
        this.setAlwaysOnTop(true);//界面位于最上层
        this.setLocationRelativeTo(null);//界面居中
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//关闭模式：关闭任何窗口时结束虚拟机运行
        this.setLayout(null);//取消默认布局
        this.setVisible(true);
        this.setTitle("拼图游戏V0.1:主界面");
    }


}
