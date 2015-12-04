import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Rectangle;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.LinkedList;

public class Shooter extends Entity {

    private LinkedList<Projectile> bullets;
    private int direction;
    private static int height=70;
    private static int width=60;

    private Rectangle box;
    private Texture texture;
    private float width_ratio;
    private float height_ratio;


    public Shooter(int width, String pngpath, LinkedList<Projectile> bullets) {
        super(0,0,100,100);
        this.bullets = bullets;
        this.direction = 1;
        try
        {

            AudioManager aman = AudioManager.getInstance();
            aman.loadSample("shoot", "res/shoot.ogg");
            // load texture as png from res/ directory (this can throw IOException)
            texture = TextureLoader.getTexture("PNG",
                    ResourceLoader.getResourceAsStream(pngpath));

            // textures come in as a power of 2.  use these ratios to
            // calculate texture offsets for sprite based on box size
            width_ratio = (1.0f)*texture.getImageWidth() / texture.getTextureWidth();
            height_ratio = (1.0f)*texture.getImageHeight() / texture.getTextureHeight();

            // create a Rectangle at the origin where height is calculated from
            // texture aspect ratio
            box = new Rectangle(0, 0,
                    width,
                    (int)(width * (float)texture.getImageHeight()/texture.getImageWidth()));

        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.err.println("failed to load image/ogg");
            System.exit(-1);
        }


    }


    public void update(float delta) {

        float x = hitbox.getX();
        float y = hitbox.getY();

//        while (Mouse.next()){
//            if (Mouse.getEventButtonState()) {
//                if (Mouse.getEventButton() == 0) {
//                    System.out.println("Left button pressed");
//                    bullets.add(new Projectile((int)x,(int)y,direction));
//                    AudioManager aman = AudioManager.getInstance();
//                    aman.play("shoot", 0.10f);
//                }
//            }else {
//                if (Mouse.getEventButton() == 0) {
//                    System.out.println("Left button released");
//                }
//            }
//        }

        // fix on boundaries...
        if (x < 0)
        {
            x = 0;

        }

        if (x > Display.getWidth() - width)
        {
            x = Display.getWidth() - width;

        }

        if (y < 0)
        {
            y = 0;

        }

        if (y > Display.getHeight() - height) {
            y = Display.getHeight() - height;
        }


        int mx = Mouse.getX();
        int my = Display.getHeight() - Mouse.getY();

        if (Mouse.isInsideWindow())
        {
            x += (mx - x)*.01*delta;
            y += (my - y)*.01*delta;
        }
        box.setLocation((int) x, (int) y);

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
                    bullets.add(new Projectile((int)x,(int)y,direction));
                    AudioManager aman = AudioManager.getInstance();
                   aman.play("shoot", 0.10f);
                }
            }
            else {
                if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
                    System.out.println("SHOTS FIRED!");
                }
            }
        }

        // stupid thing should let me EFFING SHOOT ONE AT A TIME!
//        while (Mouse.next()) {
//            if (Mouse.getEventButtonState()) {
//                if (Mouse.getEventButton() == 1) {
//                    bullets.add(new Projectile((int)x,(int)y,direction));
//                    AudioManager aman = AudioManager.getInstance();
//                    aman.play("shoot", 0.10f);
//                }
//            }
//            else {
//                if (Mouse.getEventButton() == 1) {
//                    System.out.println("Shots Fired!");
//                }
//            }
//        }


//        //shooter code will allow jumper to shoot
//        if (Mouse.isButtonDown(0))
//        {
//            bullets.add(new Projectile((int)x,(int)y,direction));
//            AudioManager aman = AudioManager.getInstance();
//            aman.play("shoot", 0.10f);
//        }


        hitbox.setLocation((int) x, (int) y);
    }

    public int getX(){return hitbox.getX();}

    public int getY(){return hitbox.getY();}



    public void draw() {
        int x = hitbox.getX();
        int y = hitbox.getY();
        int w = hitbox.getWidth();
        int h = hitbox.getHeight();

        int zoom=800;

        if (Keyboard.isKeyDown(Keyboard.KEY_Q))
        {
            zoom=200;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_E))
        {
            zoom=100;
        }

        GL11.glViewport(0,0, Display.getWidth(),Display.getHeight());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(x-zoom/2, x+zoom/2, y+zoom/2, y-zoom/2, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glColor3f(1,1,1);
        // make the loaded texture the active texture for the OpenGL context
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
        // GL11.glColor3f(1,1,1); // interacts with color3f

        GL11.glBegin(GL11.GL_QUADS);

        x-=w/2;
        y-=h/2;

        // top-left of texture tied to top-left of box
        GL11.glTexCoord2f(0,0);
        GL11.glVertex2f(x, y);

        // top-right
        GL11.glTexCoord2f(width_ratio,0);
        GL11.glVertex2f(x + w, y);

        // bottom-right
        GL11.glTexCoord2f(width_ratio, height_ratio);
        GL11.glVertex2f(x+w,y+h);

        // bottom-left
        GL11.glTexCoord2f(0, height_ratio);
        GL11.glVertex2f(x, y + h);

        GL11.glEnd();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

    }

}
