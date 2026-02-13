package it.unicam.cs.mpgc.jtime123014.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Represents a generic report document.
 * Independent of the final output format (XML, PDF, HTML).
 */
public class Document {
    private final String title;
    private final List<ReportSection> sections;

    public Document(String title) {
        this.title = title;
        this.sections = new ArrayList<>();
    }

    public void addSection(ReportSection section) {
        this.sections.add(section);
    }

    public String getTitle() {
        return title;
    }

    public List<ReportSection> getSections() {
        return Collections.unmodifiableList(sections);
    }

}
