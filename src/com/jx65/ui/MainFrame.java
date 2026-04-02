package com.jx65.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Collections;

public class MainFrame extends JFrame implements KeyListener {
    //数据结构：二维数组data，位置抽象
    private final Integer[][] data = new Integer[4][4];
    private int blankX;
    private int blankY;

    public MainFrame() {
        //界面初始化
        FrameInit();

        //顶部菜单初始化
        getBar();

        //初始化棋盘数据（只执行一次）
        initData();

        //图片初始化
        initImage();

        //界面可见
        this.setVisible(true);


    }

    private void initData() {
        Integer[] array = new Integer[16];
        for (int i = 1; i <= 15; i++) {
            array[i - 1] = i;
        }
        Collections.shuffle(Arrays.asList(array));//打乱图片顺序

        int idx = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                data[i][j] = array[idx++];
                if (data[i][j] == null) {
                    blankX = j;
                    blankY = i;
                }
            }
        }
    }

    private void initImage() {
        this.getContentPane().removeAll();//图片格子重绘

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Integer nums = data[i][j];
                //图片初始化
                JLabel img_label;
                if (nums == null) {
                    img_label = new JLabel();//空白格子：跳过图片填充
                } else {
                    img_label = new JLabel(new ImageIcon("Image/EMUs/" + nums + ".png"));
                }                img_label.setBounds(150*j+90, 150*i+100, 150, 150);//坐标尺寸设置
                img_label.setBorder(new BevelBorder(BevelBorder.LOWERED));//边框设置:BevelBorder-凹陷边框
                this.getContentPane().add(img_label);//图片应用至区域
            }
        }

        //放置背景图片
        JLabel background=new JLabel(new ImageIcon("Image/background1.png"));
        background.setBounds(-110, -170, 1000, 1100);
        this.getContentPane().add(background);
        // 临时检查：把背景放到最顶层（会覆盖拼图块）
        //this.getContentPane().setComponentZOrder(background, 0);
        // 需要恢复到底层时，改回：
        this.getContentPane().setComponentZOrder(background, this.getContentPane().getComponentCount() - 1);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
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
        this.setSize(820, 840);
        this.setAlwaysOnTop(true);//界面位于最上层
        this.setLocationRelativeTo(null);//界面居中
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//关闭模式：关闭任何窗口时结束虚拟机运行
        this.setLayout(null);//取消默认布局
        this.addKeyListener(this);//添加键盘监听事件
        this.setVisible(true);
        this.setTitle("拼图游戏V0.2:主界面");
    }
    //图片移动逻辑：移动空白格子
    //下移规则实现
    private boolean moveDownByRule() {
        if (blankY >= 3) {
            return false;
        }
        data[blankY][blankX] = data[blankY + 1][blankX];
        data[blankY + 1][blankX] = null;
        blankY++;
        return true;
    }
    //右移规则实现
    private boolean moveRightByRule() {
        if (blankX >= 3) {
            return false;
        }
        data[blankY][blankX] = data[blankY][blankX + 1];
        data[blankY][blankX + 1] = null;
        blankX++;
        return true;
    }
    //左移规则实现
    private boolean moveLeftByRule() {
        if (blankX <= 0) {
            return false;
        }
        data[blankY][blankX] = data[blankY][blankX - 1];
        data[blankY][blankX - 1] = null;
        blankX--;
        return true;
    }
    //上移规则实现
    private boolean moveUpByRule() {
        if (blankY <= 0) {
            return false;
        }
        data[blankY][blankX] = data[blankY - 1][blankX];
        data[blankY - 1][blankX] = null;
        blankY--;
        return true;
    }

    //图片移动：实现键盘监听事件接口
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 判断键盘方向, 左上右下对应 37-40
        int code = e.getKeyCode();
        switch (code) {
            case 37: // 左移
                System.out.println("左");
                if (moveLeftByRule()) {
                    initImage();
                }
                break;
            case 38: // 上移
                System.out.println("上");
                if (moveUpByRule()) {
                    initImage();
                }
                break;
            case 39: // 右移
                System.out.println("右");
                if (moveRightByRule()) {
                    initImage();
                }
                break;
            case 40: // 下移
                System.out.println("下");
                if (moveDownByRule()) {
                    initImage();
                }
                break;
            default:
                System.out.println("其他按键: " + code);
                break;
        }
    }
}
