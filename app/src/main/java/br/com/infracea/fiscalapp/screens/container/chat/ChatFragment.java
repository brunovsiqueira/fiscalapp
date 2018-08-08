package br.com.infracea.fiscalapp.screens.container.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.infracea.fiscalapp.R;
import br.com.infracea.fiscalapp.models.Author;
import br.com.infracea.fiscalapp.models.DefaultDialog;
import br.com.infracea.fiscalapp.models.Message;

public class ChatFragment extends Fragment {

    private View view;
    private DialogsList dialogsList;

    private ArrayList<DefaultDialog> dialogList = new ArrayList<>();
    //private RecyclerView dialogRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chat, container, false);

        dialogList.clear();

        findViewItems(view);
        setDummyData();

        //findViewItems(view);
        DialogsListAdapter dialogsListAdapter = new DialogsListAdapter<>(new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                //If you using another library - write here your way to load image
                Picasso.get().load(url).into(imageView);
            }
        });

        dialogsListAdapter.setItems(dialogList);
        dialogsList.setAdapter(dialogsListAdapter);

        return view;
    }


    public void findViewItems(View view) {

        dialogsList = view.findViewById(R.id.chat_dialog_list);


    }

    private void setDummyData() {
        //create fake author, message and dialog
        Author author1 = new Author("1a", "Bruno Siqueira", "https://osegredo.com.br/wp-content/uploads/2017/07/9-maneiras-de-se-tornar-uma-pessoa-melhor-830x450.jpg");
        Author author2 = new Author("1b", "João Siqueira", "https://osegredo.com.br/wp-content/uploads/2017/07/9-maneiras-de-se-tornar-uma-pessoa-melhor-830x450.jpg");
        ArrayList<Author> authorList = new ArrayList<>();
        authorList.add(author1);
        authorList.add(author2);

        Calendar calendar = Calendar.getInstance();
        Message message = new Message("a", "Eae doidão", author1, calendar);

        DefaultDialog defaultDialog1 = new DefaultDialog("a", null, "Conversa com joão", authorList, message, 2);
        DefaultDialog defaultDialog2 = new DefaultDialog("a", "https://osegredo.com.br/wp-content/uploads/2017/07/9-maneiras-de-se-tornar-uma-pessoa-melhor-830x450.jpg", "Conversa com joão", authorList, message, 2);

        dialogList.add(defaultDialog1);
        dialogList.add(defaultDialog2);
    }

}
