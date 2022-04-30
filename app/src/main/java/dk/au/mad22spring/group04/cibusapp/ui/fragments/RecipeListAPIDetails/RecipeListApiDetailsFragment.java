package dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListAPIDetails;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import dk.au.mad22spring.group04.cibusapp.databinding.RecipeListApiDetailsFragmentBinding;
import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;

public class RecipeListApiDetailsFragment extends Fragment {

    private RecipeListApiDetailsViewModel vm;
    private RecipeListApiDetailsFragmentBinding binding;

    private String recipeObject;
    private Integer indexObject;

    public static RecipeListApiDetailsFragment newInstance() {
        return new RecipeListApiDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = RecipeListApiDetailsFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            recipeObject = bundle.get(Constants.RECIPE_OBJECT).toString();
            indexObject = bundle.getInt(Constants.INDEX_OBJECT, 0);
        }

        vm = new ViewModelProvider(this).get(RecipeListApiDetailsViewModel.class);
        vm.getRecipeByName(recipeObject).observe(getViewLifecycleOwner(), new Observer<RecipeDTO>() {
            @Override
            public void onChanged(RecipeDTO recipeDTO) {
                binding.apiDetailsName.setText(recipeDTO.getName());
                Glide.with(binding.apiDetailsThumbnailUrl.getContext()).load(recipeDTO.getThumbnailUrl());
                binding.apiDetailsCountry.setText("Country: " + recipeDTO.getCountry());
                binding.apiDetailsNumServings.setText("Serving: " + recipeDTO.getNumServings().toString());
                binding.apiDetailsUserRatings.setText("Rating: " + recipeDTO.getUserRatings());
                binding.apiDetailsDescription.setText("Description: " + recipeDTO.getDescription());
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(RecipeListApiDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}