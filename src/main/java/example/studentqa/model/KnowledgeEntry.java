package example.studentqa.model;

import java.util.List;

public class KnowledgeEntry {
    private String question;
    private List<String> answers;
    private List<Link> links;

    public KnowledgeEntry() {}

    public KnowledgeEntry(String question, List<String> answers, List<Link> links) {
        this.question = question;
        this.answers = answers;
        this.links = links;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "KnowledgeEntry{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                ", links=" + links +
                '}';
    }
}
