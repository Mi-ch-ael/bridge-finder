import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PaintArea extends JLayeredPane{

    public PaintArea(){
        setBorder(BorderFactory.createLineBorder(Color.black));
        setMinimumSize(new Dimension(400, 400));
    }

}
