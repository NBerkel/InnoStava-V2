package com.aware.plugin.InnoStaVa;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.aware.Aware;

public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    //Plugin settings in XML @xml/preferences
    public static final String STATUS_PLUGIN_INNOSTAVA = "status_plugin_innostava";
    public static final String GROUP = "group";
    public static final String LAST_SCHEDULED = "last_scheduled";

    //Plugin settings UI elements
    private static CheckBoxPreference status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        status = (CheckBoxPreference) findPreference(STATUS_PLUGIN_INNOSTAVA);
        if (Aware.getSetting(this, STATUS_PLUGIN_INNOSTAVA).length() == 0) {
            Aware.setSetting(this, STATUS_PLUGIN_INNOSTAVA, true); //by default, the setting is true on install
        }
        status.setChecked(Aware.getSetting(getApplicationContext(), STATUS_PLUGIN_INNOSTAVA).equals("true"));

        EditTextPreference group = (EditTextPreference) findPreference(GROUP);
        if (Aware.getSetting(this, GROUP).length() == 0) {
            Aware.setSetting(this, GROUP, 0);
        }
        group.setSummary("Group: " + Aware.getSetting(getApplicationContext(), GROUP));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference setting = findPreference(key);
        if (setting.getKey().equals(STATUS_PLUGIN_INNOSTAVA)) {
            Aware.setSetting(this, key, sharedPreferences.getBoolean(key, false));
            status.setChecked(sharedPreferences.getBoolean(key, false));
        }
        if (setting.getKey().equals(GROUP)) {
            Aware.setSetting(getApplicationContext(), key, sharedPreferences.getString(key, "1"));
            setting.setSummary("Group: " + Aware.getSetting(getApplicationContext(), GROUP));
        }
        if (Aware.getSetting(this, STATUS_PLUGIN_INNOSTAVA).equals("true")) {
            Aware.startPlugin(getApplicationContext(), "com.aware.plugin.innostava");
        } else {
            Aware.stopPlugin(getApplicationContext(), "com.aware.plugin.innostava");
        }
    }
}
