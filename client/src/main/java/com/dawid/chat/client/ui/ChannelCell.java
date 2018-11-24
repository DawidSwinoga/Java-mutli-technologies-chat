package com.dawid.chat.client.ui;

import com.dawid.chat.client.Channel;
import javafx.scene.control.ListCell;

/**
 * Created by Dawid on 21.11.2018 at 22:29.
 */
public class ChannelCell extends ListCell<Channel> {

    @Override
    protected void updateItem(Channel item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
            setText(null);
        } else {
            setText(item.getName());
        }
    }

}
