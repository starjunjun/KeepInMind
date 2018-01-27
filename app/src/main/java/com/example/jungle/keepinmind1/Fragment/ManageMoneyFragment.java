package com.example.jungle.keepinmind1.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jungle.keepinmind1.Adapter.ManageMoneyAdapter;
import com.example.jungle.keepinmind1.Bean.GuPiaoBean;
import com.example.jungle.keepinmind1.Bean.JiJinBean;
import com.example.jungle.keepinmind1.Bean.ManageMoneyDBBean;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * dsc
 * A simple {@link Fragment} subclass.
 */
public class ManageMoneyFragment extends Fragment {

    private List manageList;
    private ManageMoneyAdapter adapter;
    private RecyclerView recyclerView;

    public ManageMoneyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_manage_money, container, false);
        manageList = new ArrayList();
        manageList.add(new JiJinBean("-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"));
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_View);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new ManageMoneyAdapter(manageList, getContext(), true);
        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        initData();
        return view;
    }

    private void initData() {

        NetRequestFactory.getInstance().createService(MyService.class).getJiJin("http://web.juhe.cn:8080/fund/findata/main?key=" + "b5fca6b81e0891fbc04775022cd39b19").compose(Transform.<RetrunJson<Map<String, JiJinBean>[]>>defaultSchedulers()).subscribe(new HttpResultSubscriber<RetrunJson<Map<String, JiJinBean>[]>>() {
            @Override
            public void onSuccess(RetrunJson<Map<String, JiJinBean>[]> rj) {
                Collection<JiJinBean> collection = rj.getResult()[0].values();
                Iterator<JiJinBean> iterator = collection.iterator();
                while (iterator.hasNext()) {
                    JiJinBean value = (JiJinBean) iterator.next();
                    manageList.add(value);
                }
                adapter.notifyItemRangeInserted(1,manageList.size()-1);

            }

            @Override
            public void _onError(RetrunJson<Map<String, JiJinBean>[]> rj) {

            }

        });
    }

    private void initJiJin() {

        NetRequestFactory.getInstance().createService(MyService.class).getJiJin("http://web.juhe.cn:8080/fund/findata/main?key=" + "b5fca6b81e0891fbc04775022cd39b19").compose(Transform.<RetrunJson<Map<String, JiJinBean>[]>>defaultSchedulers()).subscribe(new HttpResultSubscriber<RetrunJson<Map<String, JiJinBean>[]>>() {
            @Override
            public void onSuccess(RetrunJson<Map<String, JiJinBean>[]> rj) {
                Log.i("success", "onSuccess: 111");
                List jl = new ArrayList();
                jl.add(new JiJinBean("-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"));
                Collection<JiJinBean> collection = rj.getResult()[0].values();
                Iterator<JiJinBean> iterator = collection.iterator();
                while (iterator.hasNext()) {
                    JiJinBean value = (JiJinBean) iterator.next();
                    jl.add(value);
                }
                manageList.clear();
                manageList.addAll(jl);
                adapter.notifyItemRangeChanged(1,manageList.size()-1);

            }

            @Override
            public void _onError(RetrunJson<Map<String, JiJinBean>[]> rj) {

            }

        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(Boolean isJiJin) {
        if (isJiJin) {
            initJiJin();
        }else{
            initGuPiao();
        }
    }

    private void initGuPiao() {

        NetRequestFactory.getInstance().createService(MyService.class).getgupiao().compose(Transform.<RetrunJson<List<GuPiaoBean>>>defaultSchedulers()).subscribe(new HttpResultSubscriber<RetrunJson<List<GuPiaoBean>>>() {
            @Override
            public void onSuccess(RetrunJson<List<GuPiaoBean>> rj) {

                List<GuPiaoBean> gl = new ArrayList<GuPiaoBean>();
                gl.add(new GuPiaoBean("-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", 1, 1, "-1", "-1"));
                gl.addAll(1,rj.getResult());
                manageList.clear();
                manageList.addAll(gl);
                adapter.notifyItemRangeChanged(1,manageList.size()-1);

            }

            @Override
            public void _onError(RetrunJson<List<GuPiaoBean>> rj) {

            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }



}
