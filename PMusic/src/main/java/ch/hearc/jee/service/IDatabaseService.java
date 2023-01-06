
package ch.hearc.jee.service;

import java.util.List;

public interface IDatabaseService<T>
	{

	public void add(T item);

	public void delete(T item);

	public void deleteById(Long id);

	public void update(T item);

	public List<T> getAll();

	public T getById(Long id);
	}
