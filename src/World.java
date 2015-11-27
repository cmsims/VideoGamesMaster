import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;
import sun.awt.image.ImageWatched;

import java.util.*;

public class World {

    private Entity[][] grid;
    private int countw;
    private int counth;

    private boolean drawEdges;

    public static class Cell implements Comparable<Cell>
    {
        private int row;
        private int col;
        private World world;

        public Cell(int row, int col, World w)
        {
            this.row = row;
            this.col = col;
            this.world = w;
        }

        public int getRow() { return row; }
        public int getCol() { return col; }

        public String toString()
        {
            return "Cell[row="+row+"; col="+col+"]";
        }


        public int compareTo(Cell other)
        {
            if (row < other.row)
                return -1;
            if (row > other.row)
                return 1;
            if (col < other.col)
                return -1;
            if (col > other.col)
                return 1;
            return 0;
        }


    }



    public World(int countw, int counth)
    {
        this.countw = countw;
        this.counth = counth;
        grid = new Entity[counth][countw];
        drawEdges = false;
    }

    public void clear()
    {
        for (int c=0; c<countw; c++)
        {
            for (int r=0; r<counth; r++)
            {
                grid[r][c]=null;
            }
        }

    }

    public static int randInt(int min, int max)
    {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min)+1)+min;
        return randomNum;
    }

    public void set(int r, int c, Entity e)
    {
        if (r >=0 && r < counth && c >= 0 && c < countw)
        {
            grid[r][c] = e;
        }
    }

    public void setDrawEdges(boolean flag)
    {
        drawEdges = flag;
    }

    public Cell cellAtCoord(float x, float y)
    {
        float dx = (float)Display.getWidth() / countw;
        float dy = (float)Display.getHeight() / counth;

        int col = (int) (x/dx);
        int row = (int) (y/dy);

        return new Cell(row,col,this);

    }


    public void update(float delta)
    {
        for (int c=0; c<countw; c++)
        {
            for (int r=0; r<counth; r++)
            {
                if (grid[r][c] != null)
                {
                    grid[r][c].update(delta);
                }
            }
        }
    }



    public void draw()
    {
        float dx = (float)Display.getWidth() / countw;
        float dy = (float)Display.getHeight() / counth;

        for (int c=0; c<countw; c++)
        {
            for (int r=0; r<counth; r++)
            {
                if (grid[r][c] != null)
                {
                    grid[r][c].drawAt(c*dx, r*dy, dx, dy);
                }

                if (drawEdges)
                {
                    GL11.glColor3f(1,1,1);
                    float x = c*dx;
                    float y = r*dy;

                    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                    GL11.glBegin(GL11.GL_QUADS);

                    GL11.glVertex2f(x,y);
                    GL11.glVertex2f(x+dx,y);
                    GL11.glVertex2f(x+dx,y+dy);
                    GL11.glVertex2f(x,y+dy);

                    GL11.glEnd();
                    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
                }

            }
        }
    }

    public void highlight(Cell c, float r, float g, float b)
    {
        float dx = (float)Display.getWidth() / countw;
        float dy = (float)Display.getHeight() / counth;


        float x = c.getCol()*dx;
        float y = c.getRow()*dy;

        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        GL11.glColor3f(r,g,b);

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x,y);
        GL11.glVertex2f(x+dx,y);
        GL11.glVertex2f(x+dx,y+dy);
        GL11.glVertex2f(x,y+dy);

        GL11.glEnd();


    }

    public List<Cell> findPath(Cell start, Cell end)
    {
        System.out.println("looking for path from " + start + " to " + end);

        class Data
        {
            public double cost;
            public double est;
            public Cell prev;

            public Data(double cost, double est, Cell prev)
            {
                this.cost = cost;
                this.est  = est;
                this.prev = prev;
            }

            public double heuristic()
            {
                return cost + est;
            }

        }

        TreeMap<Cell, Data> costs = new TreeMap<>();
        TreeSet<Cell> open = new TreeSet<Cell>();
        TreeSet<Cell> closed = new TreeSet<Cell>();

        costs.put(start, new Data(0,0,null));

        open.add(start);

        while(open.size()>0)
        {

           double min=9999999;
            Cell current=null;

           for(Cell c:open)
           {
               if(costs.get(c).heuristic()<min)
               {
                   min=costs.get(c).heuristic();
                   current=c;
               }
           }
            if(current.compareTo(end)==0)
            {
                System.out.println("got cha");

                LinkedList prevPath = new LinkedList();

                while(current!=null)
                {
                    prevPath.add(current);
                    current=costs.get(current).prev;

                }
                return prevPath;
            }
            System.out.println(current);
            open.remove(current);
            closed.add(current);
            for (Cell c:getNeighbors(current, end))
            {
                if(closed.contains(c))
                {
                    continue;
                }
                costs.put(c, new Data(costs.get(current).cost+1, Math.abs(end.getRow()-c.getRow()+Math.abs(end.getCol()-c.getCol())), current )); //TODO finish this
                open.add(c);
            }

        }
        System.out.println("no findy");

        return null;
    }



    public List<Cell> getNeighbors(Cell c, Cell include)
    {   
        List<Cell> nbrs = new LinkedList<Cell>();

        int row = c.getRow();
        int col = c.getCol();

        if(Math.abs(include.getRow()-row)+Math.abs(include.getCol()-col)==1)
        {
            nbrs.add(include);
        }

        if (row - 1 >= 0 && grid[row-1][col] == null)
            nbrs.add(new Cell(row-1,col,this));

        if (row + 1 < counth && grid [row+1][col] == null)
            nbrs.add(new Cell(row+1,col,this));

        if (col - 1 >= 0 && grid[row][col-1] == null)
            nbrs.add(new Cell(row,col-1,this));

        if (col +1 < countw && grid[row][col+1] == null)
            nbrs.add(new Cell(row,col+1,this));


        return nbrs;
    }

}