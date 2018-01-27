package com.example.jungle.keepinmind1.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jungle.keepinmind1.Activity.AddRecordActivity;
import com.example.jungle.keepinmind1.Adapter.PassageAdapter;
import com.example.jungle.keepinmind1.Bean.GuPiaoBean;
import com.example.jungle.keepinmind1.Bean.GuPiaoContent;
import com.example.jungle.keepinmind1.Bean.ManageMoneyDBBean;
import com.example.jungle.keepinmind1.Bean.ManageMoneyPassage;
import com.example.jungle.keepinmind1.Bean.RetrunJson;
import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Utils.PublicUtil.RecycleViewDivider;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.HttpResultSubscriber;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.MyService;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.NetRequestFactory;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.Transform;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailBillFragment extends Fragment {


    private ArrayList<ManageMoneyPassage> passageList;
    private RecyclerView recyclerView;
    private PassageAdapter adapter;

    private Context mContext;

    public DetailBillFragment(Context context) {
        // Required empty public constructor
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_detail_bill, container, false);
        passageList = new ArrayList();
        passageList.add(new ManageMoneyPassage("-1", "-1", "-1"));
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new PassageAdapter(getContext(), passageList);
        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        initData();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onEventMain(String Message) {
        ArrayList tmpList = new ArrayList();
        tmpList.addAll(passageList);
        passageList.clear();
        passageList.addAll(tmpList);
        adapter.notifyDataSetChanged();
    }

    private void initData() {

        NetRequestFactory.getInstance().createService(MyService.class).getpassage().compose(Transform.<RetrunJson<List<ManageMoneyPassage>>>defaultSchedulers()).subscribe(new HttpResultSubscriber<RetrunJson<List<ManageMoneyPassage>>>() {
            @Override
            public void onSuccess(RetrunJson<List<ManageMoneyPassage>> rj) {
                passageList.addAll(1, (ArrayList<ManageMoneyPassage>) rj.getResult());
                adapter = new PassageAdapter(getContext(), passageList);
                recyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void _onError(RetrunJson<List<ManageMoneyPassage>> rj) {

            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
