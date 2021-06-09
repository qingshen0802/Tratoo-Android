package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.com.tratto.Model.Notification;

/**
 * Created by lily on 9/6/17.
 */

public class NotificationResponse extends ResponseModel{
    @SerializedName("notifications")
    public ArrayList<Notification> notifications;
}
