package dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipesList;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.R;
import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.ui.adapters.UserRecipesListAdapter;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipeDetails.UserRecipeDetailsFragment;

public class UserRecipesListFragment extends Fragment implements UserRecipesListAdapter.IUserRecipeItemClickListener{

    private UserRecipesListViewModel userRecipeVM;
    private UserRecipesListAdapter adapter;
    private RecyclerView rcvUserRecipes;
    private TextView searchText;
    private Button searchBtn;

    public static UserRecipesListFragment newInstance() {
        return new UserRecipesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_recipes_list_fragment, container, false);

        rcvUserRecipes = view.findViewById(R.id.rcvUserRecipes);
        searchText = view.findViewById(R.id.userRecipeList_txtSearch);
        searchBtn = view.findViewById(R.id.userRecipeList_searchBtn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new UserRecipesListAdapter(this);
        rcvUserRecipes.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvUserRecipes.setAdapter(adapter);

        userRecipeVM = new ViewModelProvider(this).get(UserRecipesListViewModel.class);
        userRecipeVM.getUserRecipes().observe(getViewLifecycleOwner(), new Observer<List<RecipeWithSectionsAndInstructionsDTO>>() {
            @Override
            public void onChanged(List<RecipeWithSectionsAndInstructionsDTO> recipeWithSectionsAndInstructionsDTOS) {
                adapter.updateUserRecipeList(recipeWithSectionsAndInstructionsDTOS);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearch();
            }
        });

        searchBtn.callOnClick();

    }

    private void onSearch() {
        String text = searchText.getText().toString();
        userRecipeVM.searchRecipes(text);
    }

    @Override
    public void onUserRecipeClicked(int index) {
        long recipeId = userRecipeVM.getRecipeByIndex(index).recipe.getIdRecipe();

        //Pass arguments inspiration: https://stackoverflow.com/questions/12739909/send-data-from-activity-to-fragment-in-android
        Bundle bundle = new Bundle();
        bundle.putLong(String.valueOf(Constants.RECIPE_NAME), recipeId);
        UserRecipeDetailsFragment userRecipeDetailsFragment = new UserRecipeDetailsFragment().newInstance();
        userRecipeDetailsFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.mainActitvityFragmentHolder, userRecipeDetailsFragment)
                .commit();
    }
}