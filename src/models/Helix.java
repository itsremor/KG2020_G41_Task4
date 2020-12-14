package models;

import ru.cs.vsu.math.Vector3;
import ru.cs.vsu.third.IModel;
import ru.cs.vsu.third.PolyLine3D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Helix implements IModel {

    private int countOfTurns;
    private int countOfPoints;
    private float radius;
    private float step;
    private boolean clockwise;
    private float thickness;

    public Helix(int countOfTurns, int countOfPoints, float radius, float step, float thickness,
                 int countOfPointsPerTick, boolean clockwise) {
        this.countOfTurns = countOfTurns;
        this.countOfPoints = countOfPoints;
        this.radius = radius;
        this.step = step;
        this.clockwise = clockwise;
        this.thickness = thickness;
    }

    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();

        int koef = clockwise? -1 : 1;

        float currentZ = -0.5f * countOfTurns * step;
        float currentX;
        float currentY;
        float currentRad = 0;

        Vector3 current = new Vector3(radius, 0, currentZ);
        Vector3 previous;

        Vector3 thickCur;
        Vector3 thickPrev;


        Vector3 twoPoints[];

        float radIncr = (float)(2 * Math.PI / countOfPoints * koef);
        float zIncr = step / countOfPoints;

        //внешний цикл i отвечает за кол-во витков
        for (int i = 0; i < this.countOfTurns; i++) {
            //внутренний цикл j отвечает за построение каждого витка
            for (int j = 0; j < countOfPoints; j++) {
                currentX = (float) (Math.cos(currentRad) * radius);
                currentY = (float) (Math.sin(currentRad) * radius);


                previous = current;
                current = new Vector3(currentX, currentY, currentZ);

                twoPoints = new Vector3[]{previous, current};

                lines.add(new PolyLine3D(Arrays.asList(twoPoints), true));

                currentRad += radIncr;
                currentZ += zIncr;
            }
        }

        return lines;
    }
}
