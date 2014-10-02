package org.geeckodev.edtdroid.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.geeckodev.edtdroid.R;
import org.geeckodev.edtdroid.adapters.DaysPagerAdapter;
import org.geeckodev.edtdroid.application.EdtDroid;
import org.geeckodev.edtdroid.fragments.DayFragment;
import org.geeckodev.edtdroid.model.Group;
import org.geeckodev.edtdroid.model.Lesson;
import org.geeckodev.edtdroid.model.Model;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;



public class Main extends FragmentActivity {
	private  int PAGE_NBR ;
	private EdtDroid fd;
	private Spinner sGroup;
	private ViewPager vpDays;
	private DaysPagerAdapter paDays;
	private BroadcastReceiver br = null;
	protected int count = 60;
	


	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fd = (EdtDroid) this.getApplication();

		/* Construct the view */
		
		

		setContentView(R.layout.activity_main);
		this.sGroup = (Spinner) findViewById(R.id.sGroup);
		this.vpDays = (ViewPager) findViewById(R.id.vpDays);

		/* Create the ViewPager */
		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(this);
		

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
					
					count=60;
					}
					else
					{
						
						Log.i("info","Etat du compteur"+count);
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
						
						count=60;
						}
						else
						{
							
							Log.i("info","Etat du compteur"+count);
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

		//unregisterReceiver(this.br);
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
		@Override
		protected Integer doInBackground(Model... model) {
			try {
				 List<Lesson> lessonChanged = model[0].buildDays();
			//TODO 	
			if(lessonChanged != null && lessonChanged.size() > 0)
				{
				
					 Notification("Consulter votre emploi du temps", "Modification sur l'EDT");
					
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
	    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    android.app.Notification notification = new android.app.Notification(R.drawable.ic_launcher, notificationTitle,
	    System.currentTimeMillis());

	    Intent notificationIntent = new Intent(this, Main.class);
	    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
	    notification.setLatestEventInfo(Main.this, notificationTitle, notificationMessage, pendingIntent);
	    notificationManager.notify(10001, notification);
	}
	
}
