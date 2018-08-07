package br.com.infracea.fiscalapp.models;

import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.ArrayList;

public class DefaultDialog implements IDialog {

    private String id;
    private String dialogPhoto;
    private String dialogName;
    private ArrayList<? extends IUser> userList;
    private IMessage lastMessage;
    private int unreadCount;

    public DefaultDialog(String id, String dialogPhoto, String dialogName, ArrayList<? extends IUser> userList, IMessage lastMessage, int unreadCount) {
        this.id = id;
        this.dialogPhoto = dialogPhoto;
        this.dialogName = dialogName;
        this.userList = userList;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getDialogPhoto() {
        return dialogPhoto;
    }

    public void setDialogPhoto(String dialogPhoto) {
        this.dialogPhoto = dialogPhoto;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    public void setDialogName(String dialogName) {
        this.dialogName = dialogName;
    }

    @Override
    public ArrayList<? extends IUser> getUsers() {
        return userList;
    }

    public void setUserList(ArrayList<? extends IUser> userList) {
        this.userList = userList;
    }

    @Override
    public IMessage getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(IMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
