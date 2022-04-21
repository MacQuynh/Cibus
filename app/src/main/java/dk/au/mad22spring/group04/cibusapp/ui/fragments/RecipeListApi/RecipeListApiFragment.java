package dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListApi;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.au.mad22spring.group04.cibusapp.databinding.RecipeListApiFragmentBinding;

public class RecipeListApiFragment extends Fragment {

    // Used View binding here bc - laziness: https://developer.android.com/topic/libraries/view-binding#java
    private RecipeListApiFragmentBinding binding;
    private RecipeListApiViewModel vm;


    public static RecipeListApiFragment newInstance() {
        return new RecipeListApiFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RecipeListApiFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initialList();

        //TODO: Make a recycleView listItem
        //TODO: Make a search call
        //TODO: Find out what is wrong with constraint in xml.
        return view;
    }

    private void initialList() {
        binding.searchButton.setOnClickListener(view -> vm.getInitialListFromAPI());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(RecipeListApiViewModel.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}