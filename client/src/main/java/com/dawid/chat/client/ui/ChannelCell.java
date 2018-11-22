package com.dawid.chat.client.ui;

import com.dawid.chat.api.channel.ChannelInfo;
import javafx.scene.control.ListCell;

/**
 * Created by Dawid on 21.11.2018 at 22:29.
 */
public class ChannelCell extends ListCell<ChannelInfo> {

    @Override
    protected void updateItem(ChannelInfo item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setText(item.getName());
        }
    }


}
