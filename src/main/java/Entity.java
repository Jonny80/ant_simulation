import org.lwjgl.util.vector.Vector3f;

// instance of textured model and position plus scale
// Reusable
public class Entity {
    private TexturedModel model;
    private Vector3f position;
    private float rotX,rotY,rotZ;
    private float scale;

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }
    public void increaseRotation(float x,float y,float z) {
        this.rotX += x;
        this.rotY += y;
        this.rotZ += z;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }
    public void setPosX(float x){
        this.position.x = x;
    }
    public void setPosY(float y){
        this.position.y = y;
    }

    public void setPosZ(float z){
        this.position.z = z;
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX%360;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY%360;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ%360;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

}
