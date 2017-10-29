// Домашнее задание
// Организуем гонки
// Есть набор правил:
// Все участники должны стартовать одновременно, несмотря на то что на подготовку у каждого уходит разное время
// В туннель не может заехать одновременно больше половины участников(условность)
// Попробуйте все это синхронизировать
// Как только первая машина пересекает финиш, необходимо ее объявить победителем(победитель
// должен быть только один, и вообще должен быть)
// Только после того как все завершат гонку нужно выдать объявление об окончании
// Можете корректировать классы(в т.ч. конструктор машин)
// и добавлять объекты классов из пакета util.concurrent
package j3hw5race;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yuri Tveritin
 */
public class J3hw5race {
    public static final int CARS_COUNT = 4;
    public static CountDownLatch cdl=new CountDownLatch(CARS_COUNT);//счетчик подготовки потоков(машин)
    public static void main(String[] args) {
        
        System.out.println("Важное объявление>>>>Подготовка");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10),cdl);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        try {
            cdl.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(J3hw5race.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Важное объявление>>>>Гонка началась!");
        System.out.println("Важное объявление>>>>Гонка закончилась!");
    }
}
