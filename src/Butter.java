

import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;

public class Butter extends Entity {

    private int x = 100;
    private int y = 200;
    private int width=10;


    public void update(float delta)
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        {
            x += delta/2;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        {
            x -= delta/2;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP))
        {
            y -= delta/2;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        {
            y += delta/2;
        }

    }


    public void draw()
    {
        //this draws the box on the screen
        GL11.glColor3f(1,1,0);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x,y);
        GL11.glVertex2f(x+width*3,y);
        GL11.glVertex2f(x+width*3,y+width);
        GL11.glVertex2f(x,y+width);

        GL11.glEnd();

    }



}