import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.Iterator;
import java.util.LinkedList;

public class JumperTest extends Scene {


    private Shooter shooter;
    private Target target;

    private Jumper jumper;
    private LinkedList<Platform> platforms;
    private LinkedList<Projectile> bullets;

    public JumperTest()
    {
        jumper = new Jumper("res/kappa.png", bullets);

        platforms = new LinkedList<>();

        platforms.add(new Platform(30, Display.getHeight()-50, 400, 10));
        platforms.add(new Platform(300, Display.getHeight()-175, 20, 10));
        platforms.add(new Platform(250, Display.getHeight()-250, 20, 10));
        platforms.add(new Platform(175, Display.getHeight()-400, 10, 10));


    }


    public boolean drawFrame(float delta)
    {

        Projectile bullet;

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        shooter.update(delta);
        shooter.draw();

        Iterator<Projectile> it= bullets.iterator();
        while (it.hasNext())
        {
            bullet = it.next();

            bullet.update(delta);

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


        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

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

        return true;
    }




}