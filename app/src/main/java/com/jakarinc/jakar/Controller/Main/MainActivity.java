package com.jakarinc.jakar.Controller.Main;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jakarinc.jakar.Controller.ConfirmaID.SplashActivity;
import com.jakarinc.jakar.Controller.ListaHorario.HorarioFragment;
import com.jakarinc.jakar.Controller.ListaHorario.ListFragmentQueDeveSerInstanciado;
import com.jakarinc.jakar.Controller.Profile.Estabelecimento_profile;
import com.jakarinc.jakar.Domain.Horario;
import com.jakarinc.jakar.LocalIO.Impl.Auth;
import com.jakarinc.jakar.R;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HorarioFragment.OnListFragmentInteractionListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback


{

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        masterDispatcher();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*Instancia fragmento de mapa
        * e passa para mMap em onMapReady
        * */
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);


        /*Torna o fragmento de mapa visível no main*/
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().
                replace(R.id.jumbotron_display, mapFragment, mapFragment.getTag())
                .addToBackStack(mapFragment.getTag())
                .commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            System.gc();
            Estabelecimento_profile fragmentoEstabelecimento = Estabelecimento_profile.newInstance("123456");
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().
                    replace(R.id.jumbotron_display, fragmentoEstabelecimento, fragmentoEstabelecimento.getTag())
                    .addToBackStack(fragmentoEstabelecimento.getTag())
                    .commit();

        } else if (id == R.id.nav_gallery) {
            System.gc();
            ListFragmentQueDeveSerInstanciado listaFragment = ListFragmentQueDeveSerInstanciado.newInstance(null, null);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.jumbotron_display, listaFragment, listaFragment.getTag())
                    .addToBackStack(listaFragment.getTag())
                    .commit();


        } else if (id == R.id.nav_slideshow) {
            System.gc();
            SupportMapFragment mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(this);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().
                    replace(R.id.jumbotron_display, mapFragment, mapFragment.getTag())
                    .addToBackStack(mapFragment.getTag())
                    .commit();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void masterDispatcher() {
        Auth autenticador = new Auth(getApplicationContext());
        String id = autenticador.getUserId();
        if (id == null) {
            System.out.println(autenticador.getUserId());
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onListFragmentInteraction(Horario h) {
        Toast.makeText(getApplicationContext(), "Horas " + String.valueOf(h.getHorasTimeStamp()), Toast.LENGTH_SHORT).show();
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Se a permissão para usar localização precisa não foi concedida ainda, pede permissão para o usuário
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        } else {
            /*Isso ativa a funcionalidade MyLocation no mapa.*/
            mMap.setMyLocationEnabled(true);
        }
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }


    /*Trata os pedidos de permissão*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch(requestCode) {
            /*Caso o pedido tenha sido para habilitar o acesso a localização de alta precisão*/
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                /*Se a request for cancelada, o array retornado é vazio. Se receber permissão, [0] = constante de permissão concedida.
                * Além disso, é OBRIGATÓRIO usar `checkSelfPermission` para checar a concessão.*/
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    /*Isso ativa a funcionalidade MyLocation no mapa.*/
                    mMap.setMyLocationEnabled(true);
                } else {
                    mMap.setMyLocationEnabled(false);
                }
            }
        }


    }

    /*@Override
    public void onListFragmentInteraction(Horario h) {

    }*/



}
