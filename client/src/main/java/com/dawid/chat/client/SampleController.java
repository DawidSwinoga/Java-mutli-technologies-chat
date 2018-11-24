package com.dawid.chat.client;

import com.dawid.chat.api.ChatService;
import com.dawid.chat.api.channel.ChannelAddedEvent;
import com.dawid.chat.api.channel.ChannelAlreadyExistException;
import com.dawid.chat.api.channel.ChannelInfo;
import com.dawid.chat.api.channel.ChannelRemovedEvent;
import com.dawid.chat.api.message.MessageDto;
import com.dawid.chat.api.message.MessageEvent;
import com.dawid.chat.api.message.MessageToSend;
import com.dawid.chat.api.user.UserAlreadyLoggedInException;
import com.dawid.chat.api.user.UserDto;
import com.dawid.chat.api.user.UserJoinToChatEvent;
import com.dawid.chat.api.user.UserLeaveChatEvent;
import com.dawid.chat.api.user.credential.Credential;
import com.dawid.chat.client.ui.ChannelCell;
import com.dawid.chat.client.ui.MessageCell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static com.dawid.chat.client.ApplicationContextProvider.getBeanByName;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.stream.Collectors.toList;
import static javafx.application.Platform.runLater;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Dawid on 21.11.2018 at 19:48.
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class SampleController {
    @FXML
    private ListView<MessageDto> messagesListView;
    @FXML
    private ListView<Channel> channelsListView;
    @FXML
    private ListView<UserDto> usersListView;
    @FXML
    private ComboBox<CommunicationMethod> communicationMethodComboBox;
    @FXML
    private Button loginButton;
    @FXML
    private TextField username;
    @FXML
    private TextField channelNameTextField;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private Button removeChannelButton;

    private ChatService chatService;
    private Channel selectedChannel;
    private Credential credential;
    private final ExecutorService executorService = newCachedThreadPool();

    @FXML
    private void initialize() {
        initCommunicationMethodComboBox();
        initChannelListView();
        initMessageListView();
    }

    private void initMessageListView() {
        messagesListView.setItems(observableArrayList());
        messagesListView.setCellFactory(param -> new MessageCell());
        messagesListView.setEditable(false);
    }

    @EventListener
    public void channelAdded(ChannelAddedEvent channelAddedEvent) {
        addChannel(channelAddedEvent);
    }

    @EventListener
    public void messageListener(MessageEvent messageEvent) {
        runLater(() -> onMessage(messageEvent.getChannelId(), messageEvent));
    }

    private void onMessage(String channelId, MessageDto messageDto) {
        if (selectedChannel != null && selectedChannel.isTheSameId(channelId)) {
            messagesListView.getItems().add(messageDto);
        }
        channelsListView.getItems()
                .stream()
                .filter(it -> it.isTheSameId(channelId))
                .findFirst()
                .ifPresent(it -> it.addMessage(messageDto));
    }

    @EventListener
    public void channelRemoved(ChannelRemovedEvent channelRemovedEvent) {
        Optional<Channel> channel = channelsListView.getItems()
                .stream()
                .filter(it -> it.isTheSameId(channelRemovedEvent.getChannelId()))
                .findFirst();
        channel.ifPresent(it -> runLater(() -> removeChannel(it)));
    }

    @EventListener
    public void userJoin(UserJoinToChatEvent userJoinToChatEvent) {
    }

    @EventListener
    public void userLeave(UserLeaveChatEvent userLeaveChatEvent) {
    }

    @FXML
    private void onRemoveButtonClick() {
        chatService.removeChannel(selectedChannel.getChannelId(), credential);
        removeChannel(selectedChannel);
    }

    private void removeChannel(Channel channel) {
        channelsListView.getItems().remove(channel);
        selectedChannel = channelsListView.getSelectionModel().getSelectedItem();
        ofNullable(selectedChannel).ifPresent(this::onChannelChange);
    }

    private void initChannelListView() {
        channelsListView.setCellFactory(param -> new ChannelCell());
        channelsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        channelsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ofNullable(newValue).ifPresent(this::onChannelChange);
        });
    }

    private void onChannelChange(Channel newValue) {
        selectedChannel = newValue;
        if (!newValue.isSubscribed(credential)) {
            messagesListView.getItems().clear();
            executorService.submit(() -> requestChannel(newValue.getChannelId()));
        } else {
            messagesListView.setItems(observableArrayList(newValue.getMessages()));
        }

        removeChannelButton.setVisible(newValue.isAdministrator(credential));
    }

    private void requestChannel(String channelId) {
        chatService.joinToChannel(channelId, credential);
        List<MessageDto> channelMessage = chatService.getChannelMessage(channelId, credential);
        runLater(() -> channelMessage.forEach(messageEvent -> onMessage(channelId, messageEvent)));
    }

    @FXML
    private void channelListClicked() {
        channelsListView.getSelectionModel();
    }

    private void initCommunicationMethodComboBox() {
        communicationMethodComboBox.setItems(observableArrayList(CommunicationMethod.values()));
        communicationMethodComboBox.getSelectionModel().selectFirst();
        onCommunicationMethodChanged();
    }

    @FXML
    private void onCommunicationMethodChanged() {
        chatService = getBeanByName(communicationMethodComboBox.getSelectionModel().getSelectedItem().beanName);
    }

    @FXML
    private void onLoginButtonClicked() {
        try {
            credential = chatService.loginUser(username.getText(), ApiConfiguration.QUEUE_DESTINATION_NAME);
            loginButton.setVisible(false);
            username.setDisable(true);
            executorService.submit(this::showChannels);
        } catch (UserAlreadyLoggedInException e) {
            showDialogError("The selected username already exist");
        }
    }

    @FXML
    private void onAddChannelButtonClicked() {
        executorService.submit(this::createChannel);
    }

    private void createChannel() {
        try {
            chatService.createChannel(channelNameTextField.getText(), credential);
        } catch (ChannelAlreadyExistException e) {
            runLater(() -> showDialogError("Channel already exist"));
        }
    }

    @FXML
    private void onMessageSendClicked() {
        executorService.submit(() -> sendMessage(messageTextArea.getText() + ""));

    }

    private void sendMessage(String text) {
        ofNullable(selectedChannel)
                .ifPresent(it -> chatService.sendMessage(new MessageToSend(it.getChannelId(), text, credential)));
        runLater(() -> messageTextArea.clear());
    }

    private void addChannel(ChannelInfo channel) {
        runLater(() -> channelsListView.getItems().add(new Channel(channel)));
    }

    private void showChannels() {
        ObservableList<Channel> channels = observableArrayList(chatService.getAllChannels(credential)
                .stream()
                .map(Channel::new)
                .collect(toList()));
        channelsListView.setItems(channels);
    }

    private void showDialogError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chat error");
        alert.setHeaderText(text);
        alert.showAndWait();
    }

    public void onClose() {
        ofNullable(credential).ifPresent(chatService::logout);
    }
}
