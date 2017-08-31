package grupoabpr.dam.isi.frsf.Lab01;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText email = (EditText) findViewById(R.id.editTextEmail);
        final EditText cuil = (EditText) findViewById(R.id.editTextCUIL);

        final TextView textoBarra = (TextView)findViewById(R.id.textViewMuestraDias);
        final TextView interes = (TextView)findViewById(R.id.textViewValorInteres);
        final EditText importe = (EditText)findViewById(R.id.editTextDecimal);

        final TextView mensaje = (TextView)findViewById(R.id.textViewMensaje);

        SeekBar barra = (SeekBar)findViewById(R.id.seekBarCantDias);


        barra.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textoBarra.setText(i + "");

                double valorImporte = Double.parseDouble(importe.getText().toString().isEmpty() ? "0.0" : importe.getText().toString());
                double valorInteres=calcularInteres(valorImporte,i);

                interes.setText(String.format("$ %.2f",valorInteres));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button boton = (Button)findViewById(R.id.botonPF);
        boton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty() || cuil.getText().toString().isEmpty() || importe.getText().toString().isEmpty()){
                    mensaje.setText(getString(R.string.msjErrorCamposVacios));
                    mensaje.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorError));
                }
                else if(textoBarra.getText().equals("0") || textoBarra.getText().equals("")){
                    mensaje.setText(getString(R.string.msjSeekBar));
                    mensaje.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorError));
                }
                else if(!validarEmail(email.getText().toString())){
                    mensaje.setText(getString(R.string.msjErrorEmail));
                    mensaje.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorError));
                }
                else{
                    mensaje.setText(getString(R.string.msjAceptado1) + " " + interes.getText() + " " + getString(R.string.msjAceptado2));
                    mensaje.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAcepted));
                }
            }
        });
    }

    //Definición de métodos
    private double calcularInteres(double valorImporte, int i){
        double valorInteres;
        if(valorImporte>=0 && valorImporte<=5000){
            if(i<30){
                valorInteres = valorImporte*(Math.pow(1.0+Double.parseDouble(getString(R.string.menos30_1)),(double)i/360.0)-1);
            }
            else{
                valorInteres = valorImporte*(Math.pow(1.0+Double.parseDouble(getString(R.string.mas30_1)),(double)i/360.0)-1);
            }
        }
        else if(valorImporte>5000 && valorImporte<=99999){
            if(i<30){
                valorInteres = valorImporte*(Math.pow(1.0+Double.parseDouble(getString(R.string.menos30_2)),(double)i/360.0)-1);
            }
            else{
                valorInteres = valorImporte*(Math.pow(1.0+Double.parseDouble(getString(R.string.mas30_2)),(double)i/360.0)-1);
            }
        }
        else if(valorImporte>=99999){
            if(i<30){
                valorInteres = valorImporte*(Math.pow(1.0+Double.parseDouble(getString(R.string.menos30_3)),(double)i/360.0)-1);
            }
            else{
                valorInteres = valorImporte*(Math.pow(1.0+Double.parseDouble(getString(R.string.menos30_3)),(double)i/360.0)-1);
            }
        }
        else return 0.0;


        return valorInteres;
    }

    private Boolean validarEmail(String s){
        int arrobas=0;
        Boolean punto=false;
        if(s.charAt(0) == '@' || Character.isDigit(s.charAt(0)) || s.charAt(0) == '.' || s.isEmpty()){
            return false;
        }
        else {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '@') {
                    arrobas++;
                } else if (s.charAt(i) == '.' && arrobas > 0) {
                    punto = true;
                }
            }
        }
        if(arrobas==1 && punto){
            return true;
        }

        return false;
    }
}
