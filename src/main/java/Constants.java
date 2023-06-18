public final class Constants {

    private Constants(){
        throw new AssertionError("Class for Constants only");
    }
    public enum MapType  {
        FOOD,
        HIVE,
        NONE,
    }
    public enum AntState {
        SEEKING,
        RETURNING,
        COLLECTING
    }



}
