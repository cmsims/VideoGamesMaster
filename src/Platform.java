import org.lwjgl.opengl.GL11;

public class Platform extends Entity {

    public Platform(int x, int y, int width, int height)
    {
        super(x,y,width,height);
    }

    public void draw() {

        int x = hitbox.getX();
        int y = hitbox.getY();
        int w = hitbox.getWidth();
        int h = hitbox.getHeight();

        GL11.glColor3f(0,0,1);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x,y);
        GL11.glVertex2f(x+w,y);
        GL11.glVertex2f(x+w,y+h);
        GL11.glVertex2f(x,y+h);

        GL11.glEnd();

    }


}