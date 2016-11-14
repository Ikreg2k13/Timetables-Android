package mobile.ikreg.com.mytestapplication.database;

public class CourseMemory {

    private String name;
    private long color;
    private long id;

    public CourseMemory(String name, long color, long id) {
        this.name = name;
        this.color = color;
        this.id = id;
    }

    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }

    public void setColor(int color) { this.color = color; }
    public long getColor() { return this.color; }

    public long getId() { return this.id; }

    @Override
    public String toString() {
        String output = name + ", " + color + ", " + id;

        return output;
    }
}
