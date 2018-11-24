package com.dawid.chat.client.ui;

import com.dawid.chat.api.message.MessageDto;
import javafx.scene.control.ListCell;

/**
 * Created by Dawid on 24.11.2018 at 17:01.
 */
public class MessageCell extends ListCell<MessageDto> {

    @Override
    protected void updateItem(MessageDto item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
            setText(null);
        } else {
            setText("\r\nAuthor: " + item.getAuthor() + "\r\n"
                    + "Date: " + item.getDateTime() + "\r\n" +
                    "Message: " + item.getText());
        }
    }


}