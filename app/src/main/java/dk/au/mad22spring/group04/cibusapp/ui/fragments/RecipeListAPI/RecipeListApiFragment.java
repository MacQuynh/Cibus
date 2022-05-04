package dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListApi;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.R;
import dk.au.mad22spring.group04.cibusapp.databinding.RecipeListApiFragmentBinding;
import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.model.Result;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListApi.RecipeListApiViewModel;
import dk.au.mad22spring.group04.cibusapp.ui.adapters.ApiListAdapter;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListAPIDetails.RecipeListApiDetailsFragment;
import dk.au.mad22spring.group04.cibusapp.ui.interfaces.ApiRecipeSelectorInterface;
import dk.au.mad22spring.group04.cibusapp.ui.interfaces.UserRecipeSelectorInterface;

public class RecipeListApiFragment extends Fragment implements ApiListAdapter.IApiItemClickedListener {

    public static final String TAG = "RecipeListApiFragment";
    // Used View binding here bc - laziness: https://developer.android.com/topic/libraries/view-binding#java
    private RecipeListApiFragmentBinding binding;
    private RecipeListApiViewModel vm;
    private ApiListAdapter adapter;
    private List<Result> resultList;

    private RecyclerView recyclerView;

    private ApiRecipeSelectorInterface apiRecipeSelectorInterface;

    public static RecipeListApiFragment newInstance() {
        return new RecipeListApiFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RecipeListApiFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        searchButton();

        vm = new ViewModelProvider(this).get(RecipeListApiViewModel.class);
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
        vm.getInitialList();
        adapter = new ApiListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    //Master Detail inspiration: Demo from lecture "FragmentsArnieMovies"
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

    //Reference: https://stackoverflow.com/questions/28984879/how-to-open-a-different-fragment-on-recyclerview-onclick
    @Override
    public void onApiItemClicked(int index) {
        Log.d(TAG, "onApiItemClicked: " + index);

        Result result = adapter.getRecipeByIndex(index);

        apiRecipeSelectorInterface.onApiRecipeSelected(index);

        Bundle bundle = new Bundle();
        if (result != null) {
            bundle.putString(Constants.RECIPE_OBJECT, result.getName());
            bundle.putInt(Constants.INDEX_OBJECT, index);
        }

        AppCompatActivity activity = (AppCompatActivity) recyclerView.getContext();
        Fragment fragment = new RecipeListApiDetailsFragment();
        fragment.setArguments(bundle);

        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainActivityDetailLayout, fragment).addToBackStack(null).commit();
    }
}