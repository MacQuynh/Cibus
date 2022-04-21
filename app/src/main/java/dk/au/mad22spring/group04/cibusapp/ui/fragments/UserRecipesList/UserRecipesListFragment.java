package dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipesList;

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
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.ui.adapters.UserRecipesListAdapter;

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

    //TODO: Find link, som sagde man skal bruge onViewCreated nu hvor onActivityCreated er deprecated
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userRecipeVM = new ViewModelProvider(this).get(UserRecipesListViewModel.class);

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


    }

    //TODO: Kan søge, men skal være på hele navnet... skal gerne kunne på dele af navn
    private void onSearch() {
        String text = searchText.getText().toString();
        userRecipeVM.searchRecipes(text);
    }

    @Override
    public void onUserRecipeClicked(int index) {

    }
}