package org.mullercamille.edtdroid.activities;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.mullercamille.edtdroid.R;
import org.mullercamille.edtdroid.adapters.DaysPagerAdapter;
import org.mullercamille.edtdroid.application.EdtDroid;
import org.mullercamille.edtdroid.fragments.DayFragment;
import org.mullercamille.edtdroid.model.Day;
import org.mullercamille.edtdroid.model.Group;
import org.mullercamille.edtdroid.model.Lesson;
import org.mullercamille.edtdroid.model.Model;


import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;




@SuppressLint("SimpleDateFormat")
public class Main extends FragmentActivity {
	private  int PAGE_NBR ;
	private EdtDroid fd;
	private Spinner sGroup;
	private ViewPager vpDays;
	private DaysPagerAdapter paDays;
	private BroadcastReceiver br = null;
	protected int count = 20;





	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fd = (EdtDroid) this.getApplication();

		/* Construct the view */



		setContentView(R.layout.activity_main);
		this.sGroup = (Spinner) findViewById(R.id.sGroup);
		this.vpDays = (ViewPager) findViewById(R.id.vpDays);

		/* Create the ViewPager */


		PAGE_NBR = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString("nBJ", "3"));

		this.paDays = new DaysPagerAdapter(super.getSupportFragmentManager());
		for (int i = 0; i < PAGE_NBR; i++) {
			Bundle b = new Bundle();
			b.putInt("pos", i);

			DayFragment frag = (DayFragment) Fragment.instantiate(this,
					DayFragment.class.getName());
			frag.setArguments(b);
			this.paDays.addItem(frag);

		}
		this.vpDays.setAdapter(this.paDays);

		/* Create the spinner */

		sGroup.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {


				try
				{
					fd.model.selectGroup(fd.model.getGroups().get(pos).getValue());
					new SyncDaysTask().execute(fd.model);
				}catch(IndexOutOfBoundsException e)
				{
					e.printStackTrace();
				}


			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		/* Update the view every minute */


		this.br = new BroadcastReceiver() {
			@Override
			public void onReceive(Context ctx, Intent intent) {
				if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {

					if(count == 0)
					{
						Main.this.paDays.update();

						count=20;
					}
					else
					{

						count--;
					}
				}
			}
		};

		/* Check if it is the first run */

		if (PreferenceManager.getDefaultSharedPreferences(this)
				.getString("groups_pref", "none").equals("none")) {
			startActivity(new Intent(Main.this, Pref.class));
		}




	}

	@Override
	public void onResume() {
		super.onResume();


		/* Try to fetch the group list */

		// Regen nb pages si pref changed
		if(	PAGE_NBR != Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString("nBJ", "3")))
		{
			PAGE_NBR = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString("nBJ", "3"));
			this.paDays = new DaysPagerAdapter(super.getSupportFragmentManager());
			for (int i = 0; i < PAGE_NBR; i++) {
				Bundle b = new Bundle();
				b.putInt("pos", i);

				DayFragment frag = (DayFragment) Fragment.instantiate(this,
						DayFragment.class.getName());
				frag.setArguments(b);
				this.paDays.addItem(frag);

			}
			this.vpDays.setAdapter(this.paDays);

			/* Create the spinner */

			sGroup.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int pos, long id) {


					try
					{
						fd.model.selectGroup(fd.model.getGroups().get(pos).getValue());
						new SyncDaysTask().execute(fd.model);
					}catch(IndexOutOfBoundsException e)
					{
						e.printStackTrace();
					}


				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});

			/* Update the view every minute */


			this.br = new BroadcastReceiver() {
				@Override
				public void onReceive(Context ctx, Intent intent) {
					if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {

						if(count == 0)
						{
							Main.this.paDays.update();

							count=20;
						}
						else
						{

							count--;
						}
					}
				}
			};

		}

		if (!PreferenceManager.getDefaultSharedPreferences(this)
				.getString("groups_pref", "none").equals("none")) {
			new SyncGroupsTask().execute(fd.model);
		}


		
		/* Register the broadcast receiver */
		registerReceiver(this.br, new IntentFilter(Intent.ACTION_TIME_TICK));
	}

	@Override
	public void onPause() {
		super.onPause();

		/* Unregister the broadcast receiver */

		unregisterReceiver(this.br);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_search)
			startActivity(new Intent(Main.this, Pref.class));
		return true;
	}

	private class SyncGroupsTask extends AsyncTask<Model, Void, Integer> {
		@Override
		protected Integer doInBackground(Model... model) {
			try {
				model[0].buildGroups();
			} catch (IOException e) {
				return -1;
			}

			return 0;
		}

		protected void onPostExecute(Integer result) {
			if (result != 0) {
				Toast.makeText(Main.this,
						"Impossible de récupérer la liste des groupes",
						Toast.LENGTH_SHORT).show();
				return;
			}

			/* Update the spinner */

			// Get the group name list
			List<String> list = new ArrayList<String>();
			for (Group i : fd.model.getGroups()) {
				list.add(i.getName());
			}

			// Set the adapter
			ArrayAdapter<String> adapter;
			adapter = new ArrayAdapter<String>(Main.this,
					android.R.layout.simple_spinner_item, list);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sGroup.setAdapter(adapter);

			// Set to the preferred group
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(Main.this);
			String pref = prefs.getString("groups_pref", "0");
			int i = 0;

			for (Group group : fd.model.getGroups()) {
				if (pref.contains(group.getValue())) {
					sGroup.setSelection(i);
				}
				i++;
			}
		}
	}

	public class SyncDaysTask extends AsyncTask<Model, Void, Integer> {
		@SuppressLint("SimpleDateFormat")
		@Override
		protected Integer doInBackground(Model... model) {
			try {
				List<Day> desModifiers = model[0].buildDays();
				//TODO 	
				if(desModifiers != null && desModifiers.size() > 0)
				{
					// Si un seul cours de modifiers
					if(desModifiers.size() ==1 && desModifiers.get(0).getLessons().size() ==1)
					{
						Day unJM =  desModifiers.get(0);
						Lesson uneLM= unJM.getLessons().get(0);
						SimpleDateFormat formater = new SimpleDateFormat("EEEE, d MMM");
						Date date = null;

						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						try {
							date = dateFormat.parse(unJM.getName());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						Notification(uneLM.getName(), 
								formater.format(date)
								+uneLM.getBegin()
								+" "+uneLM.getEnd());
					}
					else{
						int count = 0;
						for(Day unJM : desModifiers)
						{
							count += unJM.getLessons().size();
						}
						Notification("Emploi du temps mise à jour","Vous avez "+count+" cours modifiés");
					}
				}
			} catch (IOException e) {
				return -1;
			}


			return 0;
		}

		protected void onPostExecute(Integer result) {
			if (result != 0) {
				Toast.makeText(Main.this, "Erreur de synchronisation",
						Toast.LENGTH_SHORT).show();
				return;
			}

			paDays.update();
		}
	}

	@SuppressWarnings("deprecation")
	private void Notification(String notificationTitle, String notificationMessage) {

		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

		if (settings.getBoolean("pref_notif", true)) {
			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			android.app.Notification notification = new android.app.Notification(R.drawable.ic_launcher, notificationTitle,
					System.currentTimeMillis());
			Intent notificationIntent = new Intent(this, Main.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
			notification.setLatestEventInfo(Main.this, notificationTitle, notificationMessage, pendingIntent);
			notificationManager.notify(10001, notification);

		}
	}

}
