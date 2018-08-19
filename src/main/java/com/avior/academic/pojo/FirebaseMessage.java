package com.avior.academic.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by jairo on 14/06/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FirebaseMessage implements Serializable{
   
	private static final long serialVersionUID = 1L;
	//Optional, Message recipient
    private String to;
    //At least 1, max 1000. Only for Multicast messaging
    private String[] registration_ids;
    //Optional.
    private String condition;
    //-------Options--------

    //Optional
    private String collapse_key;
    //Optional
    private String priority;
    //Optional
    private boolean content_available;
    //Optional
    private boolean delay_while_idle;
    //Optional
    private Integer time_to_live;
    //Optional
    private String restricted_package_name;
    //Optional
    private boolean dry_run;

    //--------Payload---------

    //Optional
    private Object data;
    //
    private FCMNotification notification;





    public FCMNotification getNotification() {
        return notification;
    }

    /**
     * Optional.
     * This parameter specifies the predefined, user-visible key-value pairs of the notification payload.
     * See Notification payload support for detail. For more information about notification message and data
     * message options, see [https://firebase.google.com/docs/cloud-messaging/concept-options#notifications_and_data_messages].
     */
    public void setNotification(FCMNotification notification) {
        this.notification = notification;
    }

    public Object getData() {
        return data;
    }

    /**
     * Optional.
     * This parameter specifies the custom key-value pairs of the message's payload.
     * For example, with data:{"score":"3x1"}:
     * On iOS, if the message is sent via APNS, it represents the custom data fields. If it is sent via FCM connection
     * server, it would be represented as key value dictionary in AppDelegate application:didReceiveRemoteNotification:.
     *
     * On Android, this would result in an intent extra named score with the string value 3x1. The key should not be a
     * reserved word ("from" or any word starting with "google" or "gcm"). Do not use any of the words defined in this
     * table (such as collapse_key). Values in string types are recommended. You have to convert values in objects or
     * other non-string data types (e.g., integers or booleans) to string.
     */
    public void setData(Object data) {
        this.data = data;
    }

    public boolean isDry_run() {
        return dry_run;
    }

    /**
     * Optional.
     * This parameter, when set to true, allows developers to test a request without actually sending a message.
     * The default value is false.
     */
    public void setDry_run(boolean dry_run) {
        this.dry_run = dry_run;
    }

    public String getRestricted_package_name() {
        return restricted_package_name;
    }

    /**
     * Optional.
     * This parameter specifies the package name of the application where the registration tokens must match in order
     * to receive the message.
     */
    public void setRestricted_package_name(String restricted_package_name) {
        this.restricted_package_name = restricted_package_name;
    }

    public Integer getTime_to_live() {
        return time_to_live;
    }

    /**
     * This parameter specifies how long (in seconds) the message should be kept in FCM storage if the device is offline.
     * The maximum time to live supported is 4 weeks, and the default value is 4 weeks.
     * For more information, see [https://firebase.google.com/docs/cloud-messaging/concept-options#setting-the-priority-of-a-message].
     */
    public void setTime_to_live(Integer time_to_live) {
        this.time_to_live = time_to_live;
    }

    public boolean isDelay_while_idle() {
        return delay_while_idle;
    }

    /**
     * Optional.
     * When this parameter is set to true, it indicates that the message should not be sent until the device becomes
     * active. The default value is false.
     */
    public void setDelay_while_idle(boolean delay_while_idle) {
        this.delay_while_idle = delay_while_idle;
    }

    public boolean isContent_available() {
        return content_available;
    }

    /**
     * Optional.
     * On iOS, use this field to represent content-available in the APNS payload. When a notification or message is sent
     * and this is set to true, an inactive client app is awoken. On Android, data messages wake the app by default.
     * On Chrome, currently not supported.
     */
    public void setContent_available(boolean content_available) {
        this.content_available = content_available;
    }

    public String getPriority() {
        return priority;
    }

    /**
     * Optional.
     * Sets the priority of the message. Valid values are "normal" and "high." On iOS, these correspond to APNs
     * priorities 5 and 10. By default, messages are sent with normal priority. Normal priority optimizes the client
     * app's battery consumption and should be used unless immediate delivery is required. For messages with normal
     * priority, the app may receive the message with unspecified delay. When a message is sent with high priority,
     * it is sent immediately, and the app can wake a sleeping device and open a network connection to your server.
     * For more information, see [https://firebase.google.com/docs/cloud-messaging/concept-options#setting-the-priority-of-a-message].
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCollapse_key() {
        return collapse_key;
    }

    /**
     * Optional.
     * This parameter identifies a group of messages (e.g., with collapse_key: "Updates Available") that can be
     * collapsed, so that only the last message gets sent when delivery can be resumed. This is intended to avoid
     * sending too many of the same messages when the device comes back online or becomes active (see delay_while_idle).
     *
     * Note that there is no guarantee of the order in which messages get sent.
     * Note: A maximum of 4 different collapse keys is allowed at any given time. This means a FCM connection server can
     * simultaneously store 4 different send-to-sync messages per client app. If you exceed this number, there is no
     * guarantee which 4 collapse keys the FCM connection server will keep.
     */
    public void setCollapse_key(String collapse_key) {
        this.collapse_key = collapse_key;
    }

    public String getCondition() {
        return condition;
    }

    /**
     * Optional.
     * This parameter specifies a logical expression of conditions that determine the message target.
     * Supported condition: Topic, formatted as "'yourTopic' in topics". This value is case-insensitive.
     * Supported operators: &&, ||. Maximum two operators per topic message supported.
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String[] getRegistration_ids() {
        return registration_ids;
    }

    /**
     * Optional. (in case "to" is not specified)
     * This parameter specifies a list of devices (registration tokens, or IDs) receiving a multicast message. It must
     * contain at least 1 and at most 1000 registration tokens.
     * Use this parameter only for multicast messaging, not for single recipients.
     */
    public void setRegistration_ids(String[] registration_ids) {
        this.registration_ids = registration_ids;
    }

    public String getTo() {
        return to;
    }

    /**
     * This parameter specifies the recipient of a message.
     * The value must be a registration token, notification key, or topic. Do not set this field when sending to
     * multiple topics. See condition.
     */
    public void setTo(String to) {
        this.to = to;
    }
}
