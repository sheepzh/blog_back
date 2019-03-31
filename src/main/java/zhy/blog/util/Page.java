package zhy.blog.util;

/**
 * Model of page
 *
 * @author zhyyy
 * @since 20190307
 */
public class Page {
    private int total;
    private int currentPage;
    private int numberPerPage;

    public Page() {
        this(0, 10, 1);
    }

    public Page(int total) {
        this(total, 10, 1);
    }

    public Page(int total, int numberPerPage) {
        this(total, numberPerPage, 1);
    }

    public Page(int total, int numberPerPage, int currentPage) {
        this.total = total;
        this.numberPerPage = numberPerPage;
        this.currentPage = currentPage;
    }

    public int getStartNum() {
        return Math.min((currentPage - 1) * numberPerPage, total);
    }

    public int getEndNum() {
        return Math.min(currentPage * numberPerPage, total);
    }
}
