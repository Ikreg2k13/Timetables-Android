package mobile.ikreg.com.mytestapplication.database;

import java.util.List;

public class ExamMemory {

    private long date;
    private String time;
    private long course_id;
    private long room;
    private long length;
    private String notific;
    private String notes;
    private long id;
    private long hasExpired;
    private boolean open;


    public ExamMemory(long date, String time, long course_id, long room, long length, String notific, String notes, long expired, long id) {
        this.date = date;
        this.time = time;
        this.course_id = course_id;
        this.room = room;
        this.length = length;
        this.notific = notific;
        this.notes = notes;
        this.hasExpired = expired;
        this.id = id;
        this.open = false;
    }

    public ExamMemory() { }


    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public long getCourseId() {return course_id; }
    //public void setCourse(int course) { this.course_id = course; }

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

    public long getExpired() { return hasExpired; }
    public void setExpired() { this.hasExpired = 1; }

    //Do not use! Only used for fetching data from database
    public void setExpired(long exp) { this.hasExpired = exp; }

    public void setOpen() { open = true; }
    public void setClosed() { open = false; }
    public boolean isOpen() { return open; };

    @Override
    public String toString() {
        String output = date + ", " + time + ", " + course_id + ", " + hasExpired;

        return output;
    }
}
