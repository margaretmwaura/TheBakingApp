package com.example.admin.baking;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable
{
    @SerializedName("id")
    int id;
    @SerializedName("shortDescription")
    public String shortDescription;
    @SerializedName("description")
    public String description;
    @SerializedName("videoURL")
    public String videoUrl;

    public void setId(int id)
    {
        this.id = id;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public void setShortDescription(String shortDescription)
    {
        this.shortDescription = shortDescription;
    }
    public void setVideoUrl(String videoUrl)
    {
        this.videoUrl = videoUrl;
    }
    public int getId()
    {
        return this.id;
    }
    public String getDescription()
    {
        return this.description;
    }
    public String getShortDescription()
    {
        return  this.shortDescription;
    }
    public String getVideoUrl()
    {
        return this.videoUrl;
    }

    public Step()
    {

    }
    public Step(int id,String description,String shortDescription,String videoUrl)
    {
        this.id = id;
        this.description = description;
        this.shortDescription = shortDescription;
        this.videoUrl = videoUrl;
    }
    public Step(Parcel in)
    {
        id = in.readInt();
        description = in.readString();
        shortDescription = in.readString();
        videoUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeString(shortDescription);
        dest.writeString(videoUrl);
    }
    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>()
    {

        @Override
        public Step createFromParcel(Parcel source)
        {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}


