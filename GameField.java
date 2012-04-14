import java.awt.geom.*;
import java.awt.*;

public class GameField 
{
    private Rectangle2D boundary;
    private Color boundaryColor;
    
    public GameField(Color _boundaryColor, Rectangle2D _boundary)
    {
        boundary = _boundary;
        boundaryColor = _boundaryColor;
    }
    
    public void drawField(Graphics2D g)
    {
        g.setColor(boundaryColor);
        g.draw(boundary);
    }
    
    public Rectangle2D getBounds()
    {
        return boundary;
    }
}
