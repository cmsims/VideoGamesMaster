import org.lwjgl.util.Rectangle;


public abstract class Entity {

    protected Rectangle hitbox;
    private boolean active;

    public Entity() {
        hitbox = new Rectangle(); // empty rectangle
        active = true;
    }

    public Entity(int x, int y, int w, int h) {
        hitbox = new Rectangle(x,y,w,h); // non-empty rectangle
        active = true;
    }

    public void init()
    {
    }

    public void destroy()
    {
    }

    public void update(float delta)
    {
    }

    public void draw()
    {
    }

    public void drawAt(float x, float y, float dx, float dy)
    {

    }


    public boolean intersects(Entity other)
    {
        return hitbox.intersects(other.hitbox);
    }

    public Rectangle intersection(Entity other)
    {
        Rectangle rval = new Rectangle();
        return hitbox.intersection(other.hitbox, rval);
    }


    public boolean testCollision(Entity other)
    {
        if (hitbox.intersects(other.hitbox))
        {
            onCollision(other);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void onCollision(Entity other)
    {
    }

    public boolean isActive()
    {
        return active;
    }

    protected void deactivate()
    {
        active = false;
    }

}