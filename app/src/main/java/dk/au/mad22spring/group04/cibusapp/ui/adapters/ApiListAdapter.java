package dk.au.mad22spring.group04.cibusapp.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.R;
import dk.au.mad22spring.group04.cibusapp.model.Recipes;
import dk.au.mad22spring.group04.cibusapp.model.Result;

//Inspired by the recycleView video from L3 - Demo 2: RecyclerView in action

public class ApiListAdapter extends RecyclerView.Adapter<ApiListAdapter.ApiListViewHolder> {
    private Recipes listOfRecipes;

    public interface IApiItemClickedListener {
        void onApiItemClicked(int index);
    }

    private final IApiItemClickedListener listener;

    public ApiListAdapter(IApiItemClickedListener listener) {
        this.listener = listener;
    }

    public void updateListOfRecipe(Recipes lists) {
        listOfRecipes = lists;
        notifyDataSetChanged();
    }

    public Result getRecipeByIndex(int index) {
        return listOfRecipes.getResults().get(index);
    }

    @NonNull
    @Override
    public ApiListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_list_api_fragment_item, parent, false);
        //Inflater stored in RcvViewHolder to return the view - the city list in adapter
        return new ApiListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ApiListViewHolder holder, int position) {

        holder.recipe_title.setText(listOfRecipes.getResults().get(position).getName());
        holder.total_cooking_time.setText(listOfRecipes.getResults().get(position).getCountry());
        if (listOfRecipes.getResults().get(position).getCookTimeMinutes() == null) {
            holder.total_cooking_time.setText(R.string.no_total_cooking_time);
        } else
            holder.total_cooking_time.setText(R.string.total_time + listOfRecipes.getResults().get(position).getCookTimeMinutes().toString() + R.string.min);
        holder.country_code.setText(listOfRecipes.getResults().get(position).getCountry());
        Glide.with(holder.imgRecipe.getContext()).load(listOfRecipes.getResults().get(position).getThumbnailUrl()).into(holder.imgRecipe);

    }

    @Override
    public int getItemCount() {
        if (listOfRecipes != null) {
            return listOfRecipes.getResults().size();
        } else return 0;
    }

    public class ApiListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Init activity
        TextView recipe_title, total_cooking_time, country_code;
        ImageView imgRecipe;

        IApiItemClickedListener listener;

        public ApiListViewHolder(@NonNull View itemView, IApiItemClickedListener apiItemClickedListener) {
            super(itemView);

            recipe_title = itemView.findViewById(R.id.header_recipe_title);
            total_cooking_time = itemView.findViewById(R.id.cooking_time);
            country_code = itemView.findViewById(R.id.country_code);
            imgRecipe = itemView.findViewById(R.id.api_recipe_img);

            listener = apiItemClickedListener;
            //set click listener for the whole list item
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onApiItemClicked(getAdapterPosition());
        }
    }
}
