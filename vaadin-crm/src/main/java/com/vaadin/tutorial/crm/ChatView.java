package com.vaadin.tutorial.crm;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.configuration.BotConfiguration;

@Route("")
@CssImport("./styles/styles.css")
public class ChatView extends VerticalLayout {

    private String userName;
    private final MessageList messageList = new MessageList();
    private final TextField message = new TextField();
    private Bot bot;
    private Chat chatSession;


    public ChatView() {
        addClassName("chat-view");
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        H1 headerChatbot = new H1();
        add(headerChatbot);
        askUserName();
    }

    private void askUserName(){
        VerticalLayout layoutUserName =  new VerticalLayout();
        TextField userNameField = new TextField();
        Button startButton = new Button("ENTER");

        layoutUserName.addClassName("layout-username");
        layoutUserName.setAlignItems(Alignment.CENTER);
        layoutUserName.add(userNameField, startButton);
        layoutUserName.setMaxWidth("500px");

        startButton.addClassName("start-button");
        userNameField.addClassName("username-field");
        userNameField.setPlaceholder("Please enter your name");
        userNameField.setWidth("210px");

        startButton.addClassName("start-button");
        startButton.addClickShortcut(Key.ENTER);
        startButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        startButton.addClickListener(click -> {
            userName = userNameField.getValue();
            remove(layoutUserName);
            createBot();
            setChatSession();
        });

        add(layoutUserName);
    }

    private void setChatSession(){
        messageList.addMessage("ModelV2-2316 -- Booting", "Hold please...");
        messageList.addMessage("ModelV2-2316 -- System online", " Welcome Human!");

        message.addClassName("input-field");
        message.setPlaceholder("Please enter a message...");
        message.addKeyDownListener(Key.ENTER, keyDownEvent -> {
            String text = message.getValue();
            messageList.addMessage(this.userName, text);
            message.clear();
            String answer = chatSession.multisentenceRespond(text);
            messageList.addMessage("ModelV2-2316", answer);
        });

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(message);
        layout.setWidth("100%");
        messageList.setWidth("100%");
        expand(message, messageList);
        add(messageList, layout);
    }

    private void createBot(){
        bot = new Bot(BotConfiguration.builder()
                .name("MoonieBanoonie")
                .path("src/main/resources")
                .build());

         chatSession = new Chat(bot);
    }
}
