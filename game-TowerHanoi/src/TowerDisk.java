/**
 * Created by liu on 2017/1/18 018.
 */
public class TowerDisk {
    public int disk_number;
    public int disk_WIDTH;
    public int disk_X;
    public int disk_Y;
    public final int DISK_HEIGH = 40;

    public TowerDisk(int x, int y, int number){
        this.disk_number = number + 1;
        this.disk_WIDTH = this.disk_number * 28;
        this.disk_X = x - this.disk_WIDTH / 2;
        this.disk_Y = y - this.disk_number * this.DISK_HEIGH;

    }
}
