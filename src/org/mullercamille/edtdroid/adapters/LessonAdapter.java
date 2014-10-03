package org.mullercamille.edtdroid.adapters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.mullercamille.edtdroid.R;
import org.mullercamille.edtdroid.application.EdtDroid;
import org.mullercamille.edtdroid.model.Day;
import org.mullercamille.edtdroid.model.Lesson;
import org.mullercamille.edtdroid.widget.AfficheEDT;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RemoteViews;
import android.widget.TextView;

public class LessonAdapter extends BaseAdapter {
	EdtDroid fd;
	Day day;
	LayoutInflater inflater;
	 TextView unT;

	public LessonAdapter(Context context, Day day) {
		this.fd = (EdtDroid) context.getApplicationContext();
		this.day = day;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		int size = 0;
		for (Lesson lesson : this.day) {
			if (lesson.matchesSubgroup()) {
				size++;
			}
		}
		return size;
	}

	@Override
	public Object getItem(int i) {
		List<Lesson> match = new ArrayList<Lesson>();
		
		for (Lesson lesson : this.day) {
			if (lesson.matchesSubgroup()) {
				match.add(lesson);
			}
		}
		
		return match.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	private class ViewHolder {
		TextView tvSubgroup;
		TextView tvName;
		TextView tvBegin;
		TextView tvEnd;
		TextView tvProfRoom;
	}

	@Override
	public View getView(int i, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.itemlesson, null);
			holder.tvSubgroup = (TextView) convertView
					.findViewById(R.id.tvSubgroup);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvBegin = (TextView) convertView.findViewById(R.id.tvBegin);
			holder.tvEnd = (TextView) convertView.findViewById(R.id.tvEnd);
			holder.tvProfRoom = (TextView) convertView.findViewById(R.id.tvProfRoom);
			View view = View.inflate(this.fd, R.layout.widget, null);

			this.unT = (TextView) view.findViewById(R.id.hello_world_widget);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			View view = View.inflate(this.fd, R.layout.widget, null);
			this.unT = (TextView) view.findViewById(R.id.hello_world_widget);

		}

		Lesson lesson = (Lesson)getItem(i);
		int curr_day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

		/* Change the color depending on the current day and hour */

		holder.tvName.setTextColor(Color.rgb(0x00, 0x99, 0xCC));
		
		Context context = this.fd;
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
		ComponentName thisWidget = new ComponentName(context, AfficheEDT.class);

		
		if (this.day.getNumber() == curr_day) {
			if (lesson.isFinished())
				holder.tvName.setTextColor(Color.GRAY);
			else if (lesson.isOngoing()) {
				holder.tvName.setTextColor(Color.BLUE);
				remoteViews.setTextViewText(  R.id.hello_world_widget,  "Cours : "+lesson.getName()+
						"\n"
						+"Salle : "+lesson.getClassroom()
						+"\n"
						+lesson.getBegin()
						+"\n"
						+lesson.getEnd()
						);
				appWidgetManager.updateAppWidget(thisWidget, remoteViews);
			}

		}

		/* Set text */

		if (lesson.getSubgroup() != "") {
			holder.tvSubgroup.setTypeface(null, Typeface.ITALIC);
			holder.tvSubgroup.setText(lesson.getSubgroup() + "  ");
		}
		holder.tvName.setText(lesson.getName());
		holder.tvBegin.setText(lesson.getBegin());
		holder.tvEnd.setText(lesson.getEnd());
		holder.tvProfRoom.setText(lesson.getClassroom()+"___"+lesson.getProf());

		return convertView;
	}
}
