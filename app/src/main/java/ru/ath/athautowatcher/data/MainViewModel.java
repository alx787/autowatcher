package ru.ath.athautowatcher.data;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static TransportDatabase database;
    private LiveData<List<Transport>> transport;
//    private LiveData<List<Transport>> transportFiltered;


    public MainViewModel(@NonNull Application application) {
        super(application);
        database = TransportDatabase.getInstance(getApplication());
        transport = database.transportDao().getAllTransports();
    }

    public Transport getTransportById(int id) {
        try {
            return new GetTransportTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAllTransport() {
        new DeleteAllTransportTask().execute();
    }

    public void insertTransport(Transport tr) {
        new InsertTransportTask().execute(tr);
    }

    public void deleteTransport(Transport tr) {
        new DeleteTransportTask().execute(tr);
    }

    public LiveData<List<Transport>> getTransport() {
        return transport;
    }

    public LiveData<List<Transport>> getTransportByFilter(String invnom, String autocol) {
        return database.transportDao().getTransportByFilter(invnom, autocol);
    }

    public List<String> getAllAutoCols() {
        try {
            return new GetAllAutocolsTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }




    private static class GetTransportTask extends AsyncTask<Integer, Void, Transport> {
        @Override
        protected Transport doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.transportDao().getTransportById(integers[0]);
            }
            return null;
        }
    }

    private static class DeleteAllTransportTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            database.transportDao().DeleteAllTransport();
            return null;
        }
    }

    private static class InsertTransportTask extends AsyncTask<Transport, Void, Void> {
        @Override
        protected Void doInBackground(Transport... transports) {
            if (transports != null && transports.length > 0) {
                database.transportDao().insertTransport(transports[0]);
            }
            return null;
        }
    }

    private static class DeleteTransportTask extends AsyncTask<Transport, Void, Void> {
        @Override
        protected Void doInBackground(Transport... transports) {
            if (transports != null && transports.length > 0) {
                database.transportDao().DeleteTransport(transports[0]);
            }
            return null;
        }
    }

    private static class GetAllAutocolsTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            return database.transportDao().getAllAutocols();
        }
    }
}
