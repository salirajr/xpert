package com.xybase.ax.eai.archcomp.larik.dao;

import java.util.ArrayList;

import com.xybase.ax.eai.archcomp.larik.Larik;

public interface LarikDao {

	public Larik get(String... parameters);

	public ArrayList<String> getRaw(String... parameters);
}
