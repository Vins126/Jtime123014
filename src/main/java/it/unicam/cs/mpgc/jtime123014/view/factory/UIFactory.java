package it.unicam.cs.mpgc.jtime123014.view.factory;

import it.unicam.cs.mpgc.jtime123014.util.UIConstants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Factory class for creating standard UI components.
 */
public class UIFactory {

    /**
     * Costruttore privato per prevenire l'istanziazione di questa classe di
     * utilità.
     */
    private UIFactory() {
        throw new UnsupportedOperationException("Questa è una classe di utilità e non può essere istanziata.");
    }

    /**
     * Creates a standard action button (icon-only style).
     *
     * @param text       the button text (or icon character).
     * @param styleClass the CSS class to apply.
     * @param handler    the action handler.
     * @return the configured Button.
     */
    public static Button createActionButton(String text, String styleClass, EventHandler<ActionEvent> handler) {
        Button btn = new Button(text);
        btn.getStyleClass().add(styleClass);
        btn.setOnAction(handler);
        return btn;
    }

    /**
     * Creates a standard button with text.
     * 
     * @param text    the button text.
     * @param handler the action handler.
     * @return the configured Button.
     */
    public static Button createButton(String text, EventHandler<ActionEvent> handler) {
        Button btn = new Button(text);
        btn.getStyleClass().add("button");
        btn.setOnAction(handler);
        return btn;
    }

    /**
     * Creates a standard GridPane for forms.
     * 
     * @return a configured GridPane.
     */
    public static Button createSuccessButton(EventHandler<ActionEvent> handler) {
        return createActionButton("✓", "btn-success", handler);
    }

    public static Button createEditButton(EventHandler<ActionEvent> handler) {
        return createActionButton("⚙", "btn-edit", handler);
    }

    public static Button createEditLabelButton(String text, EventHandler<ActionEvent> handler) {
        return createActionButton(text, "btn-edit", handler);
    }

    public static Button createDeleteButton(EventHandler<ActionEvent> handler) {
        return createActionButton("🗑", "btn-delete", handler);
    }

    public static Button createDeleteLabelButton(String text, EventHandler<ActionEvent> handler) {
        return createActionButton(text, "btn-delete", handler);
    }

    public static Button createOpenButton(String text, EventHandler<ActionEvent> handler) {
        return createActionButton(text, "btn-open", handler);
    }

    /**
     * Creates a standard GridPane for forms.
     * 
     * @return a configured GridPane.
     */
    public static GridPane createFormGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        return grid;
    }
}
