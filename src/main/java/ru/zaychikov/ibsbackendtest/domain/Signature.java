package ru.zaychikov.ibsbackendtest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Signature {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Document document;
    private boolean signature;

    public Signature() {
    }

    public Signature(User user, Document document) {
        this.user = user;
        this.document = document;
        this.signature = false;
    }

    public Signature(int id, User user, Document document, boolean signature) {
        this.id = id;
        this.user = user;
        this.document = document;
        this.signature = signature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public boolean isSignature() {
        return signature;
    }

    public void setSignature(boolean signature) {
        this.signature = signature;
    }
}
