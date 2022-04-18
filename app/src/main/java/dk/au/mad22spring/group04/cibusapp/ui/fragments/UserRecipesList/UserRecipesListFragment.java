package dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipesList;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.au.mad22spring.group04.cibusapp.R;

public class UserRecipesListFragment extends Fragment {

    private UserRecipesListViewModel userRecipeVM;

    public static UserRecipesListFragment newInstance() {
        return new UserRecipesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_recipes_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userRecipeVM = new ViewModelProvider(this).get(UserRecipesListViewModel.class);
        // TODO: Use the ViewModel
    }

}