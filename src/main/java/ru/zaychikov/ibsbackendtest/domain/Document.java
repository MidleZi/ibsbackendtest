package ru.zaychikov.ibsbackendtest.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private String number;
    @OneToOne
    private User creator;
    @OneToOne
    private User secondSide;
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    private List<Signature> signatures;

    public Document() {

    }

    public Document(int id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public Document(String name, String number, User creator, User secondSide) {
        this.name = name;
        this.number = number;
        this.creator = creator;
        this.secondSide = secondSide;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Signature> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<Signature> signatures) {
        this.signatures = signatures;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getSecondSide() {
        return secondSide;
    }

    public void setSecondSide(User secondSide) {
        this.secondSide = secondSide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return name.equals(document.name) &&
                number.equals(document.number) &&
                creator.equals(document.creator) &&
                secondSide.equals(document.secondSide);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number, creator, secondSide);
    }
}
