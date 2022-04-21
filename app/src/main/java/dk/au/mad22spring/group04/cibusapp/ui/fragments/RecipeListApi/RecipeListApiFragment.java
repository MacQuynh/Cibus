package dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListApi;

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
import android.widget.EditText;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.databinding.RecipeListApiFragmentBinding;
import dk.au.mad22spring.group04.cibusapp.model.Result;
import dk.au.mad22spring.group04.cibusapp.ui.adapters.ApiListAdapter;

public class RecipeListApiFragment extends Fragment implements ApiListAdapter.IApiItemClickedListener {

    // Used View binding here bc - laziness: https://developer.android.com/topic/libraries/view-binding#java
    private RecipeListApiFragmentBinding binding;
    private RecipeListApiViewModel vm;
    private ApiListAdapter adapter;
    private List<Result> resultList;

    private RecyclerView recyclerView;

    public static RecipeListApiFragment newInstance() {
        return new RecipeListApiFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RecipeListApiFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        vm = new ViewModelProvider(this).get(RecipeListApiViewModel.class);
        vm.getInitialListBack().observe(getViewLifecycleOwner(), results -> adapter.updateListOfRecipe(results));
        recyclerView = binding.recycleviewListAPI;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm.getInitialList();
        adapter = new ApiListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onApiItemClicked(int index) {

    }
}