package com.example.uteq.revistasplaceholder.util;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.DocumentsContract;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.example.uteq.revistasplaceholder.BuildConfig;
import com.example.uteq.revistasplaceholder.webservice.Asynchtask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Objects;

public class Util {

    public static void downloadFile(String DownloadUrl, Context context, String nombre, String ext) {
        DownloadManager.Request request1 = new DownloadManager.Request(Uri.parse(DownloadUrl));
        request1.setDescription("Descarga de archivo PDF");   //appears the same in Notification bar while downloading
        request1.setTitle(nombre + ext);
        request1.setVisibleInDownloadsUi(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request1.allowScanningByMediaScanner();
            request1.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        }


        request1.setDestinationInExternalFilesDir(context, "/File", nombre + ext);

        DownloadManager manager1 = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Objects.requireNonNull(manager1).enqueue(request1);
        if (DownloadManager.STATUS_SUCCESSFUL == 8) {
            Util.mostrarPDF(nombre + ext, context, context.getFilesDir() + nombre + ext);
            Toast.makeText(context, "El archivo fue descargado correctamente",
                    Toast.LENGTH_LONG).show();
        }
    }

    public static void downloadFile(String DownloadUrl, String nombre, String ext, Context context) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    //primero especificaremos el origen de nuestro archivo a descargar utilizando
                    //la ruta completa
                    URL url = new URL(DownloadUrl);

                    //establecemos la conexión con el destino
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    //establecemos el método jet para nuestra conexión
                    //el método setdooutput es necesario para este tipo de conexiones
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(true);

                    //por último establecemos nuestra conexión y cruzamos los dedos 😛
                    urlConnection.connect();

                    //vamos a establecer la ruta de destino para nuestra descarga
                    //para hacerlo sencillo en este ejemplo he decidido descargar en
                    //la raíz de la tarjeta SD
                    File SDCardRoot = context.getFilesDir();

                    //vamos a crear un objeto del tipo de fichero
                    //donde descargaremos nuestro fichero, debemos darle el nombre que
                    //queramos, si quisieramos hacer esto mas completo
                    //cogeríamos el nombre del origen
                    File file = new File(SDCardRoot, nombre + ext);
                    int count = 0;
                    if (file.exists()) {
                        while (file.exists()) {
                            count++;
                            file = new File(SDCardRoot, nombre + count + ext);
                        }
                    }

                    //utilizaremos un objeto del tipo fileoutputstream
                    //para escribir el archivo que descargamos en el nuevo
                    FileOutputStream fileOutput = new FileOutputStream(file);

                    //leemos los datos desde la url
                    InputStream inputStream = urlConnection.getInputStream();

                    //obtendremos el tamaño del archivo y lo asociaremos a una
                    //variable de tipo entero
                    int totalSize = urlConnection.getContentLength();
                    int downloadedSize = 0;

                    //creamos un buffer y una variable para ir almacenando el
                    //tamaño temporal de este
                    byte[] buffer = new byte[1024];
                    int bufferLength = 0;

                    //ahora iremos recorriendo el buffer para escribir el archivo de destino
                    //siempre teniendo constancia de la cantidad descargada y el total del tamaño
                    //con esto podremos crear una barra de progreso
                    while ((bufferLength = inputStream.read(buffer)) > 0) {

                        fileOutput.write(buffer, 0, bufferLength);
                        downloadedSize += bufferLength;
//        /podríamos utilizar una función para ir actualizando el progreso de lo
//                //descargado
//                actualizaProgreso(downloadedSize, totalSize);

                    }
                    //cerramos
                    fileOutput.close();
                    urlConnection.disconnect();
                    inputStream.close();

                    Util.mostrarPDF(nombre + ext, context, file.toString());
//y gestionamos errores
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                    Util.mostrarMensaje("Error al descargar el archivo", context);
                }
            }
        });
    }

    //Procedimiento para mostrar el documento PDF generado
    public static void mostrarPDF(String nombPdf, Context context, String url) {
        Util.mostrarMensaje("Visualizando documento", context);
        // Así va correctamente la dirección
        //String dir = Environment.getExternalStorageDirectory()+ "/Mi App/pdf/" + nombPdf;
        //File file = new File(context.getFilesDir().toString());
        //Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", new File(url));

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setDataAndType(Uri.fromFile(new File(url)), "application/pdf");
        intent.setType("application/pdf");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, );
        context.startActivity(intent);
    }

    public static void mostrarMensaje(String mensaje, Context context) {

        Thread thread = new Thread() {
            public void run() {
                Looper.prepare();//Call looper.prepare()
                Handler mHandler = new Handler() {
                    public void handleMessage(Message msg) {
                        Toast.makeText(context, mensaje, Toast.LENGTH_LONG);
                    }
                };
                Looper.loop();
            }
        };
        thread.start();
    }
}
