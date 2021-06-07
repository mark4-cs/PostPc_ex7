package com.example.postpc_ex7;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.UUID;

public class SandwichOrder implements Serializable {
    private String id = null, customerName = null, comment = null, status = null;
    private int pickles = -1;
    private boolean hummus = false, tahini = false;

    public static final String WAITING = "waiting";
    public static final String IN_PROGRESS = "in-progress";
    public static final String READY = "ready";
    public static final String DONE = "done";

    SandwichOrder(){
        this.id = UUID.randomUUID().toString();
        this.status = WAITING;
    }

    SandwichOrder(String customerName, int pickles, boolean hummus, boolean tahini, String comment, String status){
        this.id = UUID.randomUUID().toString();
        this.customerName = customerName;
        this.pickles = pickles;
        this.hummus = hummus;
        this.tahini = tahini;
        this.comment = comment;
        this.status = status;
    }

    public void updateDB(){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("orders").document(this.getId()).set(this);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPickles() {
        return pickles;
    }

    public void setPickles(int pickles) {
        this.pickles = pickles;
    }

    public boolean isHummus() {
        return hummus;
    }

    public void setHummus(boolean hummus) {
        this.hummus = hummus;
    }

    public boolean isTahini() {
        return tahini;
    }

    public void setTahini(boolean tahini) {
        this.tahini = tahini;
    }

    public String getId() {
        return id;
    }
}
