package dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListAPIDetails;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.au.mad22spring.group04.cibusapp.R;

public class RecipeListApiDetailsFragment extends Fragment {

    private RecipeListApiDetailsViewModel mViewModel;

    public static RecipeListApiDetailsFragment newInstance() {
        return new RecipeListApiDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recipe_list_api_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RecipeListApiDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}