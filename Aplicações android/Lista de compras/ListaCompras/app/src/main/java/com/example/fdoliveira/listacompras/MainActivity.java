package com.example.fdoliveira.listacompras;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    public Cursor cursor=null;
    public EditText edittexto = null;
    public CriaBanco banco=null;
    public BancoController db=null;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edittexto=(EditText) findViewById(R.id.editText);
        banco=new CriaBanco(this);
        Button botao = (Button)findViewById(R.id.button);
        db=new BancoController(this);
        if(cursor==null){
            db.insereDado("Insira Lista");
        }
        cursor=db.carregaDados();
        edittexto.setText(cursor.getString(cursor.getColumnIndexOrThrow(banco.TEXTO)));


        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultado;
                String texto = edittexto.getText().toString();
                if(cursor == null) {


                    resultado = db.insereDado(texto);
                    Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
                }
                else{
                    resultado=db.alteraRegistro(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(banco.ID))),
                            texto);
                            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
                }
            }
        });


        TextView sobre=(TextView) findViewById(R.id.textView2);
        sobre.setOnClickListener(new TextView.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(), Main2Activity.class);
                startActivity(it);
            }
        });
        TextView proximo=(TextView) findViewById(R.id.textView5);
        proximo.setOnClickListener(new TextView.OnClickListener() {
            public void onClick(View v) {
                if(cursor!=null&&cursor.moveToNext()){
                    edittexto.setText(cursor.getString(cursor.getColumnIndexOrThrow(banco.TEXTO)));
                }
                else {
                    cursor = null;
                    edittexto.setText("Insira Lista");
                    Toast.makeText(getApplicationContext(), "Insira uma nova lista", Toast.LENGTH_LONG).show();
                }
            }
        });
        TextView anterior=(TextView) findViewById(R.id.textView4);
        anterior.setOnClickListener(new TextView.OnClickListener() {
            public void onClick(View v) {
                if(cursor!=null&&cursor.moveToPrevious()){
                    edittexto.setText(cursor.getString(cursor.getColumnIndexOrThrow(banco.TEXTO)));
                }
                else {
                    cursor = null;
                    edittexto.setText("Insira Lista");
                }
            }
        });
        TextView consultar=(TextView) findViewById(R.id.textView10);
        consultar.setOnClickListener(new TextView.OnClickListener() {
            public void onClick(View v) {
                cursor=db.carregaDados();
                edittexto.setText(cursor.getString(cursor.getColumnIndexOrThrow(banco.TEXTO)));
            }
        });
        TextView excluir=(TextView) findViewById(R.id.textView11);
        excluir.setOnClickListener(new TextView.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (cursor != null) {
                        db.deletaRegistro(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(banco.ID))));
                        cursor = db.carregaDados();
                        edittexto.setText(cursor.getString(cursor.getColumnIndexOrThrow(banco.TEXTO)));
                        Toast.makeText(getApplicationContext(), "Lista excluída", Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Não foi possível excluir lista \n"+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        TextView contato=(TextView) findViewById(R.id.textView8);
        contato.setOnClickListener( new TextView.OnClickListener(){
            public void onClick(View v){
                Intent it=new Intent(v.getContext(),Main22Activity.class);
                startActivity(it);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
