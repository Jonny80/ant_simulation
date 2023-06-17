import org.lwjgl.opengl.*;

public class Window {

    private int width;
    private int height;
    private int fps;

    public Window(int width,int height,int fps){
       this.width = width;
        this.height = height;
        this.fps = 60;
    }


    public Window(){
        this.width = 1280;
        this.height = 720;
        this.fps = 60;
    }

    public void init()  {
        ContextAttribs contextAttribs = new ContextAttribs(3,2).
                withForwardCompatible(true).withProfileCore(true);
        try{
            Display.setDisplayMode(new DisplayMode(width,height));
            Display.create(new PixelFormat(),contextAttribs);
            Display.setTitle("Ant Simulation");
        }catch (Exception e){
            System.out.println("Creation failed due to: " + e);
        }
        GL11.glViewport(0,0,width,height);
    }


    public void update(){
        Display.sync(fps);
        Display.update();
    }

    public void end(){
        Display.destroy();
    }

}
