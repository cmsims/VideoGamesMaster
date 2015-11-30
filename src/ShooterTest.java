import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Various on 11/29/2015.
 */
public class ShooterTest extends Scene{

        private BackgroundClass background;
        private Target target;
        private Jumper jumper;
        private LinkedList<Platform> platforms;
        private LinkedList<Projectile> bullets;

        public ShooterTest()
        {
            background = new BackgroundClass("res/background.png");
            bullets = new LinkedList<>();
            jumper = new Jumper("res/SniperScope.png", bullets);
            target = new Target(400, "res/Stick_figure.png");
            platforms = new LinkedList<>();
            platforms.add(new Platform(100, Display.getHeight()-50, 1000, 20));
            platforms.add(new Platform(100, Display.getHeight()-175, 90, 140));
            platforms.add(new Platform(250, Display.getHeight()-250, 90, 200));
            platforms.add(new Platform(600, Display.getHeight()-230, 90, 200));
            platforms.add(new Platform(775, Display.getHeight()-200, 65, 150));
            platforms.add(new Platform(450, Display.getHeight()-400, 70, 350));
//        platforms.add(new Platform(75, Display.getHeight()-410, 89, 10));
//        platforms.add(new Platform(300, Display.getHeight()-300, 65, 10));
//        platforms.add(new Platform(300, Display.getHeight()-500, 55, 10));
//        platforms.add(new Platform(310, Display.getHeight()-600, 25, 10));
//        platforms.add(new Platform(60, Display.getHeight()-550, 30, 10));


        }


        private int w=200;

        public boolean drawFrame(float delta)
        {
            //TODO: make this background work
            //why doesn't this work
            background.draw();

            Projectile bullet;

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            jumper.update(delta);
            jumper.draw();

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


            //this is different from the camera test becasue of the drawframe method?
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);

            for (Platform p : platforms)
            {
                jumper.testCollision(p);
            }

            jumper.draw();

            return true;
        }






    }

