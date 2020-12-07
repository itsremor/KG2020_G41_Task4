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

    public Helix(int countOfTurns, int countOfPoints, float radius, float step) {
        this.countOfTurns = countOfTurns;
        this.countOfPoints = countOfPoints;
        this.radius = radius;
        this.step = step;
    }

    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();



        float currentZ = -0.5f * countOfTurns * step;
        float currentX;
        float currentY;
        float currentRad = 0;

        Vector3 current = new Vector3(radius, 0, currentZ);
        Vector3 previous;
        Vector3 twoPoints[];

        float radIncr = (float)(2 * Math.PI / countOfPoints);
        float zIncr = step / countOfPoints;

        for (int i = 0; i < this.countOfTurns; i++) {
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
