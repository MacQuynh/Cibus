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
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;

public class UserRecipesListAdapter extends RecyclerView.Adapter<UserRecipesListAdapter.UserRecipeViewHolder>{
    public interface IUserRecipeItemClickListener{
        void onUserRecipeClicked(int index);
    }

    private IUserRecipeItemClickListener listener;
    private List<RecipeWithSectionsAndInstructionsDTO> recipeDTOList;

    public UserRecipesListAdapter(IUserRecipeItemClickListener listener){
        this.listener = listener;
    }

    public void updateUserRecipeList(List<RecipeWithSectionsAndInstructionsDTO> list){
        recipeDTOList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recipes_list_fragment_item, parent, false);
        UserRecipeViewHolder vh = new UserRecipeViewHolder(v, this.listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecipeViewHolder holder, int position) {
        if(recipeDTOList.get(position).recipe.getTotalTimeMinutes() != null){
            holder.txtTotalCookTime.setText(recipeDTOList.get(position).recipe.getTotalTimeMinutes() + " min");
        } else{
            holder.txtTotalCookTime.setText("?");
        }
        holder.txtName.setText(recipeDTOList.get(position).recipe.getName());
        holder.txtRating.setText(recipeDTOList.get(position).recipe.getUserRatings() + "");
        holder.txtNumberOfServings.setText(recipeDTOList.get(position).recipe.getNumServings() + "");
        if(recipeDTOList.get(position).recipe.getThumbnailUrl().equals("") || recipeDTOList.get(position).recipe.getThumbnailUrl() == null){
            holder.imgRecipe.setImageResource(R.drawable.default_recipe_image);
        } else {
            Glide.with(holder.imgRecipe.getContext()).load(recipeDTOList.get(position).recipe.getThumbnailUrl()).into(holder.imgRecipe);
        }
    }

    @Override
    public int getItemCount() {
        if(recipeDTOList == null){
            return 0;
        }
        return recipeDTOList.size();
    }

    public class UserRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //viewHolder UI widget references:
        ImageView imgRecipe;
        TextView txtName, txtTotalCookTime, txtRating, txtNumberOfServings;

        IUserRecipeItemClickListener listener;

        public UserRecipeViewHolder(@NonNull View itemView, IUserRecipeItemClickListener listener) {
            super(itemView);
            this.listener = listener;

            imgRecipe = itemView.findViewById(R.id.recipe_img);
            txtName = itemView.findViewById(R.id.header_recipe_title);
            txtTotalCookTime = itemView.findViewById(R.id.txtTotalCookTimeUserRecipeList);
            txtRating = itemView.findViewById(R.id.txtRatingUserRecipeList);
            txtNumberOfServings = itemView.findViewById(R.id.txtNumberOfServingsUserRecipeList);

            //Click listener for whole item
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onUserRecipeClicked(getAdapterPosition());
        }
    }
}
