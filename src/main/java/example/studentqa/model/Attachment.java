package example.studentqa.model;

public class Attachment {
    private String fileName;
    private String base64Content; 

    public Attachment() {
    }

    public Attachment(String fileName, String base64Content) {
        this.fileName = fileName;
        this.base64Content = base64Content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "fileName='" + fileName + '\'' +
                ", base64Content length=" + (base64Content != null ? base64Content.length() : 0) +
                '}';
    }
}