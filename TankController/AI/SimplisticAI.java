
package TankController.AI;

import Game.GUI;
import TankController.TankController;
import Tanks.Tank;
import java.awt.Point;
import java.util.Iterator;

public class SimplisticAI extends TankController {
    private static long RECALC_INT = 1000, FIRE_INT = 300;
    private long recalcT = 0, fireT = 0;
    protected Tank target;
    protected double destA;
    
    public SimplisticAI (Tank t) {
        super(t);
    }
    
    @Override
    public synchronized void poll() {
        super.poll();
        
        if(death) return;

        if(System.currentTimeMillis() - recalcT > RECALC_INT) {
            recalculate();
            recalcT = System.currentTimeMillis();
        }
       
        if (target != null) {
            tank.movePoint(target.getCenterPoint());
            if (System.currentTimeMillis() - fireT > FIRE_INT) {
                tank.fire();
                fireT = System.currentTimeMillis();
            }
        }
        if(destA - tank.getBarrelAngle() < Math.PI)
            tank.rotate(1);
        else
            tank.rotate(-1);
        tank.move(1);
    }

    private synchronized void recalculate() {
        destA = 2*Math.PI*Math.random();
        
        synchronized(GUI.theGUI.tanks())
        {
            double distance = (target != null) ? target.getCenterPoint().distance(tank.getCenterPoint()) : Double.MAX_VALUE, newdist = 0;
            Iterator i = GUI.theGUI.tanks().iterator();
            while(i.hasNext()) {
                Tank t = (Tank)i.next();
                if(t != tank && (newdist = t.getCenterPoint().distance(tank.getCenterPoint())) < distance) {
                    distance = newdist;
                    target = t;
                }
            }
        }
    }
    
}
