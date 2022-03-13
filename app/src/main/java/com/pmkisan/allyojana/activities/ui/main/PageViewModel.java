package com.pmkisan.allyojana.activities.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.pmkisan.allyojana.models.MyStatusModelList;
import com.pmkisan.allyojana.models.NewsModelList;
import com.pmkisan.allyojana.models.PreviewModelList;
import com.pmkisan.allyojana.models.ProfileModelList;
import com.pmkisan.allyojana.models.QuizModelList;
import com.pmkisan.allyojana.models.StatusModelList;
import com.pmkisan.allyojana.models.StatusViewModelList;
import com.pmkisan.allyojana.models.YojanaModelList;
import com.pmkisan.allyojana.models.YojanaRepository;

import java.util.Map;

public class PageViewModel extends AndroidViewModel {
    private final YojanaRepository yojanaRepository;
    Map<String, String> map;

    public PageViewModel(@NonNull Application application) {
        super(application);
        yojanaRepository = YojanaRepository.getInstance();
    }

    public PageViewModel(@NonNull Application application, Map<String, String> map) {
        super(application);
        this.map = map;
        yojanaRepository = YojanaRepository.getInstance();

    }

    public LiveData<YojanaModelList> geYojanaList() {
        return yojanaRepository.getYojanaModelLiveData();
    }

    public LiveData<YojanaModelList> getOthersData() {
        return yojanaRepository.getOthersLiveData();
    }

    public LiveData<NewsModelList> getNews() {
        return yojanaRepository.getNewsLiveData();
    }

    public LiveData<PreviewModelList> getPreviewData() {
        return yojanaRepository.getPreviewLiveData(map);
    }

    public LiveData<QuizModelList> getquizQuestions() {
        return yojanaRepository.getQuizQuestions();
    }

    public LiveData<ProfileModelList> getUserData() {
        return yojanaRepository.getProfileLiveData(map);
    }

    public LiveData<MyStatusModelList> fetchMyStatus() {
        return yojanaRepository.getMyStatus(map);

    }

    public LiveData<StatusModelList> fetchStatus() {
        return yojanaRepository.getStatus();
    }

    public LiveData<StatusViewModelList> fetchStatusViews() {
        return yojanaRepository.getStatusViews(map);
    }


}