package com.vaadin.tutorial.crm;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;

@CssImport("./styles/styles.css")
public class MessageList extends Div {

    public MessageList() {
        addClassName("message-list");
    }

    public void addMessage(String from, String text) {
        Div line = new Div(new Text(from + ": " + text));
        line.setHeight("25px");
        add(line);
        line.getElement().callJsFunction("scrollIntoView");
    }
}
