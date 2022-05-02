package dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListAPIDetails;

import static dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListAPI.RecipeListApiFragment.TAG;

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

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.databinding.RecipeListApiDetailsFragmentBinding;
import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.Section;

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
        vm = new ViewModelProvider(this).get(RecipeListApiDetailsViewModel.class);

        Bundle bundle = this.getArguments();
        recipeObject = bundle.get(Constants.RECIPE_OBJECT).toString();
        indexObject = bundle.getInt(Constants.INDEX_OBJECT, 0);

        if (recipeObject != null) {
            vm.getRecipeByName(recipeObject);
        }

        vm.getRecipeByName().observe(getViewLifecycleOwner(), recipeDTO -> {
            recipeSetup(recipeDTO);
        });

        vm.getInstruction().observe(getViewLifecycleOwner(), instructions -> {
            //Reference to StringBuilder: https://localcoder.org/beginner-java-netbeans-how-do-i-display-for-loop-in-jlabel
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < instructions.size(); i++) {
                stringBuilder.append(i + 1 + ") ").append(instructions.get(i).getDisplayText())
                        .append("\n\n");
                binding.recipeListApiDetailsInstructions.setText(stringBuilder);
            }
        });

        vm.getSection().observe(getViewLifecycleOwner(), sections -> {
            //Sections and components
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < sections.size(); i++) {
                sb.append(sections.get(i).getIngredient().getName())
                        .append(":")
                        .append(" ").append(sections.get(i).getMeasurements().get(0).getQuantity())
                        .append(" ")
                        .append(sections.get(i).getMeasurements().get(0).getUnit().getDisplaySingular()).append("\n\n");
                binding.recipeListApiDetailsIngredients.setText(sb);
            }
        });

        saveButton();
        backButton();

        return view;
    }

    private void saveButton() {
        binding.recipeListApiDetailsBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Save-functionality
            }
        });
    }

    private void recipeSetup(RecipeDTO recipeDTO) {
        Glide.with(binding.imageViewAPI.getContext()).load(recipeDTO.getThumbnailUrl()).into(binding.imageViewAPI);
        binding.recipeListApiDetailsNameHeader.setText(recipeDTO.getName());
        binding.recipeLitApiDetailsServings.setText(recipeDTO.getNumServings().toString());
        binding.recipeListApiDetailsCountry.setText(recipeDTO.getCountry());
        binding.recipeListApiDetailsDescription.setText(recipeDTO.getDescription());
        binding.recipeListApiDetailsPrepTime.setText(String.format("%s min", Double.toString(recipeDTO.getPrepTimeMinutes())));
        binding.recipeListApiDetailsCookTime.setText(String.format("%s min", Double.toString(recipeDTO.getCookTimeMinutes())));
        binding.recipeListApiDetailsTotalTime.setText(String.format("%s min", Double.toString(recipeDTO.getTotalTimeMinutes())));
    }

    private void backButton() {
        //Reference: https://stackoverflow.com/questions/10863572/programmatically-go-back-to-the-previous-fragment-in-the-backstackhttps://stackoverflow.com/questions/10863572/programmatically-go-back-to-the-previous-fragment-in-the-backstack
        binding.recipeListApiDetailsBtnBack.setOnClickListener(view -> getActivity().onBackPressed());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(RecipeListApiDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}