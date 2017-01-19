/**
 * Created by liu on 2017/1/19 019.
 */
public class Tower {
    public int location;
    public int[] disks;

    public Tower(int x, int high){
        this.location = x;
        this.disks = new int[high];
    }

    public void addDisk(int disk_number){
        this.add(disk_number, disks.length - 1);
    }

    private void add(int disk_number, int tower_high){
        if(this.disks[tower_high] > 0){
            this.disks[tower_high + 1] = disk_number;
            //System.out.println("" + disks[tower_high]);
        }else if(tower_high == 0){
            this.disks[tower_high] = disk_number;
        }
        else{
            this.add(disk_number, tower_high  - 1);
        }
    }

    public int putDisk(int disk_number){
        //System.out.println(""+disk_number);
        for(int i = this.disks.length - 1; i >= 0; i--){
            if(this.disks[i] == disk_number){
                return i;
            }
        }
        return 1;
    }

    public int getTop(){
        for(int i = this.disks.length - 1; i >= 0; i--){
            if(this.disks[i] > 0){
                return i;
            }
        }
        return 0;
    }

    public void removed(int disk_number){
        for(int i = disks.length - 1; i >= 0; i--){
            if(disks[i] == disk_number){
                disks[i] = 0;
                break;
            }
        }
    }

}
