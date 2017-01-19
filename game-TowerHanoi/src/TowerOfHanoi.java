import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by liu on 2017/1/18 018.
 */
public class TowerOfHanoi{
    //桌面宽度
    private final int TABLE_WIDTH = 600;
    private final int TABLE_HEIGHT = 320;
    //塔心宽度
    private final int TOWER_CORE_WIDTH = 10;
    private final int START_TOWER = TABLE_WIDTH / 2;
    private MyCanvas tableArea = new MyCanvas();
    private JFrame f = new JFrame("汉诺塔");
    //碟子初始化
    private final int MAX_DIFFICULT = 8;
    private final int DIFFICULT = 7;
    private Disk[] disks = new Disk[DIFFICULT];
    //塔初始化
    private Tower one_tower = new Tower(TABLE_WIDTH / 2, MAX_DIFFICULT);
    private Tower two_tower = new Tower(TABLE_WIDTH / 3 / 2 , MAX_DIFFICULT);
    private Tower three_tower = new Tower(5 * TABLE_WIDTH / 3 / 2, MAX_DIFFICULT);

    public void init(){
        for (int i = disks.length - 1; i >= 0;i--){
            disks[i] = new Disk(START_TOWER,TABLE_HEIGHT, i);
            one_tower.addDisk(disks[i].disk_number);
            disks[i].high =one_tower.putDisk(disks[i].disk_number);
            disks[i].move();
        }

        tableArea.setPreferredSize(
                new Dimension(TABLE_WIDTH, TABLE_HEIGHT));

        //鼠标监听部分 并不好用
        tableArea.addMouseListener(new MouseAdapter() {
            private boolean take = true;
            private boolean put = false;
            private int move_disk_number;
            private Tower tmp_tower;
            private Toolkit waring = Toolkit.getDefaultToolkit();
            @Override
            public void mouseClicked(MouseEvent e) {
                //取碟子 取完之后关闭取打开放
                if(take){
                    if(e.getY() > TABLE_HEIGHT / 2 && e.getY()<= TABLE_HEIGHT) {
                        if (e.getX() > 0 && e.getX() <= TABLE_WIDTH / 3) {
                            move_disk_number = two_tower.getTop();
                            if (move_disk_number != 0) {
                                tmp_tower = two_tower;
                                take = false;
                                put = true;
                            }
                        }
                        if (e.getX() > TABLE_WIDTH / 3 && e.getX() <= 2 * TABLE_WIDTH / 3) {
                            move_disk_number = one_tower.getTop();
                            if (move_disk_number != 0) {
                                tmp_tower = one_tower;
                                take = false;
                                put = true;
                            }
                        }
                        if (e.getX() > 2 * TABLE_WIDTH / 3 && e.getX() <= TABLE_WIDTH) {
                            move_disk_number = three_tower.getTop();
                            if (move_disk_number != 0) {
                                tmp_tower = three_tower;
                                take = false;
                                put = true;
                            }
                        }
                    }
                }
                //放碟子 比已有的大就放回去 比已有的小就放下
                if(put){
                    if(e.getY() > 0 && e.getY()<= TABLE_HEIGHT / 2) {
                        if (e.getX() > 0 && e.getX() <= TABLE_WIDTH / 3) {
                            //System.out.println(""+move_disk_number);
                            if(two_tower.getTop() != 0 && move_disk_number > two_tower.getTop()){
                                waring.beep();
                                take = true;
                                put = false;
                            }
                            else {
                                tmp_tower.removed(move_disk_number);
                                two_tower.addDisk(move_disk_number);
                                disks[move_disk_number - 1].high = two_tower.putDisk(move_disk_number);
                                disks[move_disk_number - 1].xMove(two_tower.location);
                                disks[move_disk_number - 1].move();
                                take = true;
                                put = false;
                                tableArea.repaint();
                            }
                        }
                        if (e.getX() > TABLE_WIDTH / 3 && e.getX() <= 2 * TABLE_WIDTH / 3) {
                            if(one_tower.getTop() != 0 && move_disk_number > one_tower.getTop()){
                                waring.beep();
                                take = true;
                                put = false;
                            }
                            else {
                                tmp_tower.removed(move_disk_number);
                                one_tower.addDisk(move_disk_number);
                                disks[move_disk_number - 1].high = one_tower.putDisk(move_disk_number);
                                disks[move_disk_number - 1].xMove(one_tower.location);
                                disks[move_disk_number - 1].move();
                                take = true;
                                put = false;
                                tableArea.repaint();
                            }
                        }
                        if (e.getX() > 2 * TABLE_WIDTH / 3 && e.getX() <= TABLE_WIDTH) {
                            if(three_tower.getTop() != 0 && move_disk_number > three_tower.getTop()){
                                waring.beep();
                                take = true;
                                put = false;
                            }
                            else {
                                tmp_tower.removed(move_disk_number);
                                three_tower.addDisk(move_disk_number);
                                disks[move_disk_number - 1].high = three_tower.putDisk(move_disk_number);
                                disks[move_disk_number - 1].xMove(three_tower.location);
                                disks[move_disk_number - 1].move();
                                take = true;
                                put = false;
                                tableArea.repaint();
                            }
                        }
                    }
                }
            }
        });


        //窗口监听器
        f.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        f.add(tableArea);
        f.pack();
        f.setVisible(true);
    }

    public static void main(String[] args){
        new TowerOfHanoi().init();
    }

    class MyCanvas extends JPanel{
        @Override
        public void paint(Graphics g){
            super.paint(g);
            //背景绘制
            g.setColor(new Color(121,121,121));
            g.fillRect(0, 0, TABLE_WIDTH / 3, TABLE_HEIGHT);
            g.fillRect(2 * TABLE_WIDTH / 3, 0, TABLE_WIDTH / 3, TABLE_HEIGHT);
            g.setColor(new Color(137, 137, 137));
            g.fillRect(TABLE_WIDTH / 3, 0, TABLE_WIDTH / 3, TABLE_HEIGHT);
            g.setColor(new Color(74, 74, 74));
            g.fillRect(TABLE_WIDTH / 3 / 2 - TOWER_CORE_WIDTH / 2,  0, TOWER_CORE_WIDTH, TABLE_HEIGHT);
            g.fillRect(TABLE_WIDTH / 2 - TOWER_CORE_WIDTH / 2,  0, TOWER_CORE_WIDTH, TABLE_HEIGHT);
            g.fillRect(5 * TABLE_WIDTH / 3 / 2 - TOWER_CORE_WIDTH / 2,  0, TOWER_CORE_WIDTH, TABLE_HEIGHT);
            //碟子绘制
            for(Disk disk: disks){
                g.setColor(Color.WHITE);
                g.fillRect(disk.disk_X
                        , disk.disk_Y
                        , disk.disk_WIDTH
                        , disk.DISK_HEIGH);
                g.setColor(Color.BLACK);
                g.drawRect(disk.disk_X
                        , disk.disk_Y
                        , disk.disk_WIDTH
                        , disk.DISK_HEIGH);
                g.drawString(""+disk.disk_number ,disk.disk_X+10, disk.disk_Y+20);
            }
            //提示信息
            g.setColor(Color.WHITE);
            g.drawString("操作指南：窗口下半单击拿取，上半单击放置。新增判断模块和提示",0,TABLE_HEIGHT - 10);
        }
    }
}
