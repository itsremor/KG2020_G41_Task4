package models;

import ru.cs.vsu.math.Matrix3Rotation;
import ru.cs.vsu.math.Vector3;
import ru.cs.vsu.third.IModel;
import ru.cs.vsu.third.PolyLine3D;

import java.lang.invoke.VarHandle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HelixUpgraded implements IModel {

    private int countOfTurns;
    private int countOfPointsPerTurn;
    private float radius;
    private float step;
    private boolean clockwise;
    private float thickness;
    private int countOfPointsPerTick;

    public HelixUpgraded(int countOfTurns, int countOfPointsPerTurn, float radius, float step, float thickness,
                 int countOfPointsPerTick, boolean clockwise) {
        this.countOfTurns = countOfTurns;
        this.countOfPointsPerTurn = countOfPointsPerTurn;
        this.radius = radius;
        this.step = step;
        this.clockwise = clockwise;
        this.thickness = thickness;
        this.countOfPointsPerTick = countOfPointsPerTick;
    }

    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();
        Vector3[] carcass = new Vector3[countOfTurns * (countOfPointsPerTurn + 1)];

        int koef = clockwise? -1 : 1;

        float currentZ = -0.5f * countOfTurns * step;
        float currentX;
        float currentY;
        float currentRad = 0;

        int counter = 0;

        float radIncr = (float)(2 * Math.PI / countOfPointsPerTurn * koef);
        float zIncr = step / countOfPointsPerTurn;

        //внешний цикл i отвечает за кол-во витков
        for (int i = 0; i < this.countOfTurns; i++) {
            //внутренний цикл j отвечает за построение каждого витка
            for (int j = 0; j <= countOfPointsPerTurn; j++) {
                currentX = (float) (Math.cos(currentRad) * radius);
                currentY = (float) (Math.sin(currentRad) * radius);

                carcass[counter] = new Vector3(currentX, currentY, currentZ);
                currentRad += radIncr;
                currentZ += zIncr;
                counter++;
            }
        }

        lines.add(new PolyLine3D(Arrays.asList(carcass), false));

        Vector3[][] section = new Vector3[carcass.length][countOfPointsPerTick];

        radIncr = (float)(2 * Math.PI / countOfPointsPerTick);

/*
        for (int i = 0; i < section.length - 1; i++) {
            currentRad = 0;
            for (int j = 0; j < countOfPointsPerTick; j++) {
                currentX = carcass[i].getX() + (float)Math.cos(currentRad) * thickness;
                currentY = carcass[i].getY() + (float)Math.sin(currentRad) * thickness;
                currentZ = carcass[i].getZ();

                section[i][j] = new Vector3(currentX, currentY, currentZ);
                currentRad += radIncr;
            }
        }

 */


        float angleX = Matrix3Rotation.getAngleOX(carcass[0], carcass[1]);
        float angleY = Matrix3Rotation.getAngleOY(carcass[0], carcass[1]);
        float angleZ = Matrix3Rotation.getAngleOZ(carcass[0], carcass[1]);

        Vector3 temp;

        for (int i = 0; i < section.length - 1; i++) {
            currentRad = 0;

            for (int j = 0; j < countOfPointsPerTick; j++) {
                currentX = (float)Math.cos(currentRad) * thickness;
                currentY = (float)Math.sin(currentRad) * thickness;
                currentZ = 0;
                temp = new Vector3(currentX, currentY, currentZ);

                temp = Matrix3Rotation.rotationOnY(temp, angleY);
                temp = Matrix3Rotation.rotationOnX(temp, angleX);
                //temp = Matrix3Rotation.rotationOnZ(temp, angleZ);

                section[i][j] = new Vector3(temp.getX() + carcass[i].getX(), temp.getY() + carcass[i].getY(),
                        temp.getZ() + carcass[i].getZ());

                currentRad += radIncr;
            }
        }


        for (int i = 0; i < section.length - 1; i++) {
            lines.add(new PolyLine3D(Arrays.asList(section[i]), true));
        }

        //мы сами строим окружность для крайних точек



        return lines;
    }
}