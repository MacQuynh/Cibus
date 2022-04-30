package dk.au.mad22spring.group04.cibusapp.API;

import dk.au.mad22spring.group04.cibusapp.model.Recipes;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RecipeAPI {
    @Headers({"X-RapidAPI-Host: tasty.p.rapidapi.com", "X-RapidAPI-Key:bd1b2bb22dmsh1e2749b33a38d65p1badc0jsnfcbbe1b26368"})
    @GET("list?from=0&size=20&tags=under_30_minutes")
    Call<Recipes> getRandomRecipesFromTheLast30min();

    @Headers({"X-RapidAPI-Host: tasty.p.rapidapi.com", "X-RapidAPI-Key:bd1b2bb22dmsh1e2749b33a38d65p1badc0jsnfcbbe1b26368"})
    @GET("https://tasty.p.rapidapi.com/recipes/list?from=0&size=20&tags=under_30_minutes&")
    Call<Recipes> getRecipeFromSearchString(@Query("q") String s);

}
