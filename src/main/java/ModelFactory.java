import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Random;

public class ModelFactory {

    private Loader loader;
    public ModelFactory(Loader loader){
        this.loader = loader;
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

    public ArrayList<Entity> createAnts(float rotX,float rotY, float rotZ, float scale,int number){

        ModelTexture texture = new ModelTexture(loader.loadTexture("/black.png"));
        Model model = Loader.loadObjModel("ant.obj",loader);
        TexturedModel texturedModel = new TexturedModel(model,texture);

        ArrayList<Entity> ants = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Entity ant = new Entity(texturedModel,new Vector3f((this.randomPosition(1600)),(this.randomPosition(1000)), 0),rotX,rotY,rotZ,scale);
           ants.add(ant);
        }
        return ants;
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
        Model hiveModel = this.loader.loadToVAO(vertices,textureCoords,indices);
        TexturedModel texturedHive = new TexturedModel(hiveModel,hiveTexture);
        return new Entity(texturedHive,position,
                rotX,rotY,rotZ,scale);
    }

    public ArrayList<Entity> createFoodSource(Vector3f position,int number,int scale,int radius){
        ModelTexture hiveTexture = new ModelTexture(loader.loadTexture("/green.png"));
        Model hiveModel = loader.loadToVAO(vertices,textureCoords,indices);
        TexturedModel texturedFood = new TexturedModel(hiveModel,hiveTexture);

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
    public Entity createFood(){
        ModelTexture hiveTexture = new ModelTexture(loader.loadTexture("/green.png"));
        Model hiveModel = loader.loadToVAO(vertices,textureCoords,indices);
        TexturedModel texturedHive = new TexturedModel(hiveModel,hiveTexture);
        return new Entity(texturedHive,new Vector3f((float) (Math.random() * 10000 - 5000), (float) (Math.random() * 10000 - 5000), 0),
                0,0,0,200);
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
