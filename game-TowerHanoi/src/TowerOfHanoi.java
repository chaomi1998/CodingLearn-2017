import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        tableArea.setPreferredSize(
                new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        f.add(tableArea);
        //窗口监听器
        f.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        for (int i = disks.length - 1; i >= 0;i--){
            disks[i] = new Disk(START_TOWER,TABLE_HEIGHT, i);
            one_tower.addDisk(disks[i].disk_number);
            for(int c : one_tower.disks){System.out.print("|"+c);}
            disks[i].high =one_tower.putDisk(disks[i].disk_number);
            //System.out.println("" + disks[i].high);
            disks[i].move();
        }

        f.pack();
        f.setVisible(true);
    }

    public static void main(String[] args){
        new TowerOfHanoi().init();
    }

    class MyCanvas extends Canvas{
        @Override
        public void paint(Graphics g){
            g.setColor(new Color(121,121,121));
            g.fillRect(0, 0, TABLE_WIDTH / 3, TABLE_HEIGHT);
            g.fillRect(2 * TABLE_WIDTH / 3, 0, TABLE_WIDTH / 3, TABLE_HEIGHT);
            g.setColor(new Color(137, 137, 137));
            g.fillRect(TABLE_WIDTH / 3, 0, TABLE_WIDTH / 3, TABLE_HEIGHT);
            g.setColor(new Color(74, 74, 74));
            g.fillRect(TABLE_WIDTH / 3 / 2 - TOWER_CORE_WIDTH / 2,  0, TOWER_CORE_WIDTH, TABLE_HEIGHT);
            g.fillRect(TABLE_WIDTH / 2 - TOWER_CORE_WIDTH / 2,  0, TOWER_CORE_WIDTH, TABLE_HEIGHT);
            g.fillRect(5 * TABLE_WIDTH / 3 / 2 - TOWER_CORE_WIDTH / 2,  0, TOWER_CORE_WIDTH, TABLE_HEIGHT);

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
            //- (MAX_DIFFICULT - DIFFICULT) * disk.DISK_HEIGH
            //g.fillRect(disks[0].disk_X, disks[0].disk_Y, disks[0].disk_WIDTH, disks[0].DISK_HEIGH);
        }
    }
}
