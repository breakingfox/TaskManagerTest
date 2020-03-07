/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;

import java.io.Serializable;

/**
 *
 * @author Rodion
 * @param <T>
 */
public class Message<T> implements Serializable {
    private final T value;
    private final String action;
    public Message(T v,String a)
    {
        this.value=v;
        this.action=a;
    }
    public T getValue()
    {
        return this.value;
    }
    public String getMessage()
    {
        return this.action;
    }
    
    
}
