package vnu.uet.tuan.uetsupporter.Activities;


import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Fragment.LinhTinh.DialogLogoutFragment;
import vnu.uet.tuan.uetsupporter.Model.Download.LoaiThongBao;
import vnu.uet.tuan.uetsupporter.Model.Download.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.Model.Response.Message;
import vnu.uet.tuan.uetsupporter.Model.Subcribe;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

import static vnu.uet.tuan.uetsupporter.config.Config.REGISTER_ANNOUNCES;
import static vnu.uet.tuan.uetsupporter.config.Config.REGISTER_NEWS;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */

public class SettingsActivity extends AppCompatPreferenceActivity {
    private static Context context;
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            }
            else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private boolean isXLargeTablet(Context context) {
        SettingsActivity.context = context;
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<PreferenceActivity.Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || UserPreferenceFragment.class.getName().equals(fragmentName)
                || NotificationPreferenceFragment.class.getName().equals(fragmentName);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class UserPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_user);
            setHasOptionsMenu(true);
            Preference preference = findPreference(getString(R.string.logout));
            String username = Utils.getUsername(context);
            preference.setSummary(username);
            preference.setOnPreferenceClickListener(this);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {

            DialogFragment dialogFragment = new DialogLogoutFragment();
            dialogFragment.show(getFragmentManager(),"Logout");
            return false;
        }
    }


    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {

        private final String TAG =this.getClass().getSimpleName();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);

            initPreference();

            getRegister();

            listenUI();
        }

        private void listenUI() {
            final MultiSelectListPreference tintuc = (MultiSelectListPreference) findPreference(getString(R.string.pref_title_tintuc));
            final MultiSelectListPreference thongbao = (MultiSelectListPreference) findPreference(getString(R.string.pref_title_thongbao));
            //init announcemark
            final CheckBoxPreference mark = (CheckBoxPreference) findPreference(getString(R.string.pref_title_mark));
            mark.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    Boolean isRegister = (Boolean) newValue;
                    registCourseIds(isRegister);
                    return true;
                }
            });
            tintuc.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    Log.e("prefer", "valueString : " + newValue.toString());
                    SharedPreferences sharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(REGISTER_NEWS,newValue.toString());
                    editor.apply();
                    //todo : register with firebase
                    String[] registers = newValue.toString()
                            .replace("[","").replace("]","")
                            .trim().split(",");

                    //unregister
                    for (int i=0;i<tintuc.getEntryValues().length;i++){
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(String.valueOf(tintuc.getEntryValues()[i]));
                    }

                    // register
                    for (String rg : registers){
                        Log.e("SUB",rg.trim());
                        FirebaseMessaging.getInstance().subscribeToTopic(rg.trim());
                    }

                    return true;
                }
            });
            thongbao.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    SharedPreferences sharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(REGISTER_ANNOUNCES,newValue.toString());
                    editor.apply();
                    //todo : register with firebase
                    String[] registers = newValue.toString()
                            .replace("[","").replace("]","")
                            .trim().split(",");

                    //unregister
                    for (int i=0;i<thongbao.getEntryValues().length;i++){
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(String.valueOf(thongbao.getEntryValues()[i]));
                    }

                    // register
                    for (String rg : registers){
                        Log.e("SUB",rg.trim());
                        FirebaseMessaging.getInstance().subscribeToTopic(rg.trim());
                    }

                    return true;
                }
            });

        }
        public static CharSequence[] removeElements(CharSequence[] input, String deleteMe) {
            List result = new LinkedList();

            for(CharSequence item : input)
                if(!deleteMe.equals(String.valueOf(item)))
                    result.add(item);

            return (CharSequence[]) result.toArray(input);
        }


        private void initPreference(){

            //init tintuc
            MultiSelectListPreference tintuc = (MultiSelectListPreference) findPreference(getString(R.string.pref_title_tintuc));

            //lấy được tất cả tin tức
            ArrayList<LoaiTinTuc> loaiTinTucArrayList = Utils.getAllLoaiTinTuc(getActivity());

            CharSequence[] entriesTinTuc = new CharSequence[loaiTinTucArrayList.size()];
            CharSequence[] entriesValueTinTuc = new CharSequence[loaiTinTucArrayList.size()];


            for (int i = 0; i < loaiTinTucArrayList.size(); i++) {
                entriesTinTuc[i] = (loaiTinTucArrayList.get(i).getKind());
                entriesValueTinTuc[i] = (String.valueOf(loaiTinTucArrayList.get(i).get_id()));
            }
            tintuc.setEntries(entriesTinTuc);
            tintuc.setEntryValues(entriesValueTinTuc);

            //=============================================================================
            //initThongBao
            MultiSelectListPreference thongbao = (MultiSelectListPreference) findPreference(getString(R.string.pref_title_thongbao));

            //lấy được tất cả thongbao
            ArrayList<LoaiThongBao> loaiThongBaoArrayList = Utils.getAllLoaiThongBao(getActivity());

            CharSequence[] entriesThongBao = new CharSequence[loaiThongBaoArrayList.size()];
            CharSequence[] entriesValueThongBao = new CharSequence[loaiThongBaoArrayList.size()];


            for (int i = 0; i < loaiThongBaoArrayList.size(); i++) {
                entriesThongBao[i] = (loaiThongBaoArrayList.get(i).getTenLoaiThongBao());
                entriesValueThongBao[i] = (String.valueOf(loaiThongBaoArrayList.get(i).get_id()));

            }
            thongbao.setEntries(entriesThongBao);
            thongbao.setEntryValues(entriesValueThongBao);

        }

        private void getRegister() {

            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(context);
            String stringNews = sharedPreferences.getString(REGISTER_NEWS,"[]");
            String stringAnnounces=sharedPreferences.getString(REGISTER_ANNOUNCES,"[]");

            try {
                JSONArray announces = new JSONArray(stringAnnounces);
                JSONArray news = new JSONArray(stringNews);

                MultiSelectListPreference tintuc = (MultiSelectListPreference) findPreference(getString(R.string.pref_title_tintuc));
                Set<String> valueTinTuc = new HashSet<String>(); //setValue ~ check cai nao dung ID
                for (int i = 0; i < news.length(); i++) {
                    valueTinTuc.add(String.valueOf(news.get(i)));
                }
                tintuc.setValues(valueTinTuc);
                MultiSelectListPreference thongbao = (MultiSelectListPreference) findPreference(getString(R.string.pref_title_thongbao));
                Set<String> valueThongBao = new HashSet<String>(); //setValue ~ check cai nao dung ID
                for (int i = 0; i <announces.length(); i++) {
                    valueThongBao.add(String.valueOf(announces.get(i)));
                }
                thongbao.setValues(valueThongBao);


            } catch (JSONException e) {
                Log.e(TAG, "getRegister: "+e.getMessage() );
                e.printStackTrace();
            }
        }

        private void registCourseIds(final Boolean isRegister) {

            Call<String[]> call;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.API_HOSTNAME)
                    // Sử dụng GSON cho việc parse và maps JSON data tới Object
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
            call = apiTinTuc.getCourseIds(Utils.getUserToken(getActivity()));
            call.enqueue(new Callback<String[]>() {
                @Override
                public void onResponse(Call<String[]> call, Response<String[]> response) {
                    if (isRegister){ //sub
                        for (String cId : response.body()){
                            Log.e(TAG,cId);
                            FirebaseMessaging.getInstance().subscribeToTopic(cId);
                        }
                    } else { //un sub
                        for (String cId : response.body()){
                            FirebaseMessaging.getInstance().unsubscribeFromTopic(cId);
                        }
                    }
                }

                @Override
                public void onFailure(Call<String[]> call, Throwable t) {
                    CheckBoxPreference mark = (CheckBoxPreference) findPreference(getString(R.string.pref_title_mark));
                    mark.setChecked(!mark.isChecked());
                }
            });
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class NotificationPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            ListPreference preference = (ListPreference) findPreference(getString(R.string.title_setting_time));
            bindPreferenceSummaryToValue(preference);
            bindPreferenceSummaryToValue(findPreference(context.getString(R.string.sound_notification)));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
