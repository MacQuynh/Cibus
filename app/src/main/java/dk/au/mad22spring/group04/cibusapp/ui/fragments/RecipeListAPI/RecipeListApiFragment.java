package dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListApi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dk.au.mad22spring.group04.cibusapp.databinding.RecipeListApiFragmentBinding;
import dk.au.mad22spring.group04.cibusapp.ui.adapters.ApiListAdapter;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListAPI.RecipeListApiViewModel;
import dk.au.mad22spring.group04.cibusapp.ui.interfaces.ApiRecipeSelectorInterface;

public class RecipeListApiFragment extends Fragment implements ApiListAdapter.IApiItemClickedListener {

    // Used View binding here bc - laziness: https://developer.android.com/topic/libraries/view-binding#java
    private RecipeListApiFragmentBinding binding;
    private RecipeListApiViewModel vm;
    private ApiListAdapter adapter;
    private RecyclerView recyclerView;

    private ApiRecipeSelectorInterface apiRecipeSelectorInterface;

    //Reference: https://stackoverflow.com/questions/2665993/is-is-possible-to-make-a-method-execute-only-once - boolean to make sure initial list only get called once.
    private boolean alreadyExecuted = false;

    public static RecipeListApiFragment newInstance() {
        return new RecipeListApiFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RecipeListApiFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        searchButton();

        vm = new ViewModelProvider(getActivity()).get(RecipeListApiViewModel.class);
        if (!alreadyExecuted) {
            vm.getInitialList();
            alreadyExecuted = true;
        }
        vm.getInitialListBack().observe(getViewLifecycleOwner(), results -> adapter.updateListOfRecipe(results));
        recyclerView = binding.recycleviewListAPI;
        return view;
    }

    private void searchButton() {
        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search_text = binding.apiEditText.getText().toString();
                vm.searchRecipes(search_text);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ApiListAdapter(this, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    //Master Detail inspiration: Demo "FragmentsArnieMovies" from lecture
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            apiRecipeSelectorInterface = (ApiRecipeSelectorInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement UserRecipe interface");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onApiItemClicked(int index) {
        apiRecipeSelectorInterface.onApiRecipeSelected(index);

    }
}