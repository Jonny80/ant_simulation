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
        Map map = new Map(1600,1400);

        List<Ant> ants = factory.createAnts(0,0,0,0,0,10,60);
        Entity hive = factory.createHive(new Vector3f((float) 0, (float) 0, 0),0,0,0,100);
        map.setEntity(0,0,100, Constants.MapType.HIVE);

        List<List<Entity>> foodSources = new ArrayList<>();

        List<Entity> foodSource = factory.createFoodSource(new Vector3f((float) 400, (float) -400, 0),60,5,50);
        map.setEntity(400,-400,50, Constants.MapType.FOOD);
        List<Entity> foodSource2 = factory.createFoodSource(new Vector3f((float) -200, (float) -400, 0),60,5,50);
        map.setEntity(-200,-400,50, Constants.MapType.FOOD);
        List<Entity> foodSource3 = factory.createFoodSource(new Vector3f((float) 400, (float) 400, 0),60,5,50);
        map.setEntity(400,400,50, Constants.MapType.FOOD);
        List<Entity> foodSource4 = factory.createFoodSource(new Vector3f((float) -600, (float) 400, 0),60,5,50);
        map.setEntity(-600,400,50, Constants.MapType.FOOD);
        List<Entity> foodSource5 = factory.createFoodSource(new Vector3f((float) -800, (float) 600, 0),60,5,50);
        map.setEntity(-700,500,50, Constants.MapType.FOOD);
        List<Entity> foodSource6 = factory.createFoodSource(new Vector3f((float) -600, (float) -600, 0),60,5,50);
        map.setEntity(-600,-600,50, Constants.MapType.FOOD);


        foodSources.add(foodSource);
        foodSources.add(foodSource2);
        foodSources.add(foodSource3);
        foodSources.add(foodSource4);
        foodSources.add(foodSource5);
        foodSources.add(foodSource6);

        Camera camera = new Camera();


        for (Ant ant:
             ants) {
            ant.setFood(factory.createFood(ant.getPosition(),10));
        }



        while (!Display.isCloseRequested()){
            // here movement
            for (Ant ant : ants) {
                Constants.MapType type =
                        map.getTile((int) ant.getPosition().x, (int) ant.getPosition().y);
                ant.processState(type);
            }
            // has to be called every frame
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(hive,shader);
            for (List<Entity> fs: foodSources){
                for (Entity entity : fs) {
                    renderer.render(entity,shader);
                }
            }
            for (Ant ant : ants) {
                renderer.render(ant, shader);
            }
            for (Ant ant : ants) {
                if (ant.getState() == Constants.AntState.RETURNING){
                    renderer.render(ant.getFood(),shader);
                }
            }
            shader.stop();
            window.update();
        }

        shader.cleanUp();
        loader.cleanUp();
        window.end();
    }

}
