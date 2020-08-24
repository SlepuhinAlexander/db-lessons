package ifmo.jjd.dao;

public class Book {
    private int id;
    private String title;
    private int pageCount;

    public Book(int id, String title, int pageCount) {
        this.id = id;
        this.title = title;
        this.pageCount = pageCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public String toString() {
        return "Book{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", pageCount=" + pageCount +
               '}';
    }
}
