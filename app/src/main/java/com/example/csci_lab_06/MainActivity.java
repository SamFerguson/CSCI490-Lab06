package com.example.csci_lab_06;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private LabDatabase labDB;
    private static final String DATABASE_NAME = "lab database";
    private Button b;
    private Button c;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = findViewById(R.id.get);
        c = findViewById(R.id.button2);
        et = findViewById(R.id.editText);
        labDB = Room.databaseBuilder(this, LabDatabase.class, DATABASE_NAME)
                .build();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person p = new Person();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Person person =new Person();
                        person.setName(et.getText().toString());

                        labDB.personDao().insertPerson(person);
                    }
                }) .start();
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectAsync selectAsync = new SelectAsync(MainActivity.this, labDB);
                selectAsync.execute();
            }
        });

    }

    static class SelectAsync extends AsyncTask<Void, Void, ArrayList<Person>>{

        private Context context;
        private LabDatabase labDB;
        private static final String DATABASE_NAME = "lab database";

        public SelectAsync(Context context, LabDatabase labDB){
            this.context = context;
            this.labDB = labDB;
        }
        @Override
        protected ArrayList<Person> doInBackground(Void... voids) {

            ArrayList<Person> persons = (ArrayList)labDB.personDao().getAllPersons();
            return persons;
        }

        @Override
        protected void onPostExecute(ArrayList<Person> persons) {
            super.onPostExecute(persons);
            ArrayList<String> personNames = new ArrayList<>();
            for(Person p: persons) {
                personNames.add(p.getName());
            }
            try {
                throw new Exception();
            }catch (Exception e){
                Intent i = new Intent(context, PersonsActivity.class);
                i.putExtra("Persons", personNames);
                context.startActivity(i);
            }
        }
    }

}
