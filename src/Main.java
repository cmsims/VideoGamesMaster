

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import org.lwjgl.input.Keyboard;

import org.lwjgl.LWJGLException;
import org.lwjgl.BufferUtils;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.Color;

import java.io.IOException;
import java.nio.FloatBuffer;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.LinkedList;

public class Main
{
    public static final int TARGET_FPS=100;
    public static final int SCR_WIDTH=800;
    public static final int SCR_HEIGHT=600;

    public static void main(String[] args) throws LWJGLException
    {
        initGL(SCR_WIDTH, SCR_HEIGHT);

        //new ProjectileTest().go();

        LinkedList<Entity> entities = new LinkedList<>();

        // for (int i=0; i<10; i++) 
        // {
        //     entities.add(new Bouncer(i*10, i*30));
        // }
        entities.add(new AutoButter(300, "res/1.png"));


        entities.add(new MouseFollower(300, "res/2.png"));

//        entities.add(new AutoButter());





        long lastloop = (Sys.getTime()*1000 / Sys.getTimerResolution());

        while (! Display.isCloseRequested())
        {
            Display.sync(TARGET_FPS);
            long now =(Sys.getTime()*1000 / Sys.getTimerResolution());
            long delta = now - lastloop;
            lastloop = now;

            // UPDATE GAME OBJECTS
            for (Entity e : entities)
            {
                e.update(delta);
            }

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            // DRAW OBJECTS
            for (Entity e : entities)
            {
                e.draw();
            }

            // UPDATE DISPLAY
            Display.update();
        }

        Display.destroy();
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
        // GLU.gluPerspective(90f, 1.333f, 2f, -2f);
        // GL11.glTranslated(0, 0, -500);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
}