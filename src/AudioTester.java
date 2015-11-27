import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.openal.SoundStore;



public class AudioTester
{
    public static void main(String[] args) throws IOException, LWJGLException
    {
        AudioManager aman = AudioManager.getInstance();
        initGL(800,600);

        aman.loadLoop("song", "res/background-sound.ogg");
        aman.loadSample("beep", "res/collision.ogg");
        aman.play("song");


        while (! Display.isCloseRequested()) {

//            update();


            if (Keyboard.isKeyDown(Keyboard.KEY_F))
            {
                aman.play("beep", .3f);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_G))
            {
                aman.play("beep", 1f);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_UP))
            {
                SoundStore.get().setCurrentMusicVolume(SoundStore.get().getCurrentMusicVolume() + .1f);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
            {
                SoundStore.get().setCurrentMusicVolume(SoundStore.get().getCurrentMusicVolume() - .1f);
            }




            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

//            render();

            aman.update();
            Display.update();
            Display.sync(100);
        }

        Display.destroy();
        aman.destroy();
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
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // set viewport to entire window
        GL11.glViewport(0,0,width,height);

        // set up orthographic projectionr
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

}