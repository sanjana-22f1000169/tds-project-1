package example.studentqa.model;

import java.util.List;


public class StudentAnswerResponse {
    private String answer;
    private List<Link> links;

    public StudentAnswerResponse() {
    }

    public StudentAnswerResponse(String answer, List<Link> links) {
        this.answer = answer;
        this.links = links;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "StudentAnswerResponse{" +
                "answer='" + answer + '\'' +
                ", links=" + (links != null ? links.size() + " links" : "none") +
                '}';
    }
}