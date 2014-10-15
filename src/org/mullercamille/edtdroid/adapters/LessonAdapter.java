package org.mullercamille.edtdroid.adapters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.mullercamille.edtdroid.R;
import org.mullercamille.edtdroid.application.EdtDroid;
import org.mullercamille.edtdroid.model.Day;
import org.mullercamille.edtdroid.model.Lesson;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
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


			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}
		Lesson lesson = (Lesson)getItem(i);
		int curr_day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		/* Change the color depending on the current day and hour */
		holder.tvName.setTextColor(Color.rgb(0x00, 0x99, 0xCC));	
		if (this.day.getNumber() == curr_day) {
			if (lesson.isFinished())
				holder.tvName.setTextColor(Color.GRAY);
			else if (lesson.isOngoing()) {
				holder.tvName.setTextColor(Color.BLUE);
				
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
