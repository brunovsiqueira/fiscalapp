package br.com.infracea.fiscalapp.models;

import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Calendar;
import java.util.Date;

public class Message implements IMessage {

    private String id;
    private String text;
    private Author user;
    private Calendar createdDate;
    private Date createdAt = new Date();

    public Message(String id, String text, Author user, Calendar createdDate) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.createdDate = createdDate;
        this.createdAt.setTime(createdDate.getTimeInMillis());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Author getUser() {
        return user;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }
}
