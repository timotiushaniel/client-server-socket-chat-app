public class Option3 {
    private String fname;
    private String lname;
    private String title;

    public Option3(String fname, String lname, String title) {
        this.fname = fname;
        this.lname = lname;
        this.title = title;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("%10s%39s%45s",fname,lname,title);
    }
}
