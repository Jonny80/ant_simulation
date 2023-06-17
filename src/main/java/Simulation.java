import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    public static void main(String[] args){
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
        Window window = new Window();
        window.init();
        ShaderManager shader = new ShaderManager();
        Loader loader =  new Loader();
        Renderer renderer = new Renderer(shader);

        ModelTexture hiveTexture = new ModelTexture(loader.loadTexture("/home/jonny/IdeaProjects/CGVI2/src/main/resources/textures/ground.png"));
        Model hiveModel = loader.loadToVAO(vertices,textureCoords,indices);
        TexturedModel texturedHive = new TexturedModel(hiveModel,hiveTexture);
       Entity hiveEntity = new Entity(texturedHive,new Vector3f((float) (Math.random() * 10000 - 5000), (float) (Math.random() * 10000 - 5000), 0),
                0,0,0,100);

        ModelTexture texture = new ModelTexture(loader.loadTexture("/home/jonny/IdeaProjects/CGVI2/src/main/resources/textures/black.png"));
        Model model = Loader.loadObjModel("/home/jonny/IdeaProjects/CGVI2/src/main/resources/objects/ant.obj", loader);
        TexturedModel texturedModel = new TexturedModel(model,texture);


        List<Entity> hive = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            hive.add(new Entity(texturedModel,new Vector3f((float) (Math.random() * 10000 - 5000), (float) (Math.random() * 10000 - 5000), 0),
                    0,0,0,10));
        }
        Camera camera = new Camera();

        while (!Display.isCloseRequested()){
            // here movement
           hiveEntity.increaseRotation(1,1,0);

            // has to be called every frame
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
           renderer.render(hiveEntity,shader);
            for (int i = 0; i < hive.size(); i++) {
                renderer.render(hive.get(i), shader);
            }
            shader.stop();
            window.update();
        }
        shader.cleanUp();
        loader.cleanUp();
        window.end();


    }

}
