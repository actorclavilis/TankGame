package TankController;

import Tanks.*;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.KeyEventDispatcher;
import java.awt.event.MouseEvent;

public class HumanController extends TankController implements MouseMotionListener, MouseListener, KeyEventDispatcher 
{
    private Configuration c;
    private boolean kU = false, kD = false, kL = false, kR = false, fire = false, defense = false;
    private Point mousePoint;
    private Point oldScreenPoint;
    private Point screenPoint;
    
    public HumanController(Tank t, Configuration _c) 
    {
        super(t);     
        mousePoint = new Point(0,0);
        screenPoint = new Point(0,0);
        oldScreenPoint = new Point(0,0);
        c = _c;
    }
    
    public void poll() 
    {
        tank.move((kU ? 1 : 0) - (kD ? 1 : 0));
        tank.rotate((kR ? 1 : 0) - (kL ? 1 : 0));
        
        if(fire && !defense)
        {
            tank.fire();
        }  
        else
        {
            tank.cooldown();
        }
        
        if(defense)
        {
            tank.defend();
        }
        else
        {       
            tank.stopDefend();
        }
    }
    
    public void setScreenPoint(Point _screenPoint)
    {
        screenPoint.setLocation(_screenPoint);
        if(!screenPoint.equals(oldScreenPoint))
        {
            int dx = oldScreenPoint.x-screenPoint.x;
            int dy = oldScreenPoint.y-screenPoint.y;
            mousePoint.translate(dx, dy);
            tank.movePoint(mousePoint); 
            oldScreenPoint = (Point)screenPoint.clone();
        }      
    }

    public boolean dispatchKeyEvent(KeyEvent e) {
        boolean pressed = e.getID() == KeyEvent.KEY_PRESSED;
        int ev = e.getKeyCode();
        
        if (ev == c.mUp) 
        {
            kU = pressed;
            return true;
        }
        if (ev == c.mDown) 
        {
            kD = pressed;
            return true;
        }
        if (ev == c.mLeft) 
        {
            kL = pressed;
            return true;
        }
        if (ev == c.mRight) 
        {
            kR = pressed;
            return true;
        }
        if (ev == c.kSpace) 
        {
            tank.specialFire();
            return true;
        }
        return false;
    }

    public void mouseDragged(java.awt.event.MouseEvent e) 
    {
        mousePoint = new Point(e.getX() - screenPoint.x, e.getY() - screenPoint.y);
        tank.movePoint(mousePoint);
    }

    public void mouseMoved(java.awt.event.MouseEvent e) 
    {
        mouseDragged(e);
    }

    public void mouseClicked(java.awt.event.MouseEvent e) 
    {
        mouseDragged(e);
    }
    
    public void mousePressed(java.awt.event.MouseEvent e) 
    {
        mouseDragged(e);
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            fire = true;
        }
        else if(e.getButton() == MouseEvent.BUTTON3)
        {
            defense = true;
        }
    }
    
    public void mouseReleased(java.awt.event.MouseEvent e) 
    {
        mouseDragged(e);
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            fire = false;
        }
        else if(e.getButton() == MouseEvent.BUTTON3)
        {
            defense = false;
        }
    }
    
    public void mouseEntered(java.awt.event.MouseEvent e) 
    {
        mouseDragged(e);
    }
    
    public void mouseExited(java.awt.event.MouseEvent e) 
    {
        mouseDragged(e);
    }
    
    public static class Configuration
    {
        public int mUp, mDown, mLeft, mRight, kSpace, aUp, aDown, aLeft, aRight;
        public boolean mouse;
        
        public Configuration(int config)
        {            
            if(config == 1)
            {
                mUp = KeyEvent.VK_W;
                mDown = KeyEvent.VK_S;
                mLeft = KeyEvent.VK_A;
                mRight = KeyEvent.VK_D;
                kSpace = KeyEvent.VK_SPACE;
                mouse = true;
            }
            else if(config == 2)
            {
                mUp = KeyEvent.VK_UP;
                mDown = KeyEvent.VK_DOWN;
                mLeft = KeyEvent.VK_LEFT;
                mRight = KeyEvent.VK_RIGHT;
                kSpace = KeyEvent.VK_0;
                mouse = true;
            }
            else if(config == 3)
            {
                mUp = KeyEvent.VK_W;
                mDown = KeyEvent.VK_S;
                mLeft = KeyEvent.VK_A;
                mRight = KeyEvent.VK_D;
                kSpace = KeyEvent.VK_SPACE;
                aUp = KeyEvent.VK_I;
                aDown = KeyEvent.VK_K;
                aLeft = KeyEvent.VK_J;
                aRight = KeyEvent.VK_L;
                mouse = false;
            }
        }
    }
}
