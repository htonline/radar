package com.example.BackRadar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.BackRadar.Entity.FileInfoInPath;
import com.example.BackRadar.Entity.ParamsOfSetting;
import com.example.ladarmonitor.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class ShowPathFragment extends Fragment {
    private static final String TAG = "ShowPathFragment";
    ListView listView_path;
    List<FileInfoInPath> fileInfoInPaths;
    ImageView iv_back;
    Context mcontext;
    ISettingActivityPresenter mpresenter;
    private ParamsOfSetting paramsOfSetting;
    ICallBackGetParams mcallbackgetparams;
    ShowPathFragment(ISettingActivityPresenter presenter,ICallBackGetParams iCallBackGetParams){
        mpresenter = presenter;
        mcallbackgetparams = iCallBackGetParams;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileInfoInPaths = (List<FileInfoInPath>) getArguments().getSerializable("FILEPATH");
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showpath,null);
        listView_path = view.findViewById(R.id.listview_path_view);
        iv_back = view.findViewById(R.id.iv_backicon);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(ShowPathFragment.this).commit();
            }
        });
        FileListAdapter fileListAdapter = new FileListAdapter(mcontext,fileInfoInPaths);
        listView_path.setAdapter(fileListAdapter);
        listView_path.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setMessage("确定删除");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FileInfoInPath paths = fileInfoInPaths.get(position);

                        mpresenter.deleteParams(paths.getFilename());
                        fileInfoInPaths.remove(position);
                        fileListAdapter.notifyDataSetChanged();
                        Toast.makeText(mcontext,"删除成功",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("取消", new  DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
                return true;
            }
        });
        listView_path.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileInfoInPath infoInPath = fileInfoInPaths.get(position);
                paramsOfSetting = mpresenter.loadParamsFromFile(infoInPath.getFilename(), new ICallBackToDo() {
                    @Override
                    public void dosuccess() {
                        Toast.makeText(mcontext,"加载成功！",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void dofailed() {
                        Toast.makeText(mcontext,"加载失败！",Toast.LENGTH_LONG).show();
                    }
                });
                mcallbackgetparams.getParams(paramsOfSetting);
                getActivity().getSupportFragmentManager().beginTransaction().remove(ShowPathFragment.this).commit();
            }
        });
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK){
                    if (isVisible()){
                        getActivity().getSupportFragmentManager().beginTransaction().remove(ShowPathFragment.this).commit();
                    }
                    return true;
                }
                return false;
            }

        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
