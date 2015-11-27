import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;
import org.lwjgl.input.Mouse;

public class MovementTest extends Scene {

    private World w;


    public MovementTest()
    {
        w = new World(10, 5);
    }



    public boolean drawFrame(float delta)
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        w.setDrawEdges(true);

        while (Mouse.next())
        {
            if (Mouse.getEventButtonState())
            {
                if (Mouse.getEventButton() == 0)
                {
                    // left
                    World.Cell c = w.cellAtCoord(Mouse.getEventX(), Display.getHeight()-Mouse.getEventY());
                    w.set(c.getRow(), c.getCol(), new Box());

                }
                if (Mouse.getEventButton() == 1)
                {
                    // right
                }
            }
        }



        w.draw();
        return true;
    }


}