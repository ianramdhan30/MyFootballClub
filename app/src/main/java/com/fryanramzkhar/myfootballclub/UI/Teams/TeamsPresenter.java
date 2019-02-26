package com.fryanramzkhar.myfootballclub.UI.Teams;

import android.telecom.Call;

import com.fryanramzkhar.myfootballclub.Data.Remote.ApiClient;
import com.fryanramzkhar.myfootballclub.Data.Remote.ApiInterface;
import com.fryanramzkhar.myfootballclub.Model.TeamsResponse;
import com.fryanramzkhar.myfootballclub.Utils.Constant;

import retrofit2.Callback;
import retrofit2.Response;

public class TeamsPresenter implements TeamsContract.Presenter {
    //TODO Membuat Variable yang dibuthukan
    private final TeamsContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public TeamsPresenter(TeamsContract.View view) {
        this.view = view;
    }

    @Override
    public void getDataListTeams() {
        view.showProgress();
        retrofit2.Call<TeamsResponse> call = apiInterface.getAllTeams(Constant.S, Constant.C );
        call.enqueue(new Callback<TeamsResponse>() {
            @Override
            public void onResponse(retrofit2.Call<TeamsResponse> call, Response<TeamsResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    view.showDataList(response.body().getTeams());
                }else{
                    view.showFailureMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<TeamsResponse> call, Throwable t) {
                view.showFailureMessage(t.getMessage());
            }
        });
    }
}
