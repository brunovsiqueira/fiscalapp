package br.com.infracea.fiscalapp.screens.container.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.infracea.fiscalapp.R;

public class MenuFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu, container, false);

        //findViewItems(view);

        return view;
    }

}
