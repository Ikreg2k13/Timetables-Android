package mobile.ikreg.com.mytestapplication.database;

public class ExamMemory {

    private String date;
    private String time;
    private String course;
    private long room;
    private long length;
    private String notific;
    private String notes;
    private long id;


    public ExamMemory(String date, String time, String course, long room, long length, String notific, String notes, long id) {
        this.date = date;
        this.time = time;
        this.course = course;
        this.room = room;
        this.length = length;
        this.notific = notific;
        this.notes = notes;
        this.id = id;
    }


    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getCourse() {return course; }
    public void setCourse(String course) { this.course = course; }

    public long getRoom() { return room; }
    public void setRoom(long room) { this.room = room; }

    public long getLength() { return length; }
    public void setLength(long length) { this.length = length; }

    public String getNotific() { return notific; }
    public void setNotific(String notific) { this.notific = notific; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        String output = date + ", " + time + ", " + course;

        return output;
    }
}
