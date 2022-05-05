package dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListAPIDetails;

import static dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListApi.RecipeListApiFragment.TAG;

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
import dk.au.mad22spring.group04.cibusapp.model.Component;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.Result;
import dk.au.mad22spring.group04.cibusapp.model.Section;

public class RecipeListApiDetailsFragment extends Fragment {

    private RecipeListApiDetailsViewModel vm;
    private RecipeListApiDetailsFragmentBinding binding;

    public Result recipe;

    private String recipeObject;
    private Integer indexObject;

    private static int recipeIndex = 0;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = RecipeListApiDetailsFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        vm = new ViewModelProvider(getActivity()).get(RecipeListApiDetailsViewModel.class);
        setSelectedRecipe(recipeIndex);
        //recipe = vm.getRecipeByIndex(recipeIndex);

        /*//TODO: Slet bundle og get by index i stedet
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
        });*/


        saveButton();
        backButton();

        return view;
    }

    public void setSelectedRecipe(int index){
        recipeIndex = index;

        if(vm != null){
            recipeSetup();
        }
    }
    
    private void saveButton() {
        binding.recipeListApiDetailsBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipeTobeAddedtoDB = recipeObject;
                vm.addRecipeFromAPItoDB(recipeTobeAddedtoDB);
            }
        });
    }

    private void recipeSetup() {
        recipe = vm.getRecipeByIndex(recipeIndex);

        Glide.with(binding.imageViewAPI.getContext()).load(recipe.getThumbnailUrl()).into(binding.imageViewAPI);
        binding.recipeListApiDetailsNameHeader.setText(recipe.getName());
        if(recipe.getNumServings() != null){
            binding.recipeLitApiDetailsServings.setText(recipe.getNumServings().toString());
        }
        binding.recipeListApiDetailsCountry.setText(recipe.getCountry());
        binding.recipeListApiDetailsDescription.setText(recipe.getDescription());
        if(recipe.getPrepTimeMinutes()!=null){
            binding.recipeListApiDetailsPrepTime.setText(String.format("%s min", Double.toString(recipe.getPrepTimeMinutes())));
        } else {
            binding.recipeListApiDetailsPrepTime.setText("?");
        }
        if(recipe.getCookTimeMinutes()!=null){
            binding.recipeListApiDetailsCookTime.setText(String.format("%s min", Double.toString(recipe.getCookTimeMinutes())));
        } else {
            binding.recipeListApiDetailsCookTime.setText("?");
        }
        if(recipe.getTotalTimeMinutes()!=null){
            binding.recipeListApiDetailsTotalTime.setText(String.format("%s min", Double.toString(recipe.getTotalTimeMinutes())));
        } else {
            binding.recipeListApiDetailsTotalTime.setText("?");
        }

        //Reference to StringBuilder: https://localcoder.org/beginner-java-netbeans-how-do-i-display-for-loop-in-jlabel
        StringBuilder stringBuilderInstructions = new StringBuilder();
        for (int i = 0; i < recipe.getInstructions().size(); i++) {
            stringBuilderInstructions.append(i + 1 + ") ").append(recipe.getInstructions().get(i).getDisplayText())
                    .append("\n\n");
            binding.recipeListApiDetailsInstructions.setText(stringBuilderInstructions);
        }

        StringBuilder sb = new StringBuilder();
        if(recipe.getSections() != null){
            List<Component> componentList = recipe.getSections().get(0).getComponents();
            for (int i = 0; i < componentList.size(); i++) {
                sb.append(componentList.get(i).getIngredient().getName())
                        .append(":")
                        .append(" ").append(componentList.get(i).getMeasurements().get(0).getQuantity())
                        .append(" ")
                        .append(componentList.get(i).getMeasurements().get(0).getUnit().getDisplaySingular()).append("\n\n");
                binding.recipeListApiDetailsIngredients.setText(sb);
            }
        }



    }

    private void backButton() {
        //Reference: https://stackoverflow.com/questions/10863572/programmatically-go-back-to-the-previous-fragment-in-the-backstackhttps://stackoverflow.com/questions/10863572/programmatically-go-back-to-the-previous-fragment-in-the-backstack
        binding.recipeListApiDetailsBtnBack.setOnClickListener(view -> getActivity().onBackPressed());
    }


}