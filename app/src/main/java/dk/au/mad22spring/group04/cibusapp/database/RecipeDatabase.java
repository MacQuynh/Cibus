package dk.au.mad22spring.group04.cibusapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.helpers.Converters;
import dk.au.mad22spring.group04.cibusapp.model.InstructionDTO;
import dk.au.mad22spring.group04.cibusapp.model.RecipeDTO;

@Database(entities = {RecipeDTO.class, InstructionDTO.class}, version = 1) //@TypeConverters(Converters.class)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipeDAO drinkDAO();
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
