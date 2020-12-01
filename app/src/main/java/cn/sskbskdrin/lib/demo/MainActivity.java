package cn.sskbskdrin.lib.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import cn.sskbskdrin.base.IA;
import cn.sskbskdrin.lib.demo.simple.SampleListFragment;
import cn.sskbskdrin.lib.demo.simple.SimpleAdapter;
import cn.sskbskdrin.lib.demo.tool.HttpFragment;
import cn.sskbskdrin.lib.demo.widget.BannerFragment;
import cn.sskbskdrin.lib.demo.widget.FlowFragment;
import cn.sskbskdrin.lib.demo.widget.PickerFragment;
import cn.sskbskdrin.lib.demo.widget.PullFragment;
import cn.sskbskdrin.lib.demo.widget.SwitchButtonFragment;
import cn.sskbskdrin.lib.demo.widget.TabHostFragment;

public class MainActivity extends FragmentActivity implements IA {
    private static final String TAG = "MainActivity";

    private static List<ClassItem> mList = new ArrayList<>();

    static {
        mList.add(new ClassItem(SampleListFragment.class, "SampleList"));
        mList.add(new ClassItem(FlowFragment.class, "FlowLayout"));
        mList.add(new ClassItem(TabHostFragment.class, "TabHost"));
        mList.add(new ClassItem(PickerFragment.class, "PickerView"));
        mList.add(new ClassItem(BannerFragment.class, "BannerView"));
        mList.add(new ClassItem(HttpFragment.class, "HTTP"));
        mList.add(new ClassItem(SwitchButtonFragment.class, "SwitchButton"));
        mList.add(new ClassItem(PullFragment.class, "PullLayout"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.main_list);
        listView.setAdapter(new SimpleAdapter<>(mList));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            ClassItem item = (ClassItem) parent.getAdapter().getItem(position);
            if (Activity.class.isAssignableFrom(item.clazz)) {
                openActivity(item.clazz);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("fragment", item.clazz.getName());
                openActivity(CommonFragmentActivity.class, bundle);
            }
        });
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public boolean isFinish() {
        return isFinishing();
    }

    public static class ClassItem implements Serializable {

        public Class clazz;
        public String text;

        public ClassItem(Class clazz, String text) {
            this.clazz = clazz;
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
