import org.lwjgl.util.vector.Vector3f;

public class Utils {
    public static float getScalar(Vector3f a,Vector3f b){
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }


    public static float getAmount(Vector3f a){
        return (float) Math.sqrt((Math.pow(a.x,2)+Math.pow(a.y,2) + Math.pow(a.z,2)));
    }


    public static double getDegreeOfVec(Vector3f a,Vector3f b){
        return Math.toDegrees(Math.acos(getScalar(a,b)/(getAmount(a)*getAmount(b))));
    }

    public static double getDegreeToZ(Vector3f a){
        return getDegreeOfVec(a,new Vector3f(1,1,1));
    }
    public static double getDegreeToY(Vector3f a){
        return getDegreeOfVec(a,new Vector3f(0,1,0));
    }
}
