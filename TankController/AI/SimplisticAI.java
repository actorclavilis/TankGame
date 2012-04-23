
package TankController.AI;

import Game.GUI;
import TankController.TankController;
import Tanks.Tank;
import java.awt.Point;
import java.util.Iterator;
import Resources.*;
import Tanks.Bullets.Bullet;
import java.awt.Dimension;

public class SimplisticAI extends TankController {
    private static final long RECALC_INT = 1000, FIRE_INT = 300;
    private static final double WAV_BEN = .2, WAV_STR = 5;
    private long recalcT = 0, fireT = 0;
    protected Tank target;
    private Dimension fd;
    private int mod = 0;

    
    public SimplisticAI (Tank t, Dimension d) {
        super(t);
        fd = d;
    }
    
    @Override
    @DontSynchronize
    public void poll() {
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
            else tank.cooldown();
        }
        
        double fx = -Math.cos(tank.getDirection())/10, fy = -Math.sin(tank.getDirection())/10, x = tank.getCenterPoint().x, y = tank.getCenterPoint().y;
        /*synchronized (GUI.theGUI.tanksMutex()) {   //go toward tanks
            Iterator i = GUI.theGUI.tanks().iterator();
            while(i.hasNext()) {
                Tank t = (Tank)i.next();
                if(t.equals(tank)) continue;
                double r = t.distanceFrom2(x, y);
                double a = Math.atan2(t.getCenterPoint().y-y, t.getCenterPoint().x-x);
                fx -= Math.cos(a)/r;
                fy -= Math.sin(a)/r;
            }
        }*/
        /*synchronized (GUI.theGUI.bulletsMutex()) {  //go away from bullets
            Iterator i = GUI.theGUI.bullets().iterator();
            while(i.hasNext()) {
                Bullet b = (Bullet)i.next();
                if(b.parent().equals(tank)) continue;
                double r = b.distanceFrom2(x, y);
                double a = Math.atan2(b.y()-y, b.x()-x);
                if(Tanks.PowerUp.class.isInstance(b))
                    r = -r;                           //go toward powerups
                fx += Math.cos(a)/r;
                fy += Math.sin(a)/r;
            }
        }*/
        
        x /= fd.width; y /= fd.height;
        double wx = WAV_STR*((1+WAV_BEN)*x*x*x - WAV_BEN*x);
        double wy = WAV_STR*((1+WAV_BEN)*y*y*y - WAV_BEN*y);
        
        double dr = Math.atan2(fy+wy, fx+wx) - tank.getDirection();
        //tank.setMessage(Integer.toString((int)Math.round(dr*180/Math.PI)).concat(" ").concat(Integer.toString((int)Math.round(tank.getDirection()*180/Math.PI))));
        tank.setMessage(Integer.toString((int)(/*fx-*/wx)).concat(" ").concat(Integer.toString((int)(/*fy-*/wy))));
        tank.rotate((int)Math.signum(dr));
        tank.move(((dr>Math.PI/2) || (dr < -Math.PI/2))?1:-1);
    }

    private synchronized void recalculate() {
        synchronized(GUI.theGUI.tanksMutex())
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
