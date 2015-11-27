import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;

import java.util.Iterator;
import java.util.LinkedList;


public class CameraTest extends Scene {

    private BackgroundClass background;
    private Target target;
    private Jumper jumper;
    private LinkedList<Platform> platforms;
    private LinkedList<Projectile> bullets;

    public CameraTest()
    {
        background = new BackgroundClass("res/background.png");
        bullets = new LinkedList<>();
        jumper = new Jumper("res/furSure.png", bullets);
        target = new Target(200, "res/brain.png");
        platforms = new LinkedList<>();
        platforms.add(new Platform(100, Display.getHeight()-100, 600, 10));
        platforms.add(new Platform(100, Display.getHeight()-175, 90, 10));
        platforms.add(new Platform(250, Display.getHeight()-250, 40, 10));
        platforms.add(new Platform(175, Display.getHeight()-400, 20, 10));
        platforms.add(new Platform(300, Display.getHeight()-200, 65, 10));
        platforms.add(new Platform(175, Display.getHeight()-300, 40, 10));
        platforms.add(new Platform(75, Display.getHeight()-410, 89, 10));
        platforms.add(new Platform(300, Display.getHeight()-300, 65, 10));
        platforms.add(new Platform(300, Display.getHeight()-500, 55, 10));
        platforms.add(new Platform(310, Display.getHeight()-600, 25, 10));
        platforms.add(new Platform(60, Display.getHeight()-550, 30, 10));


    }


    private int w=200;

    public boolean drawFrame(float delta)
    {
        background.draw();

        // draw the main screen
        GL11.glViewport(0,0,Display.getWidth(),Display.getHeight());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        Projectile bullet;

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        //jumper.update(delta);
        //jumper.draw();

        Iterator<Projectile> it= bullets.iterator();
        while (it.hasNext())
        {
            bullet = it.next();

            bullet.update(delta);

            for (Platform p : platforms)
            {
                bullet.testCollision(p);
            }

            for (Projectile p : bullets)
            {
                if (p != bullet)
                {
                    bullet.testCollision(p);
                }
            }

            if (! bullet.isActive())
            {
                System.out.println("removing inactive projectile");
                it.remove();
            }
            else
            {
                bullet.draw();
                target.testCollision(bullet);
            }
        }

        target.update(delta);
        target.draw();

       // GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        if (Keyboard.isKeyDown(Keyboard.KEY_A))
        {
            w ++;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_B))
        {
            w --;
        }




        jumper.update(delta);

        for (Platform p : platforms)
        {
            p.update(delta);
            p.draw();
        }

        for (Platform p : platforms)
        {
            jumper.testCollision(p);
        }

        jumper.draw();


        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        // draw the minimap
        GL11.glViewport(Display.getWidth()-200, Display.getHeight()-200, 200, 200);

        GL11.glColor3f(0,0,0);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(0, 0);
        GL11.glVertex2f(Display.getWidth(), 0);
        GL11.glVertex2f(Display.getWidth(), Display.getHeight());
        GL11.glVertex2f(0, Display.getHeight());
        GL11.glEnd();



        for (Platform p : platforms)
        {
            p.draw();
        }
        jumper.draw();

        return true;
    }




}