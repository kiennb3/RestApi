package com.kiendtph33043;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kiendtph33043.myapp.R;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
TextView tvKQ;
FirebaseFirestore datbase;
Context context=this;
String strKQ="";
ToDo toDo=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tvKQ=findViewById(R.id.tvKQ);
        datbase=FirebaseFirestore.getInstance();//khoi tao
delete();

    }
    void insert(){
        String id= UUID.randomUUID().toString();
        toDo= new ToDo(id,"title 11 ","content 11");//tao doi tuong moi de insert
        datbase.collection("TODO")//truy cap den bang du lieu
                .document(id)//truy cap den dong du lieu
                .set(toDo.convertToHashMap())//dua du lieu vao dong
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "insert thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "insert that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void update(){
        String id=

                "411ffc3f-43a3-44b0-8890-200785f43fe7";//copy id vao day
        toDo= new ToDo(id,"title 11 update","content 11 update");//noi dung can update
        datbase.collection("TODO")//lay bang du lieu
                .document(id)//lay id
                .update(toDo.convertToHashMap())//thuc hien update
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "update thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "update that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void  delete(){
        String id=

                "411ffc3f-43a3-44b0-8890-200785f43fe7";
        datbase.collection("TODO")//truy cap vao bang
                .document(id)//truy cap vao id
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "xoa thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "xoa that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }

        ArrayList<ToDo> select(){
            ArrayList<ToDo> list= new ArrayList<>();
            datbase.collection("TODO")
                    .get()//lay dulieu ve
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                strKQ="";
                                for (QueryDocumentSnapshot doc: task.getResult()){
                                    ToDo t=doc.toObject(ToDo.class);//chuyen du lieu doc duoc sang todo
                                    list.add(t);
                                    strKQ +="id: "+t.getId()+"/n";
                                    strKQ +="title: "+t.getTitle()+"/n";
                                    strKQ +="content: "+t.getContent()+"/ythltrlhrkn";
                                }
                                tvKQ.setText(strKQ);
                            }else {
                                Toast.makeText(context, "select that bai", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    return list;
    }

}