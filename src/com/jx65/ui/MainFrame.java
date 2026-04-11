package com.jx65.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Collections;


//TODO：游戏存在无解的可能性，在初始的随机放图阶段需要排除这种可能
public class MainFrame extends JFrame implements KeyListener, ActionListener {
    //数据结构：二维数组data，位置抽象
    private final Integer[][] data = new Integer[4][4];
    private int blankX;
    private int blankY;
    public int step_count=0;//计步器

    //子菜单JMenuItem定义，放在成员变量位置以便事件绑定
    JMenuItem restartGame = new JMenuItem("重新开始");//子菜单1-1
    JMenuItem reLogin = new JMenuItem("重新登录");//子菜单1-2
    JMenuItem exitGame = new JMenuItem("退出游戏");//子菜单1-3
    JMenu imgSelect= new JMenu("图片选择");//子菜单1-4
    JMenuItem img1_Cities=new JMenuItem("City");//子菜单1-4-1
    JMenuItem img2_EMUs=new JMenuItem("Train");//子菜单1-4-2
    JMenuItem img3_Hypercars=new JMenuItem("Hypercar");//子菜单1-4-3
    JMenuItem producerInfos = new JMenuItem("制作人");//子菜单2-1

    String img_type="EMUs";//图片类型选择


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
        this.requestFocusInWindow();


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
        this.getContentPane().removeAll();//图片格子清空

        if(isWin()) {
            showWin();

        }

        //步数显示模块
        JLabel steps = new JLabel("步数："+step_count);
        steps.setBounds(100, 10, 100, 30);
        this.getContentPane().add(steps);


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Integer nums = data[i][j];
                //图片初始化
                JLabel img_label;
                if (nums == null) {
                    img_label = new JLabel();//空白格子：跳过图片填充
                } else {
                    img_label = new JLabel(new ImageIcon("Image/"+img_type+"/" + nums + ".png"));
                }
                img_label.setBounds(150*j+90, 150*i+100, 150, 150);//坐标尺寸设置
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
        this.getContentPane().revalidate();//布局更新，尺寸重新计算
        this.getContentPane().repaint();//界面重绘
    }

    private void getBar() {
        //菜单初始化,
        //具体菜单对象定义由于绑定事件需要，放到了主类成员变量的位置，也就是这个代码文件的21-24行
        JMenuBar menu = new JMenuBar();//菜单栏
        JMenu functionMenu = new JMenu("功能");//菜单1

        functionMenu.add(restartGame);
        functionMenu.add(reLogin);
        functionMenu.add(exitGame);
        functionMenu.add(imgSelect);
        imgSelect.add(img1_Cities);
        imgSelect.add(img2_EMUs);
        imgSelect.add(img3_Hypercars);
        menu.add(functionMenu);

        JMenu aboutUsMenu = new JMenu("关于我们");//菜单2
        aboutUsMenu.add(producerInfos);
        menu.add(aboutUsMenu);
        this.setJMenuBar(menu);//应用菜单格式

        //菜单绑定事件
        restartGame.addActionListener(this);
        reLogin.addActionListener(this);
        exitGame.addActionListener(this);
        producerInfos.addActionListener(this);
        img1_Cities.addActionListener(this);
        img2_EMUs.addActionListener(this);
        img3_Hypercars.addActionListener(this);

    }

    private void FrameInit() {
        //界面初始化
        this.setSize(820, 840);
        this.setAlwaysOnTop(true);//界面位于最上层
        this.setLocationRelativeTo(null);//界面居中
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//关闭模式：关闭任何窗口时结束虚拟机运行
        this.setLayout(null);//取消默认布局
        this.addKeyListener(this);//添加键盘监听事件
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
        step_count++;
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
        step_count++;
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
        step_count++;
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
        step_count++;
        return true;
    }

    //胜利判断
    private boolean isWin() {
        int expected = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 3 && j == 3) {
                    return data[i][j] == null;
                }
                if (data[i][j] == null || data[i][j] != expected) {
                    return false;
                }
                expected++;
            }
        }
        return true;
    }

    //胜利弹窗
    private void showWin() {
        JOptionPane.showMessageDialog(this, "恭喜通关！", "胜利", JOptionPane.INFORMATION_MESSAGE);
        // TODO: 弹窗个性化，修改字体和颜色，传入JLabel而非字符串
        // TODO：胜利音效
    }

    //图片移动：实现键盘监听事件接口
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();
        //快捷键A：按下不松查看完整原图/快捷键W：一键通关
        if (code == 65||code==87) {
            this.getContentPane().removeAll();
            JLabel all_image=new JLabel(new ImageIcon("Image/"+img_type+"/full.png"));//原图呈现
            all_image.setBounds(90, 100, 600, 600);
            this.getContentPane().add(all_image);
            //背景图片保持
            JLabel background=new JLabel(new ImageIcon("Image/background1.png"));
            background.setBounds(-110, -170, 1000, 1100);
            this.getContentPane().add(background);
            this.getContentPane().revalidate();
            this.getContentPane().repaint();
            this.requestFocusInWindow();
        }

        //快捷键W的剩余实现
        if(code==87){
            //TODO：降低窗口透明度
            showWin();
        }



    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(isWin()){
            return;//如果已经胜利，按键释放不执行任何操作
        }
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
            case 65://快捷键A松开：原图->拼图界面
                System.out.println("快捷键A松开");
                initImage();
                break;

            default:
                System.out.println("其他按键: " + code);
                break;
        }
    }

    //菜单选择实现
    @Override
    public void actionPerformed(ActionEvent e) {
        //获取被点击的菜单Item
        Object source = e.getSource();
        if (source == restartGame) {//重新开始按钮
            step_count=0;
            initData();
            initImage();
        }
        else if(source==reLogin){//重新登录按钮
            this.setVisible(false);
            new LoginFrame();
        }
        else if (source == exitGame) {//退出游戏按钮
            System.exit(0);
        }
        else if (source == producerInfos) {//制作人信息按钮
            JOptionPane.showMessageDialog(this, "制作人：Tangerin", "关于我们", JOptionPane.INFORMATION_MESSAGE);
        }
        else if (source == img1_Cities) {//图片选择-城市
            img_type="Cities";
            initData();
            initImage();
        }
        else if (source == img2_EMUs) {//图片选择-火车
            img_type="EMUs";
            initData();
            initImage();
        }
        else if (source == img3_Hypercars) {//图片选择-汽车
            img_type="Hypercars";
            initData();
            initImage();
        }
        else{
            System.out.println(source+"被点击");
        }

    }
}
