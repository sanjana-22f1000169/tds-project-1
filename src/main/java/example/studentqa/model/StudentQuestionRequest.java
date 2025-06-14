package example.studentqa.model;

import java.util.List;

public class StudentQuestionRequest {
    private String question;
    private List<Attachment> attachments; 
    private String image; 

    public StudentQuestionRequest() {
    }

    public StudentQuestionRequest(String question, List<Attachment> attachments, String image) {
        this.question = question;
        this.attachments = attachments;
        this.image = image;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "StudentQuestionRequest{" +
                "question='" + question + '\'' +
                ", attachments=" + (attachments != null ? attachments.size() + " files" : "none") +
                ", imagePresent=" + (image != null && !image.isEmpty()) +
                '}';
    }
}