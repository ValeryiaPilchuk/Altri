package com.example.altri.Fragment;

import static com.parse.Parse.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.altri.MainMenuActivity;
import com.example.altri.R;
import com.example.altri.SchedulerMenuActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class MessageFragment extends Fragment {

    private TextView etMotivateMessage;
    public MessageFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.popup_message, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> phrases = new ArrayList<String>();
        phrases.add("You're Doing \n Great");
        phrases.add("Good Job!");
        phrases.add("Another Task \n Completed!!");
        phrases.add("Keep Going, \n You're Doing Great!");
        phrases.add("Good Job! \n You Completed \n Another Task!");
        phrases.add("Another Task \n Down! \n You're on a streak");
        phrases.add("Good Job! \n Believe in yourself because you can do it!");

        Collections.shuffle(phrases);

        Intent intent = new Intent(getApplicationContext(), SchedulerMenuActivity.class);

        etMotivateMessage = view.findViewById(R.id.etMotivateMessage);
        etMotivateMessage.setText(phrases.get(0));
        etMotivateMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

}
