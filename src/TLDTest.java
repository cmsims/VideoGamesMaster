import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class TLDTest extends Scene {
    private World w;
    private World.Cell start;
    private World.Cell end;
    private List<World.Cell> path;

    public TLDTest() {
        w = new World(10, 10);

        for (int i = 0; i < 25; i++)
        {
            w.set(World.randInt(0, 9), World.randInt(0, 9), new Box());
        }

        w.setDrawEdges(true);
    }


    public boolean drawFrame(float delta) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        w.setDrawEdges(true);

        while (Mouse.next()) {
            if (Mouse.getEventButtonState()) {
                if (Mouse.getEventButton() == 0) {
                    if (end != null) {
                        w.set(end.getRow(), end.getCol(), null);
                    }
                    // make this the end point
                    end = w.cellAtCoord(Mouse.getEventX(), Display.getHeight() - Mouse.getEventY());
                    w.set(end.getRow(), end.getCol(), new Box(1,1,1));
                    path=null;
                }
                if (Mouse.getEventButton() == 1) {
                    if (start != null) {
                        // make this the start point
                        w.set(start.getRow(), start.getCol(), null);
                    }
                    //make this the start point
                    start = w.cellAtCoord(Mouse.getEventX(), Display.getHeight() - Mouse.getEventY());
                    w.set(start.getRow(), start.getCol(), new Box(1,1,1));
                    //i want to clear the previous cell after i click again
                    path=null;
                }
            }
        }
        if(start!=null&&end!=null&&path==null)
        {
           path=w.findPath(start, end);
        }

        w.draw();
        if(path!=null)
        {
            for(World.Cell c: path)
            {
                w.highlight(c,1, 1, 1);
            }
        }
        return true;
    }
}