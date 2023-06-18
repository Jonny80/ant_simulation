import org.lwjgl.util.vector.Vector3f;

import java.util.Random;


public class Ant extends Entity{
    private final int directionRadius = 45;
    private final Vector3f hivePos = new Vector3f(0,0,0);
    private Vector3f foodSource;
    private Constants.AntState state ;
    private float speed;
    private int directionDecision;
    private Entity food;
    public Ant(TexturedModel model, Vector3f position, Vector3f rotation, float scale,float speed){
        super(model,position, rotation.x, rotation.y, rotation.z, scale);
        this.speed = speed;
        this.foodSource = null;
        this.directionDecision = 0;
        this.food =null;
        this.state = Constants.AntState.SEEKING;
    }

    public Entity getFood() {
        return food;
    }

    public void setFood(Entity food) {
        this.food = food;
    }

    private Vector3f getDirectionVec(Vector3f pos){
        return new Vector3f(pos.x - getPosition().x,pos.y - getPosition().y,0);
    }
    private float randomPosition(int range){
        Random random= new Random();
        int pos  = random.nextInt(range);
        if (pos > (float) (range/2)){
            return ((pos - ((float) range /2))*-1);
        }
        return pos;
    }

    public Constants.AntState getState(){
        return this.state;
    }


    public void processState(Constants.MapType type){
        if (this.state == Constants.AntState.SEEKING){
            if (type == Constants.MapType.NONE){
                moveRandom();
                return;
            }
            if (type == Constants.MapType.FOOD){
                this.foodSource = new Vector3f(getPosition());
                this.changeState(Constants.AntState.RETURNING);
                moveToPosition(hivePos);
                return;
            }
            if (type == Constants.MapType.HIVE){
                moveRandom();
                return;
            }
        }
        if (this.state == Constants.AntState.RETURNING){
            this.food.setPosition(this.setFoodPos());
            if (type == Constants.MapType.HIVE){
                this.changeState(Constants.AntState.COLLECTING);
                moveToPosition(foodSource);
                return;
            }
            if (type == Constants.MapType.NONE){
                moveToPosition(hivePos);
                return;
            }
            if (type == Constants.MapType.FOOD){
                moveToPosition(hivePos);
                return;
            }
        }
        if (this.state == Constants.AntState.COLLECTING){
            if (type == Constants.MapType.NONE){
                moveToPosition(foodSource);
                return;
            }
            if (type == Constants.MapType.FOOD){
                this.changeState(Constants.AntState.RETURNING);
                moveToPosition(hivePos);
                return;
            }
            if (type == Constants.MapType.HIVE){
                if (foodSource == null){
                    System.out.println("Foudsource not found");
                    this.changeState(Constants.AntState.SEEKING);
                }
                moveToPosition(foodSource);
                return;
            }
        }

    }
    public void changeState(Constants.AntState state){
        this.state = state;
    }

    public void moveRandom(){
        if (Math.abs(getPosition().x) > 700 || Math.abs(getPosition().y) > 600){
            setRotZ(getRotZ() - 180);
            moveForward();
            directionDecision = 0;
            return;
        }

        if (directionDecision < 20){
            moveForward();
            if (food != null){
                food.setPosition(getPosition());
            }
            directionDecision++;
        }else {
            float result =randomPosition(directionRadius);
            setRotZ(getRotZ() + result);
            directionDecision = 0;
        }
    }

    public Vector3f getFacingVec(){
        double degree = this.getRotZ();
        float x = (float) (Math.sin(Math.toRadians(degree)));
        float y = (float) (Math.cos(Math.toRadians(degree)));
        return new Vector3f(x*-1,y,0);
    }
    public Vector3f getFacingVec(double degree){
        float x = (float) (Math.sin(Math.toRadians(degree)));
        float y = (float) (Math.cos(Math.toRadians(degree)));
        return new Vector3f(x*-1,y,0);
    }

    private void moveForward(){
        Vector3f direction = getFacingVec().normalise(new Vector3f(speed,speed,speed));
        Vector3f newPostion = new Vector3f(getPosition().x + direction.x,getPosition().y + direction.y,0);
        setPosition(newPostion);
    }

    private Vector3f setFoodPos(){
        Vector3f direction = getFacingVec().normalise(new Vector3f(1,1,1));
        return new Vector3f(getPosition().x + direction.x*35,getPosition().y + direction.y*35,0);

    }

    public double getTurnDestiny(Vector3f dest){
        return Utils.getDegreeToY(dest);
    }

    public double getDeltaTurn(Vector3f moveVec){
        Vector3f facingVec = getFacingVec();
        return Utils.getDegreeOfVec(moveVec,facingVec);
    }
    public double getDeltaTurnByPos(Vector3f pos){
        Vector3f moveVec = getDirectionVec(pos);
        Vector3f facingVec = getFacingVec();
        return Utils.getDegreeOfVec(moveVec,facingVec);
    }
    public double getDeltaTurnByPos(Vector3f pos,Vector3f facingVec){
        Vector3f moveVec = getDirectionVec(pos);
        return Utils.getDegreeOfVec(moveVec,facingVec);
    }


    public void moveToPosition(Vector3f position){
        double deltaTurn = getDeltaTurnByPos(position);

        float rotLeft = (float) (deltaTurn + getRotZ());
        float rotRight = (float) (getRotZ() - deltaTurn);
        Vector3f facingVecLeft = getFacingVec(rotLeft);
        Vector3f facingVecRight = getFacingVec(rotRight);

        double deltaTurnLeft = getDeltaTurnByPos(position,facingVecLeft);
        // double deltaTurnRight = getDeltaTurnByPos(hivePos,facingVecRight);

        if (Double.isNaN(deltaTurnLeft) || deltaTurnLeft < 1 ){
            setRotZ((float) (deltaTurn/20 + getRotZ()));
        }else {
            setRotZ((float)(getRotZ() - deltaTurn/20));
        }
        moveForward();
    }
}
