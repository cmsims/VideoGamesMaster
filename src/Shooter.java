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

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        {
            x += delta/2;
            direction=1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        {
            x -= delta/2;
            direction=-1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP))
        {
            y -= delta/2;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        {
            y += delta/2;
        }


        if (Keyboard.isKeyDown(Keyboard.KEY_E))
        {
            bullets.add(new Projectile((int)x,(int)y,direction));
            AudioManager aman = AudioManager.getInstance();

            aman.play("shoot", 0.10f);
        }


        hitbox.setLocation((int)x, (int)y);
    }


    public void draw() {
        int x = hitbox.getX();
        int y = hitbox.getY();
        int w = hitbox.getWidth();
        int h = hitbox.getHeight();

        GL11.glColor3f(1,1,1);
        // make the loaded texture the active texture for the OpenGL context
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
        // GL11.glColor3f(1,1,1); // interacts with color3f

        GL11.glBegin(GL11.GL_QUADS);


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
