package dk.au.mad22spring.group04.cibusapp.ui.adapters;

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
import dk.au.mad22spring.group04.cibusapp.model.Result;

//Inspired by the recycleView video from L3 - Demo 2: RecyclerView in action

public class ApiListAdapter extends RecyclerView.Adapter<ApiListAdapter.ApiListViewHolder> {
    private List<Result> listOfRecipes;

    public interface IApiItemClickedListener {
        void onApiItemClicked(int index);
    }

    private final IApiItemClickedListener listener;

    public ApiListAdapter(IApiItemClickedListener listener) {
        this.listener = listener;
    }

    public void updateListOfRecipe(List<Result> lists) {
        listOfRecipes = lists;
        notifyDataSetChanged();
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
        holder.recipe_title.setText(listOfRecipes.get(position).getName());
        //TODO error here.
        //holder.total_cooking_time.setText((Integer) listOfRecipes.get(position).getTotalTimeMinutes());
        Glide.with(holder.imgRecipe.getContext()).load(listOfRecipes.get(position).getThumbnailUrl()).into(holder.imgRecipe);

    }

    @Override
    public int getItemCount() {
        if (listOfRecipes != null) {
            return listOfRecipes.size();
        } else return 0;
    }


    public class ApiListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Init activity
        TextView recipe_title, total_cooking_time;
        ImageView imgRecipe;

        IApiItemClickedListener listener;

        public ApiListViewHolder(@NonNull View itemView, IApiItemClickedListener apiItemClickedListener) {
            super(itemView);

            recipe_title = itemView.findViewById(R.id.header_recipe_title);
            total_cooking_time = itemView.findViewById(R.id.cooking_time);
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
