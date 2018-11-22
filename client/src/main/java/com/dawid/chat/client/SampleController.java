package com.dawid.chat.client;

import com.dawid.chat.api.ChatService;
import com.dawid.chat.api.channel.ChannelAlreadyExistException;
import com.dawid.chat.api.channel.ChannelInfo;
import com.dawid.chat.api.message.MessageDto;
import com.dawid.chat.api.user.UserAlreadyLoggedInException;
import com.dawid.chat.api.user.UserDto;
import com.dawid.chat.api.user.credential.Credential;
import com.dawid.chat.client.ui.ChannelCell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.dawid.chat.client.ApplicationContextProvider.getBeanByName;
import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.stream.Collectors.toList;
import static javafx.application.Platform.runLater;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Dawid on 21.11.2018 at 19:48.
 */
@Controller
@Slf4j
public class SampleController {
    @FXML
    private ListView<MessageDto> messagesListView;
    @FXML
    private ListView<ChannelInfo> channelsListView;
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

    private ChatService chatService;
    private Credential credential;
    private final ExecutorService executorService = newCachedThreadPool();

    @FXML
    private void initialize() {
        initCommunicationMethodComboBox();
        initChannelListView();
    }

    private void initChannelListView() {
        channelsListView.setCellFactory(param -> new ChannelCell());
        channelsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        channelsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            channelsListView.getFocusModel().focus(channelsListView.getSelectionModel().getSelectedIndex());
        });
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
            credential = chatService.loginUser(username.getText());
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

    private void addChannel(ChannelInfo channel) {
        ObservableList<ChannelInfo> items = channelsListView.getItems();
        items.add(channel);
        List<ChannelInfo> channels = items.stream().distinct().collect(toList());
        items.setAll(channels);
    }

    private void showChannels() {
        channelsListView.setItems(observableArrayList(chatService.getAllChannels(credential)));
    }

    private void showDialogError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chat error");
        alert.setHeaderText(text);
        alert.showAndWait();
    }
}
