package Game;

import java.awt.Dimension;

public class GameController 
{
    private String[] names;
    private int[] scores;
    private boolean gameOn, menuOn;
    private boolean oldGameOn, oldMenuOn;
    private boolean windowMode, oldWindowMode, soundOn;
    
    public GameController(boolean initialMenu)
    {
        oldMenuOn = initialMenu;
        menuOn = initialMenu;
    }
    
    public void setStatus(boolean _gameOn, boolean _menuOn)
    {
        oldGameOn = gameOn;
        oldMenuOn = menuOn;
        gameOn = _gameOn;
        menuOn = _menuOn;
    }
    public int loadPanel()
    {
        if(oldGameOn != gameOn)
        {
            oldMenuOn = !menuOn;
            menuOn = !menuOn;
            return 1;
        }
        if(oldMenuOn != menuOn)
        {      
            oldGameOn = !gameOn;
            gameOn = !gameOn;
            return 2;
        }
        return 0;
    }
    public void setResults(String[] _names, int[] _scores)
    {
        names = _names;
        scores = _scores;
    }
    public int[] getScores()
    {
        return scores;
    }
    public String[] getNames()
    {
        return names;
    }
    
    public void setSettings(GameSettings gs)
    {
        soundOn = gs.sO;
        windowMode = gs.wM;
    }
    
    public boolean changeWindowMode()
    {
        if(windowMode != oldWindowMode)
        {
            oldWindowMode = windowMode;
            return true;
        }
        return false;
    }
    
    public static class GameSettings
    {
        boolean wM, sO;
        int[] tankType;
        int[] tankCntrl;
        
        public GameSettings(boolean _wM, boolean _sO, Dimension _fd, int[] _tankType, int[] _tankCntrl)
        {
            //0 = heavy, 1 = range, 2 = mage:: 0 = human, 1 = AI, 2 = NET
            wM = _wM;
            sO = _sO;
            tankType = _tankType;
            tankCntrl = _tankCntrl;
        }
    }
}
