package ph.me.testonlajn;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArrayList<Pytanie> pytania;
    TextView textViewPytanie, blad;
    RadioButton radioButton1, radioButton2, radioButton3;
    RadioGroup grupaRadio;
    Button dalej;
    int x = 0;
    int sprawdzian = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textViewPytanie = findViewById(R.id.pytanieView);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        grupaRadio = findViewById(R.id.grupaRadio);
        dalej = findViewById(R.id.dalej);
        blad = findViewById(R.id.blad);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/karol3ke36/JSon/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ArrayList<Pytanie>> call = jsonPlaceHolderApi.getPytanie();
        call.enqueue(
                new Callback<ArrayList<Pytanie>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Pytanie>> call,
                                           Response<ArrayList<Pytanie>> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        pytania = response.body();
                        textViewPytanie.setText(pytania.get(x).trescPytania);
                        radioButton1.setText(pytania.get(x).odpA);
                        radioButton2.setText(pytania.get(x).odpB);
                        radioButton3.setText(pytania.get(x).odpC);
                        dalej.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(radioButton1.isChecked()){
                                    sprawdzian = 1;
                                }
                                else if(radioButton2.isChecked()){
                                    sprawdzian = 2;
                                }
                                else if(radioButton3.isChecked()){
                                    sprawdzian = 3;
                                }

                                if(sprawdzian == pytania.get(x).odpPoprawna){
                                    x++;
                                    blad.setText("");
                                    pytania = response.body();
                                    textViewPytanie.setText(pytania.get(x).trescPytania);
                                    radioButton1.setText(pytania.get(x).odpA);
                                    radioButton2.setText(pytania.get(x).odpB);
                                    radioButton3.setText(pytania.get(x).odpC);
                                }
                                else {
                                    blad.setText(("Gupiś"));
                                }
                                if(x > 3){
                                    textViewPytanie.setVisibility(view.INVISIBLE);
                                    grupaRadio.setVisibility(view.INVISIBLE);
                                    blad.setText("Szacuneczke Beemiarzu!");
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Pytanie>> call, Throwable t) {

                    }
                }
        );
    }
}