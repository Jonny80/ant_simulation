import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.Random;

public class ModelFactory {
    private static final Random randomGenerator = new Random();

    private ModelTexture blackTexture;
    private ModelTexture greenTexture;
    private Loader loader;
    public ModelFactory(Loader loader){
        this.loader = loader;
        this.blackTexture = new ModelTexture(this.loader.loadTexture("/black.png"));
        this.greenTexture = new ModelTexture(this.loader.loadTexture("/green.png"));
    }

    private float randomPosition(int range){
        Random random= new Random();
        int pos  = random.nextInt(range);
        if (pos > (float) (range/2)){
            return ((pos - ((float) range /2))*-1);
        }
        return pos;
    }

    private float randomPositionWithin(float position,int radius){
        float dP = this.randomPosition(radius);
        return position + dP;
    }

    public ArrayList<Ant> createAnts(float rotX,float rotY, float rotZ, float scale,int number){
        Random random= new Random();

        ModelTexture texture = new ModelTexture(loader.loadTexture("/black.png"));
        Model model = Loader.loadObjModel("ant.obj",loader);
        TexturedModel texturedModel = new TexturedModel(model,texture);

        ArrayList<Ant> ants = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Ant ant = new Ant(texturedModel,new Vector3f((this.randomPosition(3000)),(this.randomPosition(1500)), 0),
                    new Vector3f(rotX,rotY, rotZ ),scale,1);
           ants.add(ant);
        }
        return ants;
    }
    public ArrayList<Ant> createAnts(float posx,float posy,float rotX,float rotY, float rotZ, float scale,int number){
        Model model = Loader.loadObjModel("ant.obj",loader);
        TexturedModel texturedModel = new TexturedModel(model,this.blackTexture);

        ArrayList<Ant> ants = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Ant ant = new Ant(texturedModel,new Vector3f(posx,posy, 0),
                    new Vector3f(rotX,rotY,randomGenerator.nextInt(360)),scale,2);
            ants.add(ant);
        }
        return ants;
    }

    public void changeTextureOfAnt(Ant ant){
        TexturedModel texturedModel = new TexturedModel(ant.getModel().getRawModel(), this.greenTexture);
        ant.setModel(texturedModel);
        System.out.println("Texture changed");
    }

    public Entity createHive(){
        ModelTexture hiveTexture = new ModelTexture(loader.loadTexture("/ground.png"));
        Model hiveModel = loader.loadToVAO(vertices,textureCoords,indices);
        TexturedModel texturedHive = new TexturedModel(hiveModel,hiveTexture);
        return new Entity(texturedHive,new Vector3f((float) this.randomPosition(1600), (float) this.randomPosition(1000), 0),
                0,0,0,200);
    }
    public Entity createHive(Vector3f position,float rotX,
                              float rotY, float rotZ, float scale){
        ModelTexture hiveTexture = new ModelTexture(this.loader.loadTexture("/ground.png"));
        Model hiveModel = Loader.loadObjModel("hive.obj",loader);
        TexturedModel texturedHive = new TexturedModel(hiveModel,hiveTexture);
        return new Entity(texturedHive,position,
                rotX,rotY,rotZ,scale);
    }

    public ArrayList<Entity> createFoodSource(Vector3f position,int number,int scale,int radius){
        Model foodModel = loader.loadToVAO(vertices,textureCoords,indices);
        TexturedModel texturedFood = new TexturedModel(foodModel,this.greenTexture);

        ArrayList<Entity> foodSource = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            Vector3f newPosition = new Vector3f();
            newPosition.x = this.randomPositionWithin(position.x,radius);
            newPosition.y = this.randomPositionWithin(position.y,radius);
            newPosition.z = 0;
            foodSource.add(new Entity(texturedFood,newPosition,0,0,0,scale));
        }
        return foodSource;
    }
    public Entity createFood(Vector3f position,int scale){
        Model foodModel = loader.loadToVAO(vertices,textureCoords,indices);
        TexturedModel texturedFood = new TexturedModel(foodModel,this.greenTexture);
       return new Entity(texturedFood,position,0,0,0,scale);

    }
    float[] vertices = {
            -0.5f,0.5f,-0.5f,
            -0.5f,-0.5f,-0.5f,
            0.5f,-0.5f,-0.5f,
            0.5f,0.5f,-0.5f,

            -0.5f,0.5f,0.5f,
            -0.5f,-0.5f,0.5f,
            0.5f,-0.5f,0.5f,
            0.5f,0.5f,0.5f,

            0.5f,0.5f,-0.5f,
            0.5f,-0.5f,-0.5f,
            0.5f,-0.5f,0.5f,
            0.5f,0.5f,0.5f,

            -0.5f,0.5f,-0.5f,
            -0.5f,-0.5f,-0.5f,
            -0.5f,-0.5f,0.5f,
            -0.5f,0.5f,0.5f,

            -0.5f,0.5f,0.5f,
            -0.5f,0.5f,-0.5f,
            0.5f,0.5f,-0.5f,
            0.5f,0.5f,0.5f,

            -0.5f,-0.5f,0.5f,
            -0.5f,-0.5f,-0.5f,
            0.5f,-0.5f,-0.5f,
            0.5f,-0.5f,0.5f

    };

    float[] textureCoords = {

            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0


    };
    int[] indices = {0,1,3, 3,1,2, 4,5,7, 7,5,6, 8,9,11,
            11,9,10,
            12,13,15,
            15,13,14,
            16,17,19,
            19,17,18,
            20,21,23,
            23,21,22

    };
}
