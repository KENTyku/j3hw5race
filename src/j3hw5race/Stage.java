package j3hw5race;

public abstract class Stage {//этап гонки
    protected int length;
    protected String description;

    public String getDescription() {
        return description;
    }

    public abstract void go(Car c);
}
