package com.van.sale.vansale.Database;

/**
 * Created by maaz on 24/09/18.
 */

public class UserClass {

    private String mUserName,mFirstName,mLastName,mPasswordMob,mFullName;

    public UserClass(String mUserName, String mFirstName, String mLastName, String mPasswordMob, String mFullName) {
        this.mUserName = mUserName;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mPasswordMob = mPasswordMob;
        this.mFullName = mFullName;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmPasswordMob() {
        return mPasswordMob;
    }

    public void setmPasswordMob(String mPasswordMob) {
        this.mPasswordMob = mPasswordMob;
    }

    public String getmFullName() {
        return mFullName;
    }

    public void setmFullName(String mFullName) {
        this.mFullName = mFullName;
    }
}
