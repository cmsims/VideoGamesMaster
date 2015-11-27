import org.lwjgl.opengl.GL11;
import java.util.LinkedList;
import java.util.Iterator;


public class ProjectileTest extends Scene {

    private LinkedList<Projectile> bullets;
    private Shooter shooter;
    private Target target;

    public ProjectileTest()
    {
        bullets = new LinkedList<>();
        shooter = new Shooter(400, "res/kappa.png", bullets);
        target = new Target(200, "res/target.png");
    }

    public Scene nextScene() { return null; }

    public boolean drawFrame(float delta) {

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



        return true;
    }

}
