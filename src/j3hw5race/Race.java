package j3hw5race;

import java.util.ArrayList;
import java.util.Arrays;

public class Race {//класс описывающий гонку
    private ArrayList<Stage> stages;//список хранящий этапы

    public ArrayList<Stage> getStages() { return stages; }//метод возвращающий список этапов

    public Race(Stage... stages) {//конструктор класса Race
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
}
