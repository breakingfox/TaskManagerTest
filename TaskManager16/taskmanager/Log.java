/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;

import java.io.IOException;

/**
 *
 * @author Rodion
 * @param <T>
 */
public interface Log<T> {
  
   public void createTask(T object);
   public void deleteTask(T object);
   public void saveAll()throws IOException;
   public T get(int i);
   public int size();
   public boolean isEmpty();
}
