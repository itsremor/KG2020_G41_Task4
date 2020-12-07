package ru.cs.vsu;

public class TestClass {
    public static void main(String[] args) {
        getSpiral(1, 1, 4, 360);
    }

    public static void getSpiral(int countOfTurns, double radius, double step, int countPointsPerTurn){
        double currentRad = 0;
        double radStep = 2 * Math.PI / countPointsPerTurn;
        double zStep = step / countPointsPerTurn;

        double currentX;
        double currentY;
        double currentZ = 0;


        //current Rad всегда начинается с PI/4, т.к. угол = 45градусов


        for (int i = 0; i <= countPointsPerTurn; i++) {

            currentX = radius * Math.cos(currentRad);
            currentY = radius * Math.sin(currentRad);
            currentZ += zStep;

            System.out.println(i + "°: cos: " + currentX + " sin: " + currentY);
            currentRad += radStep;
        }

    }
}
