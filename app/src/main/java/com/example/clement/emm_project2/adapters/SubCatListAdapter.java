package com.example.clement.emm_project2.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.activities.FormationSummaryActivity;
import com.example.clement.emm_project2.activities.FormationsActivity;
import com.example.clement.emm_project2.app.App;
import com.example.clement.emm_project2.app.server.ResponseHandler;
import com.example.clement.emm_project2.app.server.ServerHandler;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.model.SubCategory;
import com.example.clement.emm_project2.util.JsonUtil;
import com.example.clement.emm_project2.util.StringUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by perso on 13/08/15.
 */
public class SubCatListAdapter extends ArrayAdapter<SubCategory> {
    private static class ViewHolder{
        TextView title;
        TextView groupName = null;
    }

    public SubCatListAdapter(Context context, ArrayList<SubCategory> subcatList) {
        super(context, 0, subcatList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        final SubCategory subcat = getItem(position);
        int layoutId = R.layout.subcategory_with_separator_row;
        if(position != 0 && StringUtil.startsWithSameLetter(getItem(position - 1).getTitle(), subcat.getTitle())) {
            layoutId = R.layout.subcategory_row;
        }

        viewHolder = new ViewHolder();
        convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        viewHolder.title = (TextView) convertView.findViewById(R.id.subcat_row_title);
        viewHolder.groupName = (TextView) convertView.findViewById(R.id.subcat_group_name);

        viewHolder.title.setText(subcat.getTitle());
        if(viewHolder.groupName != null) {
            viewHolder.groupName.setText(subcat.getTitle().substring(0, 1).toUpperCase());
            View vHeader = convertView.findViewById(R.id.subcat_group_name);
            vHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                // Do nothing here, we don't want to trigger an action when clicking on an header

                }
            });
        }



        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerHandler server = new ServerHandler(App.getAppContext());
                final ProgressDialog progress;
                progress = ProgressDialog.show(getContext(), getContext().getString(R.string.loading),
                        getContext().getString(R.string.loading), true);

                // Look for formation in da

                final DataAccess da = new DataAccess(App.getAppContext());
                List<Formation> dbFormations = da.findDataWhere(Formation.class, "subCatId", subcat.getMongoID());
                if(dbFormations.size() > 0) {
                    //Formation found in database
                    progress.dismiss();
                    handleRedirection(dbFormations, subcat);
                } else {
                    server.getFormations(subcat.getMongoID(), new ResponseHandler() {
                        @Override
                        public void onSuccess(Object datas) {
                            List<Formation> formations = JsonUtil.parseJsonDatas((JSONArray) datas, Formation.class);
                            da.open();
                            for(Formation formation: formations) {
                                da.createData(formation);
                            }
                            da.close();
                            handleRedirection(formations, subcat);
                            progress.dismiss();

                        }

                        @Override
                        public void onError(String error) {
                            Log.wtf("Error", error);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progress.dismiss();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(App.getAppContext(), R.style.alertDialogStyle);
                                    builder.setTitle(R.string.dialog_error);
                                    builder.setMessage(R.string.download_dialog_error_msg);
                                    builder.setPositiveButton("OK", null);
                                    AlertDialog dialog = builder.create();
                                    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                                    dialog.show();
                                }
                            }, 2000);
                        }
                    });
                }



            }
        });

        return convertView;
    }

    private void handleRedirection(List<Formation> formations, SubCategory subcat) {
        Intent intent;
        Activity act = (Activity) getContext();
        if (formations.size() == 1) {
            intent = new Intent(act, FormationSummaryActivity.class);
            intent.putExtra("ean", formations.get(0).getEan());
            act.startActivity(intent);
        } else {
            Intent i = new Intent(act, FormationsActivity.class);
            i.putExtra("subCatId", subcat.getMongoID());
            i.putExtra("subCatTitle", subcat.getTitle());
            act.startActivity(i);
        }
    }
}
