public class Map {
    Constants.MapType[][] map;

    private int width;
    private int height;
    private int dWidth;
    private int dHeight;
    public Map(int width,int height){
        this.width = width;
        this.height = height;
        this.map = new Constants.MapType[width][height];
        this.dHeight = this.height/2;
        this.dWidth = this.width/2;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.map[i][j] = Constants.MapType.NONE;
            }
        }
    }

    public void printMap(){
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                Constants.MapType type = this.map[i][j];
                if (type == Constants.MapType.NONE){
                    //System.out.print(" ");
                }
                if (type == Constants.MapType.FOOD){
                    System.out.print("#");
                }
                if (type == Constants.MapType.HIVE){
                    System.out.print("$");
                }
            }
            System.out.println("");
        }


    }


    public void setMap(int x, int y, Constants.MapType type){
        int w = x + dWidth;
        int h = y + dHeight;

        if (w >= this.width || w < 0){
            return;
        }


        if (h >= this.height || h < 0){
            return;
        }

        this.map[w][h] = type;
    }

    public void setEntity(int x, int y, int radius, Constants.MapType type){
        int nx = x + this.dWidth;
        int ny = y + this.dHeight;
        for (int i = nx-radius; i < nx+radius; i++) {
            for (int j = ny-radius; j < ny+radius; j++) {
                //System.out.println("Setting Entity at pos: " + i + " "+j);
                this.map[i][j] = type;
            }
        }
    }

    public Constants.MapType getTile(int x,int y){
        int w = x + dWidth;
        int h = y + dHeight;

        if (w >= this.width || w < 0){
            return Constants.MapType.NONE;
        }


        if (h >= this.height || h < 0){
            return Constants.MapType.NONE;
        }

        return this.map[w][h];
    }


}
