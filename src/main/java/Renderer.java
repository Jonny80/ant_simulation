import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

public class Renderer {

    private static final float FOV = 100;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 10000;
    private Matrix4f projectionMatrix;

    public Renderer(ShaderManager shader){
        createProjectionMatrix();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }
    public void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(1.39f, 0.69f, 0.45f, 0);
    }

    public void render(Entity entity,ShaderManager shader){
        TexturedModel texturedModel = entity.getModel();
        Model model = texturedModel.getRawModel();
        // Bind Vaos
        GL30.glBindVertexArray(model.getVaoID());
        // activate attrib List added to attrib List nr 0
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        // creating transformation Matreix to get movement
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),entity.getRotX(),entity.getRotY(),entity.getRotZ(),entity.getScale());

        shader.loadTransformationMatrix(transformationMatrix);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,texturedModel.getTexture().getID());

        // Draw Triangels
        GL11.glDrawElements(GL11.GL_TRIANGLES,model.getVertexCount(),GL11.GL_UNSIGNED_INT,0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        // unbind vao
        GL30.glBindVertexArray(0);
    }

    public void renderTexture(TexturedModel texturedModel){
        Model model = texturedModel.getRawModel();
        // Bind Vaos
        GL30.glBindVertexArray(model.getVaoID());
        // activate attrib List added to attrib List nr 0
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,texturedModel.getTexture().getID());
        // Draw Triangels
        GL11.glDrawElements(GL11.GL_TRIANGLES,model.getVertexCount(),GL11.GL_UNSIGNED_INT,0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        // unbind vao
        GL30.glBindVertexArray(0);
    }

    private void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }


}
