package dk.au.mad22spring.group04.cibusapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.IngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.InstructionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.MeasurementDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.UnitDTO;

@Database(entities = {RecipeDTO.class,
        InstructionDTO.class,
        SectionDTO.class,
        ComponentDTO.class,
        MeasurementDTO.class,
        IngredientDTO.class,
        UnitDTO.class}, version = 7, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipeDAO recipeDAO();
    private static RecipeDatabase instance;

    public static RecipeDatabase getDatabase(final Context context){
        if(instance == null){
            synchronized (RecipeDatabase.class){
                if(instance==null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeDatabase.class, Constants.RECIPE_DATABASE)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
