import org.lwjgl.openal.AL;
import org.lwjgl.opengl.GL11;
import java.util.Random;
import org.lwjgl.opengl.Display;

public class Box extends Entity {

    private float r,g,b;
    private Random rand = new Random();

    public Box() {
        randomize();
    }

    private void randomize() {
        r = rand.nextFloat();
        g = rand.nextFloat();
        b = rand.nextFloat();
    }

    public Box(float r, float g, float b)
    {
        this.r=r;
        this.g=g;
        this.b=b;
    }

    public void drawAt(float x, float y, float dx, float dy) {

        GL11.glColor3f(1, 1, 1);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + dx, y);
        GL11.glVertex2f(x + dx, y + dy);
        GL11.glVertex2f(x, y + dy);

        GL11.glEnd();


    }




}