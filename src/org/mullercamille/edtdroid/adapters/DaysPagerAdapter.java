package org.mullercamille.edtdroid.adapters;

import java.util.List;
import java.util.Vector;

import org.mullercamille.edtdroid.fragments.DayFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class DaysPagerAdapter extends FragmentPagerAdapter {

	private final List<Fragment> fragments;

	public DaysPagerAdapter(FragmentManager fm) {
		super(fm);
		this.fragments = new Vector<Fragment>();
	}

	public void update() {
		for (Fragment i : this.fragments) {
			((DayFragment) i).update();
		}
	}

	public void addItem(Fragment frag) {
		this.fragments.add(frag);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int pos) {
		final Fragment frag = (Fragment) super.instantiateItem(container, pos);
		this.fragments.set(pos, frag);

		return frag;
	}

	@Override
	public Fragment getItem(int pos) {
		return this.fragments.get(pos);
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}
}
