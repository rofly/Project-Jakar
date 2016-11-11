package com.jakarinc.jakar.Controller.Main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jakarinc.jakar.Controller.ConfirmaID.SplashActivity;
import com.jakarinc.jakar.Controller.ListaHorario.HorarioFragment;
import com.jakarinc.jakar.Controller.ListaHorario.ListFragmentQueDeveSerInstanciado;
import com.jakarinc.jakar.Controller.Profile.Estabelecimento_profile;
import com.jakarinc.jakar.Domain.Horario;
import com.jakarinc.jakar.Domain.Lugar;
import com.jakarinc.jakar.LocalIO.Impl.Auth;
import com.jakarinc.jakar.LocalIO.Impl.Horarios;
import com.jakarinc.jakar.R;
import com.jakarinc.jakar.RemoteIO.getPlaces;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HorarioFragment.OnListFragmentInteractionListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveListener


{


    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private GoogleMap mMap;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LatLng ultimaPosicao;
    final ArrayList<Lugar> lugares = new ArrayList<>();
    private boolean moveFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //                                                                                                                                                                                             new Horarios().addNew(new Horario());

        masterDispatcher();

        moveFlag = true;

        /*Inicializa a API Google Play Services, necessária para ferramenta de localização*/
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


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
       /* if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {

            System.gc();
            SupportMapFragment mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(this);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().
                    replace(R.id.jumbotron_display, mapFragment, mapFragment.getTag())
                    .addToBackStack(mapFragment.getTag())
                    .commit();



        } else if (id == R.id.nav_schedule) {
            System.gc();
            ListFragmentQueDeveSerInstanciado listaFragment = ListFragmentQueDeveSerInstanciado.newInstance(null, null);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.jumbotron_display, listaFragment, listaFragment.getTag())
                    .addToBackStack(listaFragment.getTag())
                    .commit();


        } else if (id == R.id.nav_account) {

            System.gc();
            Estabelecimento_profile fragmentoEstabelecimento = Estabelecimento_profile.newInstance("jak123");
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().
                    replace(R.id.jumbotron_display, fragmentoEstabelecimento, fragmentoEstabelecimento.getTag())
                    .addToBackStack(fragmentoEstabelecimento.getTag())
                    .commit();


        } else if (id == R.id.nav_join) {

        } else if (id == R.id.nav_about) {



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

    public void onCameraMove() {
        moveFlag = true;
    }

    public void onCameraIdle() {

        if (moveFlag) { //Verifica se a ultima interacao com a camera foi de movimento ou nao.
            LatLng mapPosition = mMap.getCameraPosition().target;

            //Recupera estabelecimentos próximos.
            mMap.clear();
            lugares.clear();
            new getPlaces().fetchInRadius(500, mapPosition.latitude, mapPosition.longitude, this, lugares, new getPlaces.callback() {
                @Override
                public void callbackFunction() {

                    for (int i = 0; i < lugares.size(); i++) {
                        if (lugares.get(i).getId().equals("jak124")) {//Adiciona marcador customizado ao exemplo.
                            mMap.addMarker(new MarkerOptions().position(new LatLng(lugares.get(i).getLatitude(), lugares.get(i).getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.diamondmarker)).title("Exemplo"));
                        } else if (lugares.get(i).getId().equals("ChIJh2MbM3y9pgAR_F1x0-4EIkU")) {

                        } else {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(lugares.get(i).getLatitude(), lugares.get(i).getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.unregisteredmarker)).flat(true));
                        }

                    }

                }
            });
            moveFlag = false;
        }

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

         //Se a permissão para usar localização não foi concedida ainda, pede permissão para o usuário
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

        } else {
            //Isso ativa a funcionalidade MyLocation no mapa.
            mMap.setMyLocationEnabled(true);
            //Move a câmera do mapa para a localização obtida
            if (ultimaPosicao != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(ultimaPosicao));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }

            //Recupera os estabelecimentos próximos.
            LatLng mapPosition = mMap.getCameraPosition().target;
            mMap.clear();
            lugares.clear();
            new getPlaces().fetchInRadius(500, mapPosition.latitude, mapPosition.longitude, this, lugares, new getPlaces.callback() {
                @Override
                public void callbackFunction() {

                    for (int i = 0; i < lugares.size(); i++) {
                        if (lugares.get(i).getId().equals("jak124")) {//Adiciona marcador customizado ao exemplo.
                            mMap.addMarker(new MarkerOptions().position(new LatLng(lugares.get(i).getLatitude(), lugares.get(i).getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.diamondmarker)).title("Exemplo"));
                        } else if (lugares.get(i).getId().equals("ChIJh2MbM3y9pgAR_F1x0-4EIkU"))  {

                        } else {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(lugares.get(i).getLatitude(), lugares.get(i).getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.unregisteredmarker)).flat(true));
                        }

                    }

                }
            });

            mMap.setOnCameraIdleListener(this);
            mMap.setOnCameraMoveListener(this);


        }



    }


    /*Trata os pedidos de permissão*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch(requestCode) {
            /*Caso o pedido tenha sido para habilitar o acesso a localização de baixa precisão*/
            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                /*Se a request for cancelada, o array retornado é vazio. Se receber permissão, [0] = constante de permissão concedida.
                * Além disso, é OBRIGATÓRIO usar `checkSelfPermission` para checar a concessão.*/
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    /*Isso ativa a funcionalidade MyLocation no mapa.*/
                    mMap.setMyLocationEnabled(true);

                    if (mLastLocation == null) {
                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        if (mLastLocation != null) {
                            double lat = mLastLocation.getLatitude();
                            double lng = mLastLocation.getLongitude();
                            ultimaPosicao = new LatLng(lat, lng);

                            //Move a câmera do mapa para a localização obtida
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(ultimaPosicao));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                        }
                    }

                } else {
                    Toast toast = Toast.makeText(this, R.string.toastPermissionDenied, Toast.LENGTH_LONG);
                }
            }
        }


    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        /*Armazena a última localização do usuário utilizando a GoogleApiClient
          É necessário pedir permissão antes.
         */
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);


        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                double lat = mLastLocation.getLatitude();
                double lng = mLastLocation.getLongitude();
                ultimaPosicao = new LatLng(lat, lng);
                //Move a câmera do mapa para a localização obtida
                mMap.moveCamera(CameraUpdateFactory.newLatLng(ultimaPosicao));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            }

            LatLng mapPosition = mMap.getCameraPosition().target;
            //Recupera estabelecimentos próximos.
            mMap.clear();
            lugares.clear();
            new getPlaces().fetchInRadius(500, mapPosition.latitude, mapPosition.longitude, this, lugares, new getPlaces.callback() {
                @Override
                public void callbackFunction() {

                    for (int i = 0; i < lugares.size(); i++) {
                        if (lugares.get(i).getId().equals("jak124")) {//Adiciona marcador customizado ao exemplo.
                            mMap.addMarker(new MarkerOptions().position(new LatLng(lugares.get(i).getLatitude(), lugares.get(i).getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.diamondmarker)).title("Exemplo"));
                        } else if (lugares.get(i).getId().equals("ChIJh2MbM3y9pgAR_F1x0-4EIkU")) {

                        } else {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(lugares.get(i).getLatitude(), lugares.get(i).getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.unregisteredmarker)).flat(true));
                        }

                    }

                }
            });

        }

    }



    @Override
    public void onConnectionSuspended(int i) {
        Toast toast = Toast.makeText(this, R.string.googleApiConnectionSuspended, Toast.LENGTH_LONG);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast toast = Toast.makeText(this, R.string.googleApiConnectionFailed, Toast.LENGTH_LONG);
    }




    /*@Override
    public void onListFragmentInteraction(Horario h) {

    }*/



}
