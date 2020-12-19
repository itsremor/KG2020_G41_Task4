package functions;

public class CosinusFunction implements IFunction{
    @Override
    public float complete(float percent) {
        float rad = (float)Math.PI * percent;
        return (float)Math.abs(Math.cos(rad));
    }
}
