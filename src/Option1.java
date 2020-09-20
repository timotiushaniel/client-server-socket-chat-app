public class Option1
{
    private String title;
    private String id_category;
    private String nama_category;

    public Option1(String title, String id_category, String nama_category) {
        this.title = title;
        this.id_category = id_category;
        this.nama_category = nama_category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getNama_category() {
        return nama_category;
    }

    public void setNama_category(String nama_category) {
        this.nama_category = nama_category;
    }

    @Override
    public String toString() {
        return String.format("%-35s %-55s", title, nama_category);
    }
}
