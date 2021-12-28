package si.fri.rso.models;


import java.util.ArrayList;
import java.util.List;

public class Cart {

    private String id;
    //Book ids
    private List<Book> books;
    private String owner;
    private String address;

    public Cart() {
        books = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public String getOwner() {
        return owner;
    }

    public String getAddress() {
        return address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
