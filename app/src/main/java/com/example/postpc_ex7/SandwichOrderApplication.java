package com.example.postpc_ex7;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;

public class SandwichOrderApplication extends Application {
    private SandwichOrder sandwichOrder = null;
    private static SandwichOrderApplication instance = null;
    private ListenerRegistration listener = null;


    @Override
    public void onCreate(){
        super.onCreate();
        if (instance == null){instance = this;}
        String id = PreferenceManager.getDefaultSharedPreferences(getInstance()).getString("id", "");
        if (!id.isEmpty()){
            firstStartupGetOrderFromDB(id);
        }
    }

    public SandwichOrder getSandwichOrder() {
        return sandwichOrder;
    }

    public void setSandwichOrder(SandwichOrder sandwichOrder) {
        String old_id = null;
        if (getInstance().sandwichOrder != null){old_id = getInstance().sandwichOrder.getId();}
        getInstance().sandwichOrder = sandwichOrder;
        String new_id = sandwichOrder.getId();
        if (old_id == null | !new_id.equals(old_id)){setNewListenerAndUpdateSP();}
        activitySwitcherManager();
    }

    public void setNewListenerAndUpdateSP(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getInstance());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", getInstance().sandwichOrder.getId());
        editor.apply();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        if (getInstance().listener != null){
            getInstance().listener.remove();
        }
        getInstance().listener = firestore.collection("orders").document(getInstance().getSandwichOrder().getId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null & value != null){
                    getInstance().sandwichOrder = value.toObject(SandwichOrder.class);
                    activitySwitcherManager();
                }
            }
        });

    }

    private void firstStartupGetOrderFromDB(String id){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        Task<DocumentSnapshot> orders = firestore.collection("orders").document(id).get();
        orders.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                getInstance().sandwichOrder = documentSnapshot.toObject(SandwichOrder.class);
                setNewListenerAndUpdateSP();
                activitySwitcherManager();
            }
        });
    }

    public static SandwichOrderApplication getInstance() {
        return instance;
    }

    public void deleteSandwichOrder(){
        String id = instance.getSandwichOrder().getId();
        getInstance().sandwichOrder = null;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getInstance());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", "");
        editor.apply();

        if (getInstance().listener != null){
            getInstance().listener.remove();
        }
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("orders").document(id).delete();
        activitySwitcherManager();
    }

    private void resetFieldsAfterOrderDone(){
        getInstance().sandwichOrder = null;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getInstance());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", "");
        editor.apply();
        if (getInstance().listener != null){
            getInstance().listener.remove();
        }
    }

    private void activitySwitcherManager(){
        Intent intent = null;
        if (getInstance().getSandwichOrder() == null){
            intent = new Intent(getInstance(), newOrderActivity.class);
        }
        else if (getInstance().getSandwichOrder().getStatus().equals(SandwichOrder.WAITING)) {
            intent = new Intent(getInstance(), editOrderActivity.class);
        }
        else if (getInstance().getSandwichOrder().getStatus().equals(SandwichOrder.IN_PROGRESS)){
            intent = new Intent(getInstance(), inProgressOrderActivity.class);
        }
        else if (getInstance().getSandwichOrder().getStatus().equals(SandwichOrder.READY)){
            intent = new Intent(getInstance(), readyOrderActivity.class);
        }
        else if (getInstance().getSandwichOrder().getStatus().equals(SandwichOrder.DONE)){
            resetFieldsAfterOrderDone();
            intent = new Intent(getInstance(), newOrderActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
