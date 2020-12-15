package ru.cs.vsu.math;

public class Matrix3Rotation {
    private float[][] matrix;

    public Matrix3Rotation() {
        this.matrix = new float[3][3];
    }

    private void initXMatrix(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.matrix[i][j] = 0;
            }
        }
        this.matrix[0][0] = 1;
    }

    private void initYMatrix(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.matrix[i][j] = 0;
            }
        }
        this.matrix[1][1] = 1;
    }

    private void initZMatrix(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.matrix[i][j] = 0;
            }
        }
        this.matrix[2][2] = 1;
    }

    public void setXMatrix(float alpha){
        this.initXMatrix();
        this.matrix[1][1] = (float)Math.cos(alpha);
        this.matrix[1][2] = -1 * (float)Math.sin(alpha);
        this.matrix[2][1] = (float)Math.sin(alpha);
        this.matrix[2][2] = (float)Math.cos(alpha);
    }

    public void setYMatrix(float alpha){
        this.initYMatrix();
        this.matrix[0][0] = (float)Math.cos(alpha);
        this.matrix[2][0] = -1 * (float)Math.sin(alpha);
        this.matrix[0][2] = (float)Math.sin(alpha);
        this.matrix[2][2] = (float)Math.cos(alpha);
    }

    public void setZMatrix(float alpha){
        this.initZMatrix();
        this.matrix[0][0] = (float)Math.cos(alpha);
        this.matrix[0][1] = -1 * (float)Math.sin(alpha);
        this.matrix[1][0] = (float)Math.sin(alpha);
        this.matrix[1][1] = (float)Math.cos(alpha);
    }

    //toDO СКОРЕЕ ВСЕГО ТУТЬ БУДЕТ КОСЯК!!!!!!!
    public Vector3 multVector(Vector3 vector){
        float newX = vector.getX() * this.matrix[0][0] + vector.getY() * this.matrix[1][0] + vector.getZ()
                * this.matrix[2][0];

        float newY = vector.getX() * this.matrix[0][1] + vector.getY() * this.matrix[1][1] + vector.getZ()
                * this.matrix[2][1];

        float newZ = vector.getX() * this.matrix[0][2] + vector.getY() * this.matrix[1][2] + vector.getZ()
                * this.matrix[2][2];

        return new Vector3(newX, newY, newZ);
    }

    public static float getAngle(Vector3 v1, Vector3 v2){
        float cos = (v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ())
                / (float)(Math.sqrt(v1.getX() * v1.getX() + v1.getY() * v1.getY() + v1.getZ() * v1.getZ())
        * Math.sqrt(v2.getX() * v2.getX() + v2.getY() * v2.getY() + v2.getZ() * v2.getZ()));

        return (float)Math.acos(cos);
    }

    public static float getAngleOZ(Vector3 v1, Vector3 v2){
        float dx = Math.abs(v1.getX() - v2.getX());
        float dy = Math.abs(v1.getY() - v2.getY());
        float dz = Math.abs(v1.getZ() - v2.getZ());

        return (float) Math.acos(dx / Math.sqrt(dx * dx + dy * dy));
    }

    public static float getAngleOY(Vector3 v1, Vector3 v2){
        float dx = Math.abs(v1.getX() - v2.getX());
        float dy = Math.abs(v1.getY() - v2.getY());
        float dz = Math.abs(v1.getZ() - v2.getZ());

        return (float) Math.acos(dx / Math.sqrt(dx * dx + dz * dz));
    }

    public static float getAngleOX(Vector3 v1, Vector3 v2){
        float dx = Math.abs(v1.getX() - v2.getX());
        float dy = Math.abs(v1.getY() - v2.getY());
        float dz = Math.abs(v1.getZ() - v2.getZ());

        return (float) Math.acos(dz / Math.sqrt(dz * dz + dy * dy));
    }

    public static Vector3 rotationOnX(Vector3 v, float angle){
        float newX = v.getX();
        float newY = (float)(v.getY() * Math.cos(angle) - v.getZ() * Math.sin(angle));
        float newZ = (float)(v.getY() * Math.sin(angle) + v.getZ() * Math.cos(angle));

        return new Vector3(newX, newY, newZ);
    }

    public static Vector3 rotationOnY(Vector3 v, float angle){
        float newX = (float)(v.getX() * Math.cos(angle) - v.getZ() * Math.sin(angle));
        float newY = v.getY();
        float newZ = (float)(v.getX() * Math.sin(angle) + v.getZ() * Math.cos(angle));

        return new Vector3(newX, newY, newZ);
    }

    public static Vector3 rotationOnZ(Vector3 v, float angle){
        float newX = (float)(v.getX() * Math.cos(angle) - v.getY() * Math.sin(angle));
        float newY = (float)(v.getX() * Math.sin(angle) + v.getY() * Math.cos(angle));
        float newZ = v.getZ();

        return new Vector3(newX, newY, newZ);
    }

    ;

}
