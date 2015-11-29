import org.lwjgl.Sys;
import java.io.IOException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.LWJGLException;

import java.util.LinkedList;



public class Game {

    public static void main(String[] args) throws IOException, LWJGLException {
        AudioManager aman = AudioManager.getInstance();
          initGL(800, 800);


            //this is where i am starting to work on my game
        //TODO: lose the gravity, add a texture that will allow me to immulate a scope
        aman.loadLoop("song", "res/background-sound.ogg");
        aman.loadSample("beep", "res/collision.ogg");
        aman.play("song");
        LinkedList<Entity> entities = new LinkedList<>();
        entities.add(new Target(200, "res/target.png"));
        entities.add(new MouseFollower(300, "res/SniperScope.png"));
        new CameraTest().go();

        Display.destroy();

        AudioManager.getInstance().destroy();



    }


    public static void initGL(int width, int height) throws LWJGLException
    {
        // open window of appropriate size
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.create();
        Display.setVSyncEnabled(true);

        // enable 2D textures
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        // set "clear" color to black
        GL11.glClearColor(1, 1, 1, 1);

        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // set viewport to entire window
        GL11.glViewport(0,0,width,height);

        // set up orthographic projectionr
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        // GLU.gluPerspective(90f, 1.333f, 2f, -2f);
        // GL11.glTranslated(0, 0, -500);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
}
