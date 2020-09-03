package com.unfpa.safepal.chatmodule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pixplicity.easyprefs.library.Prefs;
import com.unfpa.safepal.R;
import com.unfpa.safepal.adapters.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.unfpa.safepal.Utils.Constants.ANONYMOUS_USER_NAME;
import static com.unfpa.safepal.Utils.Constants.MAC_ADDRESS;
import static com.unfpa.safepal.Utils.Utilities.getMacAddress;
import static com.unfpa.safepal.Utils.Utilities.getRandomString;

public class ChatActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private static final String USER = "user";
    private static final String CHAT = "chat";
    private EditText inputMessageEditView;
    private ImageButton sendButton;
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private DocumentReference userDocument;
    private ChatUser chatUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chatUser = new ChatUser(
                Prefs.getString(ANONYMOUS_USER_NAME, "User" + getRandomString()),
                Prefs.getString(MAC_ADDRESS, getMacAddress(this)));

        FirebaseApp.initializeApp(this);
        inputMessageEditView = findViewById(R.id.input_messsage);
        sendButton = findViewById(R.id.send);
        recyclerView = findViewById(R.id.chats_list);

        sendButton.setOnClickListener(v -> {
            String message = inputMessageEditView.getText().toString();
            Timber.d("onClick: %s", message);
            saveChatDocumentData(message);
            inputMessageEditView.setText("");
        });

        db = FirebaseFirestore.getInstance();
        saveUserDocumentData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        triggerNewMessageListener();
    }

    private void triggerNewMessageListener() {
        try {
            userDocument.collection(CHAT).orderBy("createdAt", Query.Direction.ASCENDING).addSnapshotListener((queryDocumentSnapshots, e) -> {
                if (e != null) {
                    Timber.e(e, "listen:error");
                    return;
                }
                displayMessagesFromServer(queryDocumentSnapshots);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayMessagesFromServer(@Nullable QuerySnapshot queryDocumentSnapshots) {
        Timber.d("displayMessagesFromServer: started");
        List<Chat> chats = queryDocumentSnapshots.toObjects(Chat.class);
        ArrayList<String> messages = new ArrayList<>();
        for (Chat chat : chats) {
            Timber.d("displayMessagesFromServer: %s", chat.getMessage());
            messages.add(chat.getName() + " : " + chat.getMessage());
        }
        adapter = new ChatAdapter(this, chats);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.smoothScrollToPosition(adapter.getItemCount());
        adapter.notifyDataSetChanged();
    }

    private void saveChatDocumentData(String message) {
        Timber.d("saveChatDocumentData: started");
        Chat chat = new Chat(chatUser.getUsername(), message);

        try {
            userDocument.collection(CHAT)
                    .add(chat)
                    .addOnSuccessListener(documentReference -> Timber.d("DocumentSnapshot added with ID: %s", documentReference.getId()))
                    .addOnFailureListener(e -> Timber.e(e, "Error adding document"));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to send message. Please check your Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserDocumentData() {
        Timber.d("saveUserDocumentData: started");
        db = FirebaseFirestore.getInstance();
        db.collection(USER)
                .add(chatUser)
                .addOnSuccessListener(documentReference -> {
                    Timber.d("DocumentSnapshot added user with ID: %s", documentReference.getId());
                    userDocument = documentReference;
                    triggerNewMessageListener();
                })
                .addOnFailureListener(e -> Timber.e(e, "Error adding document"));
    }
}
