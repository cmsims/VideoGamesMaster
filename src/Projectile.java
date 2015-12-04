import org.lwjgl.util.Rectangle;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.Project;
import org.lwjgl.util.vector.Vector2f;


public class Projectile extends Entity {

        private static final int WIDTH=10;
        private static final int HEIGHT=5;
        //private static final float SPEED=1f;

        //private Vector2f velocity;
        //private float mass;

        // initial x,y.  direction should be 1 to go right, -1 to go left
        public Projectile(int x, int y, int direction) {
                super(x,y,WIDTH,HEIGHT);
                //velocity = new Vector2f(direction*.3f, -.002f);

        }

        public void onCollision(Entity other)
        {
                if (other instanceof Platform)
                {

                        Rectangle overlap = intersection(other);
                        float x =hitbox.getX();
                        float y =hitbox.getY();
                        double width = overlap.getWidth();
                        double height = overlap.getHeight();

                        if (height > width)
                        {
                                // horizontal
                                x-=width;
                        }
                        else
                        {
                                // vertical collision
                                y -= height;

                        }

                        hitbox.setLocation((int)x,(int)y);
                }

        }

        public void update(float delta) {

                float x = hitbox.getX();
                float y = hitbox.getY();

                // apply gravity
//
//                Vector2f.add(velocity,
//                        (Vector2f)new Vector2f(0, .0001f).scale(delta),
//                        velocity);


//                x += velocity.getX()*delta;
//                y += velocity.getY()*delta;


                if (x < 0 - hitbox.getWidth() || x > Display.getWidth())
                {
                        this.deactivate();
                }
                if (! this.isActive())
                {
                        this.destroy();
                }


                hitbox.setLocation((int)x,(int)y);
        }

        public void draw() {

                int x = hitbox.getX();
                int y = hitbox.getY();
                int w = hitbox.getWidth();
                int h = hitbox.getHeight();

                GL11.glColor3f(0,0,0);
                GL11.glBegin(GL11.GL_QUADS);

                GL11.glVertex2f(x,y);
                GL11.glVertex2f(x+w,y);
                GL11.glVertex2f(x+w,y+h);
                GL11.glVertex2f(x,y+h);

                GL11.glEnd();

        }


}