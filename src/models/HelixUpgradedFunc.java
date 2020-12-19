package models;

import functions.IFunction;
import ru.cs.vsu.math.Matrix3Rotation;
import ru.cs.vsu.math.Vector3;
import ru.cs.vsu.third.IModel;
import ru.cs.vsu.third.PolyLine3D;

import java.lang.invoke.VarHandle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HelixUpgradedFunc implements IModel {

    private int countOfTurns;
    private int countOfPointsPerTurn;
    private float radius;
    private float step;
    private boolean clockwise;
    private float thickness;
    private int countOfPointsPerTick;
    IFunction radiusFunc;
    IFunction stepFunc;

    public HelixUpgradedFunc(int countOfTurns, int countOfPointsPerTurn, float radius, float step, float thickness,
                             int countOfPointsPerTick, boolean clockwise,
                             IFunction radiusFunc, IFunction stepFunc) {
        this.countOfTurns = countOfTurns;
        this.countOfPointsPerTurn = countOfPointsPerTurn;
        this.radius = radius;
        this.step = step;
        this.clockwise = clockwise;
        this.thickness = thickness;
        this.countOfPointsPerTick = countOfPointsPerTick;
        this.radiusFunc = radiusFunc;
        this.stepFunc = stepFunc;
    }

    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();
        Vector3[] carcass = new Vector3[countOfTurns * (countOfPointsPerTurn + 1)];

        int koef = clockwise? 1 : -1;

        float currentZ = 0;
        float currentX;
        float currentY;
        float currentRad = 0;

        int counter = 0;

        float radIncr = (float)(2 * Math.PI / countOfPointsPerTurn * koef);

        float zIncr = step / countOfPointsPerTurn;
        float zConstIncr = thickness * 2 / countOfPointsPerTurn;
        float percent;

        //внешний цикл i отвечает за кол-во витков
        for (int i = 0; i < this.countOfTurns; i++) {
            //внутренний цикл j отвечает за построение каждого витка
            for (int j = 0; j <= countOfPointsPerTurn; j++) {
                percent = (float) (i * countOfPointsPerTurn + j) / carcass.length;
                currentX = (float) (Math.cos(currentRad) * radius) * radiusFunc.complete(percent) + thickness;
                currentY = (float) (Math.sin(currentRad) * radius) * radiusFunc.complete(percent) + thickness;

                carcass[counter] = new Vector3(currentX, currentY, currentZ);
                currentRad += radIncr;
                currentZ += zIncr * stepFunc.complete(percent) + zConstIncr;
                counter++;
            }
        }

        //lines.add(new PolyLine3D(Arrays.asList(carcass), false));

        Vector3[][] section = new Vector3[carcass.length][countOfPointsPerTick];

        radIncr = (float)(2 * Math.PI / countOfPointsPerTick);

        float angleX = Matrix3Rotation.getAngleOX(carcass[0], carcass[1]) * koef;
        float angleY = Matrix3Rotation.getAngleOY(carcass[0], carcass[1]) * koef;

        float angleZ = 0; // = Matrix3Rotation.getAngleOZ(carcass[0], carcass[1]);
        float angleZIncr = (float)(Math.PI * 2 / (carcass.length / countOfTurns - 1)) * koef;
        Vector3 temp;

        for (int i = 0; i < section.length; i++) {
            currentRad = 0;

            for (int j = 0; j < countOfPointsPerTick; j++) {
                currentX = (float) Math.sin(currentRad) * thickness;
                currentY = 0;
                currentZ = (float) Math.cos(currentRad) * thickness;

                temp = new Vector3(currentX, currentY, currentZ);

                temp = Matrix3Rotation.rotationOnY(temp, angleY);
                temp = Matrix3Rotation.rotationOnX(temp, angleX);
                temp = Matrix3Rotation.rotationOnZ(temp, angleZ);

                section[i][j] = new Vector3(temp.getX() + carcass[i].getX(), temp.getY() + carcass[i].getY(),
                        temp.getZ() + carcass[i].getZ());

                currentRad += radIncr;
            }

            angleZ += angleZIncr;
        }

        for (int i = 0; i < section.length - 1; i++) {
            for (int j = 0; j < section[i].length - 1; j++) {

                lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                        new Vector3(section[i][j].getX(), section[i][j].getY(), section[i][j].getZ()),
                        new Vector3(section[i+1][j].getX(), section[i+1][j].getY(), section[i+1][j].getZ()),
                        new Vector3(section[i+1][j+1].getX(), section[i+1][j+1].getY(), section[i+1][j+1].getZ()),
                        new Vector3(section[i][j+1].getX(), section[i][j+1].getY(), section[i][j+1].getZ())
                }), true));
            }
            lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                    new Vector3(section[i][0].getX(), section[i][0].getY(), section[i][0].getZ()),
                    new Vector3(section[i+1][0].getX(), section[i+1][0].getY(), section[i+1][0].getZ()),
                    new Vector3(section[i+1][section[i].length - 1].getX(), section[i+1][section[i].length - 1].getY(), section[i+1][section[i].length - 1].getZ()),
                    new Vector3(section[i][section[i].length - 1].getX(), section[i][section[i].length - 1].getY(), section[i][section[i].length - 1].getZ())
            }), true));
        }

        return lines;
    }
}