import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;

import java.awt.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.Point;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;

import javax.swing.plaf.nimbus.State;
import java.util.LinkedList;



public class Game {


    public static void main(String[] args) throws IOException, LWJGLException {
        //AudioManager aman = AudioManager.getInstance();
          initGL(1400, 900);

        //this is where i am starting to work on my game
        //aman.loadLoop("song", "res/background-sound.ogg");
        //aman.loadSample("beep", "res/collision.ogg");2
        //aman.play("song");
        LinkedList<Entity> entities = new LinkedList<>();
        entities.add(new Target(50, "res/kappa.png"));
        entities.add(new MouseFollower(300, "res/SniperScope.png"));
        Menu gameMenu = new Menu();
        gameMenu.addItem("A Star Game", new TLDTest());
        gameMenu.addItem("Shooting Game", new ShooterTest());
        //gameMenu.addItem("Camera Tester!", new CameraTest());
        //gameMenu.addItem("Kappa Shoot 1000", new ProjectileTest());
        gameMenu.addSpecial("Exit", Menu.DO_EXIT);
        Scene currScene = gameMenu;
        if (Keyboard.isKeyDown(Keyboard.KEY_F5)){

        }
        while ( currScene.go()  )
        {
            // if nextScene() returns null (the default) reload the menu
            currScene = currScene.nextScene();
            if (currScene == null)
            {
                currScene = gameMenu;
            }
        }
        System.out.println("Changing Scene: " + currScene);
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
        GL11.glClearColor(0, 0, 0, 1);
        //enable alpha blending
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
        // initialise the font
    }
}
