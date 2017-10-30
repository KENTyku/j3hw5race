/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j3hw5race;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    
    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    CyclicBarrier cb;
    CountDownLatch cdl;
//    int win;
    public    volatile int win =0;//победитель

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }
    public void win(){
        System.out.println (this.name+" ВЫИГРАЛ");        
    }

    public Car(Race race, int speed, CyclicBarrier cb,CountDownLatch cdl) {//конструктор класса
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cb=cb;
        this.cdl=cdl;
        this.win=win;
    }

    @Override
    public synchronized void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");            
            cb.await();//приостанавливаем поток, т.к. ждем что остальные машины готовятся к старту
            cb.await();//приостанавливаем поток,т.к. ждем сообщение о начале гонки
        } catch (Exception e) {
            e.printStackTrace();
        }
        //цикл преодоления этапов гонки
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }        
        if (cdl.getCount()==4) win();/*проверка количества выполенных событий 
        countDown(если не произошло еще ни одного события, то значит данный 
        поток заканчивает свое выполнение первым*/ 
        cdl.countDown();/*выполнение события countDown (требуется для 
        возобновления работы главного потока main)*/
    }
}

