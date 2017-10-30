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

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Yuri Tveritin
 */
public class J3hw5race {
    public static final int CARS_COUNT = 4;
    public static CyclicBarrier cb = new CyclicBarrier(CARS_COUNT+1);//счетчик запуска приостановленных потоков(машины+основной поток main)
    public static CountDownLatch cdl = new CountDownLatch(CARS_COUNT);//cчетчик событий запуска потоков (машин)
    public static Semaphore smp=new Semaphore(CARS_COUNT/2);//ограничение на запуск дочерних потоков в туннеле

    public static synchronized void main(String[] args) {
        
        System.out.println("Важное объявление>>>>Подготовка");
        Race race = new Race(new Road(60), new Tunnel(smp), new Road(40));
        Car[] cars = new Car[CARS_COUNT];//массив машин
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10),cb,cdl);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();//создаем и запускаем отдельные потоки (для машин)
        }      
        
        try {            
            cb.await();//приостанавливаем поток, т.к. ждем пока машины приготовятся к старту                
            System.out.println("Важное объявление>>>>Гонка началась!");
            cb.await();//приостанавливаем поток для одновременного запуска с потоками машин              
            cdl.await();/*приостанавливаем поток для определения из потоков 
            машин потока, завершившегося первым. По завершению всех потоков 
            машин (конец гонки) основной поток продолжает работу*/                      
        } catch (InterruptedException interruptedException) {
        } catch (BrokenBarrierException brokenBarrierException) {
        }
        
        System.out.println("Важное объявление>>>>Гонка закончилась!");
    }
}
