package com.jakarinc.jakar.Controller.ConfirmaID;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakarinc.jakar.R;
import com.jakarinc.jakar.RemoteIO.RegisterUser;

public class EsperandoMensagemActivity extends AppCompatActivity {
    TextView contador;
    EditText codigo_input;
    Button confirma_codigo_button;
    String numeroTelefone;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esperando_mensagem);
        //getSupportActionBar().setTitle("Quase lá ... ");
        Intent intent = getIntent();
        numeroTelefone = intent.getStringExtra(SplashActivity.TELEFONE_ENTRADO);

        sendMessage(numeroTelefone);


        contador = (TextView) findViewById(R.id.contador);
        codigo_input = (EditText) findViewById(R.id.codigo_input);
        confirma_codigo_button = (Button) findViewById(R.id.confirma_codigo_button);
        confirma_codigo_button.setEnabled(false);

        codigo_input.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                if (s.length() == 6) {
                    confirma_codigo_button.setEnabled(true);
                } else {
                    confirma_codigo_button.setEnabled(false);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        startCountdown();

    }

    @Override
    public void onBackPressed() {
    }

    private void sendMessage(String telefone) {
        RegisterUser.registrar(telefone, this);
    }

    private void startCountdown() {
        setTimer(new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                contador.setText(secondsToString(millisUntilFinished / 1000));
            }

            public void onFinish() {
                timeoutPopUp();
            }
        }.start());
    }

    private void timeoutPopUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.falhaAuth)
                .setTitle("Falha de autenticação")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendMessage(numeroTelefone);
                        startCountdown();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*
    *no futuro checará se o código informado bate com o do servidor
    * mas hoje simplesmente deixa o usuário seguir para a próxima tela
    */ // TODO fazer o sistema de verificacao funcionar
    public void validaCodigo(View v) {
        String codigo = codigo_input.getText().toString();
        RegisterUser.confirmar(numeroTelefone, codigo, this);
    }

    private String secondsToString(long pTime) {
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }

    public CountDownTimer getTimer() {
        return timer;
    }

    public void setTimer(CountDownTimer timer) {
        this.timer = timer;
    }
}
