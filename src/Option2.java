public class Option2 {
    private String title;
    private String release_year;

    public Option2(String title, String release_year) {
        this.title = title;
        this.release_year = release_year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_year() {
        return release_year;
    }

    public void setRelease_year(String release_year) {
        this.release_year = release_year;
    }

    public String toString() {
        return String.format("%-35s %-55s", title, release_year);
    }
}
