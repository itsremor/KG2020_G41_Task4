package ru.cs.vsu.math;

public class Matrix3Rotation {
    private float[][] matrix;

    public Matrix3Rotation() {
        this.matrix = new float[3][3];
    }

    public void initXMatrix(){
        this.matrix[0][0] = 1;
    }

    public void initYMatrix(){
        this.matrix[1][1] = 1;
    }

    public void initZMatrix(){
        this.matrix[2][2] = 1;
    }

    public void setXMatrix(float alpha){
        this.matrix[1][1] = (float)Math.cos(alpha);
        this.matrix[1][2] = -1 * (float)Math.sin(alpha);
        this.matrix[2][1] = (float)Math.sin(alpha);
        this.matrix[2][2] = (float)Math.cos(alpha);
    }

    public void setYMatrix(float alpha){
        this.matrix[0][0] = (float)Math.cos(alpha);
        this.matrix[2][0] = -1 * (float)Math.sin(alpha);
        this.matrix[0][2] = (float)Math.sin(alpha);
        this.matrix[2][2] = (float)Math.cos(alpha);
    }

    public void setZMatrix(float alpha){
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
}
