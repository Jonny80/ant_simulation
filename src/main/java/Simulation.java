import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    public static void main(String[] args){
        Window window = new Window();
        window.init();
        ShaderManager shader = new ShaderManager();
        Loader loader =  new Loader();
        Renderer renderer = new Renderer(shader);
        ModelFactory factory = new ModelFactory(loader);
        List<Entity> hive = factory.createAnts(0,0,0,5,400);
        Entity hiveEntity = factory.createHive(new Vector3f((float) 0, (float) 0, 0),0,0,0,100);
        List<Entity> foodSource = factory.createFoodSource(new Vector3f((float) 400, (float) -400, 0),20,5,50);
        Camera camera = new Camera();

        while (!Display.isCloseRequested()){
            // here movement


            // has to be called every frame
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(hiveEntity,shader);
            for (Entity entity : foodSource) {
                renderer.render(entity,shader);
            }
            for (Entity entity : hive) {
                renderer.render(entity, shader);
            }
            shader.stop();
            window.update();
        }
        shader.cleanUp();
        loader.cleanUp();
        window.end();


    }

}
