
// import java.awt.Rectangle;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import java.io.IOException;
import org.lwjgl.opengl.GL11;


public class PictureTest extends Entity {

    private Rectangle box;
    private Texture texture;
    private float width_ratio;
    private float height_ratio;


    public PictureTest(int width, String pngpath)
    {

        try
        {

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
            System.err.println("failed to load image");
            System.exit(-1);
        }
    }


    public void draw()
    {
        float x = (float)box.getX();
        float y = (float)box.getY();
        float width = (float)box.getWidth();
        float height = (float)box.getHeight();


        // going to send a series of quad vertices...



        // if(debug)
        // GL11.glColor3f(1.0f, 0.0f, 0.0f);
        // GL11.glBegin(GL11.GL_QUADS);
        // GL11.glVertex2f(x,y);
        // GL11.glVertex2f(x+width, y);
        // GL11.glVertex2f(x+width,y+height);
        // GL11.glVertex2f(x,y+height);
        // GL11.glEnd();


        // make the loaded texture the active texture for the OpenGL context
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
        // GL11.glColor3f(1,1,1); // interacts with color3f
        GL11.glColor3f(1,1, 0); // interacts with color3f

        GL11.glBegin(GL11.GL_QUADS);


        // top-left of texture tied to top-left of box        
        GL11.glTexCoord2f(0,0);
        GL11.glVertex2f(x,y);

        // top-right 
        GL11.glTexCoord2f(width_ratio,0);
        GL11.glVertex2f(x+width, y);

        // bottom-right
        GL11.glTexCoord2f(width_ratio, height_ratio);
        GL11.glVertex2f(x+width,y+height);

        // bottom-left
        GL11.glTexCoord2f(0,height_ratio);
        GL11.glVertex2f(x,y+height);


        GL11.glEnd();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

}