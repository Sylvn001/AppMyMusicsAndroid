package br.unoeste.appmymusics.api;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LetraService {
    @GET("search.php")
    Call<Letra> buscarLetra(@Query("apikey") String apikey, @Query("art") String artista ,@Query("mus") String musica);
}
