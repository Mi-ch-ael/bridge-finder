import javax.swing.*;
import java.awt.*;

public class PaintArea extends JPanel{
    public PaintArea(){
        setBorder(BorderFactory.createLineBorder(Color.black));
        setMinimumSize(new Dimension(400, 400));
    }
}
