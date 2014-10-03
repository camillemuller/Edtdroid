package org.mullercamille.edtdroid.application;

import org.mullercamille.edtdroid.model.Model;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class EdtDroid extends Application {
	public Model model;
	

	@Override
	public void onCreate() {
		super.onCreate();

		/* Create and setup the model with saved values */

		this.model = new Model();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		this.model.selectEstablishment(prefs.getString("estts_pref", "0"));
		this.model.selectDepartment(prefs.getString("depts_pref", "0"));
		this.model.selectGroup(prefs.getString("groups_pref", "0"));
		this.model.setSubgroup(prefs.getString("subgroup_pref", "0"));
	}
}
