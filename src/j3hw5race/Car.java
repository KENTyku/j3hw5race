/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j3hw5race;

import static j3hw5race.J3hw5race.cdl;
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
        if (win==0) {
            System.out.println (this.name+" ВЫИГРАЛ");
            win=1;
        }
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
            cb.await();
            cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        cdl.countDown();
        if (cdl.getCount()==3) win();
        
        

    }
}

