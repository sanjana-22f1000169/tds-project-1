package example.studentqa.model;

public class Link {
    private String url;
    private String text;

    public Link() {
    }

    public Link(String url, String text) {
        this.url = url;
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Link{" +
                "url='" + url + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
